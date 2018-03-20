package com.zcbl.compent.function.sys;

import com.zcbl.compent.language.respertories.CmdFactory;
import com.zcbl.compent.language.respertories.Compent;
import com.zcbl.compent.language.respertories.Input;
import com.zcbl.compent.language.respertories.Output;
import com.zcbl.compent.language.respertories.SystemCompent;
import com.zcbl.compent.task.client.annation.SysLoader;
import com.zcbl.compent.task.client.factory.TaskClientFactory;
import com.zcbl.compent.task.client.global.bean.Global;

@SysLoader
public class ClientCompent extends SystemCompent {

	public void execute(Compent compent, Input input, Output output) {
		Global global = TaskClientFactory.getInstance().getConfig().getGlobal();
		output.getUser().put("app", global.getApp());
		output.getUser().put("card", global.getCard());
		output.getUser().put("port", global.getPort());
		output.getUser().put("server", global.getServer());
		output.getUser().put("transport", global.getTransport());
		output.getUser().put("ip", global.getIp());
	}

	public void load() {
		CmdFactory.getInstance().addCmd(ClientCompent.class.getName(), this);
	}
}
