package com.zcbl.compent.function.user;

import com.zcbl.compent.language.respertories.CmdFactory;
import com.zcbl.compent.language.respertories.Compent;
import com.zcbl.compent.language.respertories.Input;
import com.zcbl.compent.language.respertories.Output;
import com.zcbl.compent.language.respertories.UserCompent;
import com.zcbl.compent.task.client.annation.SysLoader;

@SysLoader
public class RequestCompent extends UserCompent
{

	public void execute(Compent compent, Input input, Output output)
	{
		if (input.getUser() != null && !input.getUser().isEmpty())
		{
			output.getUser().putAll(input.getUser());
		}
	}

	public void load()
	{
		CmdFactory.getInstance().addCmd(RequestCompent.class.getName(), this);
	}
}
