package com.zcbl.compent.function.user.string;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.zcbl.compent.language.respertories.CmdFactory;
import com.zcbl.compent.language.respertories.Compent;
import com.zcbl.compent.language.respertories.Input;
import com.zcbl.compent.language.respertories.Output;
import com.zcbl.compent.language.respertories.UserCompent;
import com.zcbl.compent.task.client.annation.SysLoader;

@SysLoader
public class AppendCompent extends UserCompent
{

	public void execute(Compent compent, Input input, Output output)
	{
		if (input.getUser() != null && !input.getUser().isEmpty())
		{
			try
			{
				Map<String, Object> attr = input.getSys();
				String join = "&";
				if (attr != null && attr.size() > 0)
				{
					String origin = (String) input.getSys().get("origin");
					String lined = (String) attr.get("join");
					if (lined != null && !lined.equals(""))
					{
						join = lined;
					}
					List<String> keys = new ArrayList<String>(input.getUser().keySet());
					String sort = (String) attr.get("sort");
					if (sort != null && sort.equals("true"))
						Collections.sort(keys);
					for (int i = 0; i < keys.size(); i++)
					{
						String key = keys.get(i);
						String value = (String) input.getUser().get(key);
						if (i == keys.size() - 1)
						{
							origin = origin + key + "=" + value;
						} else
						{
							origin = origin + key + "=" + value + join;
						}
					}
					output.getUser().put("result", origin);
				} else
				{
					output.getUser().put("result", "");
				}
			} catch (Exception e)
			{
				e.printStackTrace();
				output.getUser().put("result", "");
			}
		}
	}

	public void load()
	{
		CmdFactory.getInstance().addCmd(AppendCompent.class.getName(), this);
	}

}
