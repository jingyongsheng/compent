package com.zcbl.compent.function.user.pay;

import com.zcbl.compent.function.user.pay.utils.RSA;
import com.zcbl.compent.language.respertories.CmdFactory;
import com.zcbl.compent.language.respertories.Compent;
import com.zcbl.compent.language.respertories.Input;
import com.zcbl.compent.language.respertories.Output;
import com.zcbl.compent.language.respertories.UserCompent;
import com.zcbl.compent.task.client.annation.SysLoader;

@SysLoader
public class SignCompent extends UserCompent
{

	public void execute(Compent compent, Input input, Output output) throws Exception
	{
		if (input.getUser() != null && !input.getUser().isEmpty())
		{
			String mysign = "";
			String type = (String) input.getUser().get("sign_type");
			if (type.equals("RSA"))
			{
				mysign = RSA.sign((String) input.getUser().get("param"), (String) input.getUser().get("key"),
						(String) input.getUser().get("input_charset"));
			}
			output.getUser().put("result", mysign);
		}
	}

	public void load()
	{
		CmdFactory.getInstance().addCmd(SignCompent.class.getName(), this);
	}
}
