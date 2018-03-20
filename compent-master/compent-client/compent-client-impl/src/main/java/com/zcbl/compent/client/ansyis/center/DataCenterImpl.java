package com.zcbl.compent.client.ansyis.center;

import java.util.List;
import java.util.Map;

import com.zcbl.compent.config.ConfigClient;
import com.zcbl.compent.task.client.annation.AnysisCompent;
import com.zcbl.compent.task.client.ansyis.api.Anysis;

@AnysisCompent
public class DataCenterImpl implements Anysis
{
	@Override
	public void anysis()
	{
		List<Map<String, Object>> list = ConfigClient.getGlobal().getGlobalByType("data_center");
		if (list == null || list.isEmpty())
		{
			return;
		}
		for (Map<String, Object> map : list)
		{
			if (map != null && !map.isEmpty())
			{

			}
		}
	}
}