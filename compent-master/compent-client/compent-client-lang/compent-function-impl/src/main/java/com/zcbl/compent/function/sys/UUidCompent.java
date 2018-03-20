package com.zcbl.compent.function.sys;

import java.util.UUID;

import com.zcbl.compent.language.respertories.CmdFactory;
import com.zcbl.compent.language.respertories.Compent;
import com.zcbl.compent.language.respertories.Input;
import com.zcbl.compent.language.respertories.Output;
import com.zcbl.compent.language.respertories.SystemCompent;
import com.zcbl.compent.task.client.annation.SysLoader;

@SysLoader
public class UUidCompent extends SystemCompent
{

	public void execute(Compent compent, Input input, Output output)
	{
		output.getUser().put("result", uuid());
	}

	public static String uuid()
	{
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public void load()
	{
		CmdFactory.getInstance().addCmd(UUidCompent.class.getName(), this);
	}
}
