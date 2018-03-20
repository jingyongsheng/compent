package com.zcbl.compent.function.user.pay;

import com.zcbl.compent.function.user.pay.utils.RSA;
import com.zcbl.compent.language.respertories.CmdFactory;
import com.zcbl.compent.language.respertories.Compent;
import com.zcbl.compent.language.respertories.Input;
import com.zcbl.compent.language.respertories.Output;
import com.zcbl.compent.language.respertories.UserCompent;
import com.zcbl.compent.task.client.annation.SysLoader;

@SysLoader
public class VerifyCompent extends UserCompent
{

	public void execute(Compent compent, Input input, Output output) throws Exception
	{
		if (input.getUser() != null && !input.getUser().isEmpty())
		{
			String type = (String) input.getUser().get("sign_type");
			String key = (String) input.getUser().get("key");
			String input_charset = (String) input.getUser().get("input_charset");
			String sign = (String) input.getUser().get("sign");
			String param = (String) input.getUser().get("param");
			boolean isSign = false;
			if (type.equals("RSA"))
			{
				try
				{
					isSign = RSA.verify(param, sign, key, input_charset);
				} catch (Exception e)
				{
					output.getUser().put("result", String.valueOf(false));
				}
			}
			output.getUser().put("result", String.valueOf(isSign));
		}
	}

	public void load()
	{
		CmdFactory.getInstance().addCmd(VerifyCompent.class.getName(), this);
	}
}
