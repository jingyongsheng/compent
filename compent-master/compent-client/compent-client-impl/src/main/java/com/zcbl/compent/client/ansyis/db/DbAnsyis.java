package com.zcbl.compent.client.ansyis.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zcbl.compent.config.ConfigClient;
import com.zcbl.compent.db.client.init.InitPool;
import com.zcbl.compent.db.client.init.InitPool.Jdbc;
import com.zcbl.compent.task.client.annation.AnysisCompent;
import com.zcbl.compent.task.client.ansyis.api.Anysis;

@AnysisCompent
public class DbAnsyis implements Anysis
{
	@Override
	public void anysis()
	{
		List<Map<String, Object>> list = ConfigClient.getGlobal().getGlobalByType("db");
		if (list == null || list.isEmpty())
		{
			return;
		}
		for (Map<String, Object> m : list)
		{
			if (m != null && !m.isEmpty())
			{
				String status = (String) m.get("status");
				String driver = (String) m.get("driver");
				String url = (String) m.get("url");
				String _type = (String) m.get("type");
				String username = (String) m.get("username");
				String password = (String) m.get("password");
				String minconn = (String) m.get("minconn");
				String maxconn = (String) m.get("maxconn");
				String id = (String) m.get("unique");
				Map<String, Jdbc> jd = new HashMap<String, Jdbc>();
				Jdbc jdbc = InitPool.getInstance().buildJdbc(id, url, driver, username, password, _type, maxconn,
						minconn, status);
				jd.put(id, jdbc);
				InitPool.getInstance().init(jd);
			}
		}
	}
}