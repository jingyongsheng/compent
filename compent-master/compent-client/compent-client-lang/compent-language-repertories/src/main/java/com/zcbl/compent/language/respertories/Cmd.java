package com.zcbl.compent.language.respertories;

import com.zcbl.compent.task.client.sys.Loader;

public interface Cmd extends Loader
{
	public void execute(Compent compent, Input in, Output out) throws Exception;
}
