package com.zcbl.compent.function.user;

import java.util.Map;
import com.zcbl.compent.language.respertories.CmdFactory;
import com.zcbl.compent.language.respertories.Compent;
import com.zcbl.compent.language.respertories.Input;
import com.zcbl.compent.language.respertories.Output;
import com.zcbl.compent.language.respertories.UserCompent;
import com.zcbl.compent.task.client.annation.SysLoader;

@SysLoader
public class ObjectCompent extends UserCompent
{
	public Map<String, Object> map;

	public ObjectCompent(Map<String, Object> map)
	{
		this.map = map;
	}

	public ObjectCompent()
	{
	}

	public void execute(Compent compent, Input input, Output output)
	{
		if (map != null)
			output.getUser().putAll(map);
		if (input.getUser() != null && !input.getUser().isEmpty())
		{
			output.getUser().putAll(input.getUser());
		}
	}

	public void load()
	{
		CmdFactory.getInstance().addCmd(ObjectCompent.class.getName(), this);
	}
}
