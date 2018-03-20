package com.zcbl.compent.function.user;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.zcbl.compent.data.center.api.bean.Query;
import com.zcbl.compent.data.center.api.bean.Result;
import com.zcbl.compent.data.center.api.bean.Type;
import com.zcbl.compent.data.center.client.DataCenterClient;
import com.zcbl.compent.db.center.core.entity.Entity;
import com.zcbl.compent.language.respertories.CmdFactory;
import com.zcbl.compent.language.respertories.Compent;
import com.zcbl.compent.language.respertories.CompentFactory;
import com.zcbl.compent.language.respertories.Input;
import com.zcbl.compent.language.respertories.Output;
import com.zcbl.compent.language.respertories.UserCompent;
import com.zcbl.compent.task.client.annation.SysLoader;

@SysLoader
public class LoadCompent extends UserCompent {

	public void execute(Compent compent, Input input, Output output) throws Exception {
		if (input.getUser() != null && !input.getUser().isEmpty()) {
			Set<String> set = input.getUser().keySet();
			Iterator<String> ite = set.iterator();
			Entity entity = new Entity();
			while (ite.hasNext()) {
				String k = (String) ite.next();
				entity.getFiled().put(k, k);
				Object ob = input.getUser().get(k);
				entity.getValues().put(k, ob);
			}
			Map<String, Object> compents = CompentFactory.getInstance().getCompent().get(compent.getAttrs().get("db"));
			if (compents == null)
				return;
			String sql = (String) compent.getAttrs().get("sql");
			entity.setSql(sql);
			entity.setPrikey((String) compents.get("key"));
			entity.setTableName((String) compents.get("table"));
			Query query = new Query();
			query.setType(Type.OBJECT);
			query.setEntity(entity);
			query.setEntityName((String) compents.get("object"));
			query.setApp((String) compents.get("card"));
			Result rs = DataCenterClient.getInstance().find(query);
			entity.clear();
			if (rs.getList() != null && rs.getList().size() > 0) {
				output.getUser().putAll((Map<String, Object>) rs.getList().get(0));
			}
		}
	}

	public void load() {
		CmdFactory.getInstance().addCmd(LoadCompent.class.getName(), this);
	}
}
