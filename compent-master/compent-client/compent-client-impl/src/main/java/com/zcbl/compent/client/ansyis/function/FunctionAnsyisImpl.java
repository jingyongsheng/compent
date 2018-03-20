package com.zcbl.compent.client.ansyis.function;

import java.util.List;
import java.util.Map;

import com.zcbl.compent.config.ConfigClient;
import com.zcbl.compent.language.respertories.CmdFactory;
import com.zcbl.compent.task.client.annation.AnysisCompent;
import com.zcbl.compent.task.client.ansyis.api.Anysis;

@AnysisCompent
public class FunctionAnsyisImpl implements Anysis
{

	@Override
	public void anysis()
	{
		List<Map<String, Object>> list = ConfigClient.getGlobal().getGlobalByType("function");
		if (list == null || list.isEmpty())
		{
			return;
		}
		for (Map<String, Object> m : list)
		{
			if (m != null && !m.isEmpty())
			{
				String impl = (String) m.get("impl");
				String id = (String) m.get("unique");
				CmdFactory.getInstance().buildCmd(id, impl);
			}
		}
	}
}