package com.zcbl.compent.client.ansyis.dbobj;

import java.util.List;
import java.util.Map;

import com.zcbl.compent.config.ConfigClient;
import com.zcbl.compent.db.center.core.route.Method;
import com.zcbl.compent.db.center.core.route.RouteCache;
import com.zcbl.compent.task.client.annation.AnysisCompent;
import com.zcbl.compent.task.client.ansyis.api.Anysis;

@AnysisCompent
public class DbObjectAnsyisImpl implements Anysis
{

	@Override
	public void anysis()
	{

		List<Map<String, Object>> list = ConfigClient.getGlobal().getGlobalByType("db_obj");
		if (list == null || list.isEmpty())
		{
			return;
		}
		for (Map<String, Object> m : list)
		{
			if (m != null && !m.isEmpty())
			{

				String object = (String) m.get("object");
				String datasource = (String) m.get("datasource");
				String table = (String) m.get("table");
				String method = (String) m.get("method");
				String card = (String) m.get("card");
				String key = (String) m.get("key");
				if (method != null && !method.equals(""))
				{
					String[] me = method.split(",");
					Method md = null;
					for (String s : me)
					{
						if (s.toLowerCase().equals("read"))
						{
							md = Method.READ;
						} else if (s.toLowerCase().equals("write"))
						{
							md = Method.WRITE;
						} else if (s.toLowerCase().equals("readwrite"))
						{
							md = Method.READWRITE;
						}
						RouteCache.getInstance().addDataSource(card, object, datasource, table, key, md);
					}
				}
			}
		}
	}
}