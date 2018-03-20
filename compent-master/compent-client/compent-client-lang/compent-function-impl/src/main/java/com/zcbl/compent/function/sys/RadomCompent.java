package com.zcbl.compent.function.sys;

import com.zcbl.compent.language.respertories.CmdFactory;
import com.zcbl.compent.language.respertories.Compent;
import com.zcbl.compent.language.respertories.Input;
import com.zcbl.compent.language.respertories.Output;
import com.zcbl.compent.language.respertories.SystemCompent;
import com.zcbl.compent.task.client.annation.SysLoader;

@SysLoader
public class RadomCompent extends SystemCompent
{

	public void execute(Compent compent, Input input, Output output)
	{
		String length = (String) input.getSys().get("length");
		int l_i = Integer.parseInt(length);
		if (length != null)
		{
			String str = "";
			str += (int) (Math.random() * 9 + 1);
			for (int i = 0; i < l_i + 1; i++)
			{
				str += (int) (Math.random() * 10);
			}
			output.getUser().put("result", str);
			return;
		}
		String start = (String) input.getSys().get("start");
		String end = (String) input.getSys().get("end");
		int s_i = Integer.parseInt(start);
		int e_i = Integer.parseInt(end);
		if (start != null && end != null)
		{
			java.util.Random random = new java.util.Random();
			int tmp = Math.abs(random.nextInt());
			int re = tmp % (e_i - s_i + 1) + e_i;
			output.getUser().put("result", re);
			return;
		}
	}

	public void load()
	{
		CmdFactory.getInstance().addCmd(RadomCompent.class.getName(), this);
	}

}
