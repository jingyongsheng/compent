package com.zcbl.compent.language.respertories;

/**
 * @author jys 2016年11月15日
 */
public class FunctionFactory
{
	private static FunctionFactory function = new FunctionFactory();

	private FunctionFactory()
	{
	}

	public static FunctionFactory getInstance()
	{
		return function;
	}

	public Function buildAndReturnFuntion(Compent compent, String key)
	{
		Function f = null;
		Cmd cmd = CmdFactory.getInstance().getUserCmd(key);
		if (cmd != null)
		{
			f = new Function(compent, cmd);
		} else
		{
			cmd = CmdFactory.getInstance().getSysCmd().get(key);
			if (cmd != null)
			{
				f = new Function(compent, cmd);
			}
		}
		return f;
	}
}
