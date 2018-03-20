package com.zcbl.compent.client.ansyis.object;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zcbl.compent.config.ConfigClient;
import com.zcbl.compent.data.center.log.Log;
import com.zcbl.compent.data.center.log.factory.LogCompent;
import com.zcbl.compent.function.user.ObjectCompent;
import com.zcbl.compent.language.respertories.CmdFactory;
import com.zcbl.compent.task.client.annation.AnysisCompent;
import com.zcbl.compent.task.client.ansyis.api.Anysis;

@AnysisCompent
public class ObjectAnsyisImpl implements Anysis {
	static Log log = LogCompent.getInstance().getLog(ObjectAnsyisImpl.class);

	@Override
	public void anysis() {
		List<Map<String, Object>> list = ConfigClient.getGlobal().getGlobalByType("object");
		if (list == null || list.isEmpty()) {
			return;
		}
		for (Map<String, Object> m : list) {
			if (m != null && !m.isEmpty()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.putAll(m);
				String id = (String) map.get("unique");
				map.remove("unique");
				map.remove("status");
				map.remove("card");
				ObjectCompent b = new ObjectCompent(map);
				CmdFactory.getInstance().addCmd(id, b);
			}
		}
	}
}