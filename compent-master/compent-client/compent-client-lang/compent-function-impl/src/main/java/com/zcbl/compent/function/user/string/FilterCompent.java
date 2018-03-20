package com.zcbl.compent.function.user.string;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zcbl.compent.language.respertories.CmdFactory;
import com.zcbl.compent.language.respertories.Compent;
import com.zcbl.compent.language.respertories.Input;
import com.zcbl.compent.language.respertories.Output;
import com.zcbl.compent.language.respertories.UserCompent;
import com.zcbl.compent.task.client.annation.SysLoader;

@SysLoader
public class FilterCompent extends UserCompent
{

	public void execute(Compent compent, Input input, Output output)
	{
		if (input.getUser() != null && !input.getUser().isEmpty())
		{
			try
			{
				Map<String, Object> result = new HashMap<String, Object>();
				if (input.getUser() == null || input.getUser().size() <= 0)
				{
					return;
				}
				String filters = (String) compent.getAttrs().get("filters");
				String[] args = null;
				if (filters != null && !filters.equals(""))
				{
					args = filters.split("\\,");
				}
				List<String> list = new ArrayList<String>(input.getUser().keySet());
				out: for (String key : list)
				{
					String value = (String) input.getUser().get(key);
					if (value == null || value.equals(""))
					{
						continue out;
					}
					if (args != null)
						for (String k : args)
						{
							if (key == null)
								continue out;
							if (key.equalsIgnoreCase(k))
							{
								continue out;
							}

						}
					result.put(key, value);
				}
				output.getUser().putAll(result);
			} catch (Exception e)
			{
				e.printStackTrace();
				output.getUser().put("result", "");
			}
		}
	}

	public void load()
	{
		CmdFactory.getInstance().addCmd(FilterCompent.class.getName(), this);
	}
}
