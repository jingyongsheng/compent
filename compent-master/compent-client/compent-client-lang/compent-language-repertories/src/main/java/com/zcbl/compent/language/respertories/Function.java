package com.zcbl.compent.language.respertories;

import com.zcbl.compent.language.respertories.CommandFactory.CommandStack;

/**
 * @author jys 函数
 *
 */
public class Function
{
	public Cmd cmd;
	Compent compent;

	public Function(Compent compent, Cmd cmd)
	{
		this.compent = compent;
		this.cmd = cmd;
	}

	public void excute(Input input, CommandStack stack) throws Exception
	{
		Output output = new Output();
		cmd.execute(compent, input, output);
		if (compent.getPointer() != null)
			stack.setPointer(compent.getPointer(), output.getUser());
	}

	public Cmd getCmd()
	{
		return cmd;
	}

	public void setCmd(Cmd cmd)
	{
		this.cmd = cmd;
	}
}
