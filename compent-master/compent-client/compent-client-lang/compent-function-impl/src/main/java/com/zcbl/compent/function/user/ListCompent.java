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
public class ListCompent extends UserCompent {

	public void execute(Compent compent, Input input, Output output) throws Exception {
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
		String length = compent.getAttrs().get("length") == null ? "10" : (String) compent.getAttrs().get("length");
		String start = (String) compent.getAttrs().get("start") == null ? "0"
				: (String) compent.getAttrs().get("start");
		entity.setLength(Integer.parseInt(length));
		entity.setStart(Integer.parseInt(start));
		entity.setSql(sql);
		entity.setPrikey((String) compents.get("key"));
		entity.setTableName((String) compents.get("table"));
		entity.setOrderBy((String) compent.getAttrs().get("order"));
		Query query = new Query();
		String count = (String) compent.getAttrs().get("count");
		if (count != null && !count.equals("")) {
			boolean b = Boolean.parseBoolean(count);
			query.setCount(b);
		}
		query.setType(Type.OBJECT);
		query.setEntity(entity);
		query.setEntityName((String) compents.get("object"));
		query.setApp((String) compents.get("card"));
		Result rs = DataCenterClient.getInstance().find(query);
		entity.clear();
		output.getUser().put("list", rs.getList());
		output.getUser().put("count", rs.getCount());
	}

	public void load() {
		CmdFactory.getInstance().addCmd(ListCompent.class.getName(), this);
	}
}
