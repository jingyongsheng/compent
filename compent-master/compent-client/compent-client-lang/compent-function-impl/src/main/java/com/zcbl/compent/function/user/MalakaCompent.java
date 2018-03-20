package com.zcbl.compent.function.user;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.zcbl.compent.language.respertories.CmdFactory;
import com.zcbl.compent.language.respertories.Compent;
import com.zcbl.compent.language.respertories.Input;
import com.zcbl.compent.language.respertories.Output;
import com.zcbl.compent.language.respertories.UserCompent;
import com.zcbl.compent.task.client.annation.SysLoader;
import com.zcbl.malaka.rpc.common.Malaka;
import com.zcbl.malaka.rpc.common.url.Response;

@SysLoader
public class MalakaCompent extends UserCompent
{

	public void execute(Compent compent, Input input, Output output)
	{
		String path = (String) input.getSys().get("path");
		if (path != null && !path.equals(""))
		{
			Map<String, String> pa = new HashMap<String, String>();
			Map<String, Object> map = input.getUser();
			if (!map.isEmpty())
			{
				Set<String> set = map.keySet();
				Iterator<String> ite = set.iterator();
				while (ite.hasNext())
				{
					String key = ite.next();
					pa.put(key, (String) map.get(key));
				}
			}
			Response response = Malaka.remote(path).request(pa).result();
			output.getUser().putAll(response.getResponse());
		}
	}

	public void load()
	{
		CmdFactory.getInstance().addCmd(MalakaCompent.class.getName(), this);
	}
}
