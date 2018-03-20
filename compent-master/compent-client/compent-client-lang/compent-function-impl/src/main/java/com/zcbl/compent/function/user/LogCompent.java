package com.zcbl.compent.function.user;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.zcbl.compent.data.center.log.Log;
import com.zcbl.compent.language.respertories.CmdFactory;
import com.zcbl.compent.language.respertories.Compent;
import com.zcbl.compent.language.respertories.Input;
import com.zcbl.compent.language.respertories.Output;
import com.zcbl.compent.language.respertories.UserCompent;
import com.zcbl.compent.task.client.annation.SysLoader;

@SysLoader
public class LogCompent extends UserCompent {

	public void execute(Compent compent, Input input, Output output) {
		if (input.getUser() != null && !input.getUser().isEmpty()) {
			Log log = com.zcbl.compent.data.center.log.factory.LogCompent.getInstance().getLog(LogCompent.class);
			String type = (String) input.getSys().get("type");
			if (type == null || type.equals(""))
				type = "info";
			StringBuffer sb = new StringBuffer();
			SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sb.append(simple.format(new Date())).append("  -   ");
			sb.append(input.getUser().toString());
			if (type.equals("info")) {
				log.info(sb.toString());
			} else if (type.equals("error")) {
				log.error(sb.toString());
			} else if (type.equals("warn")) {
				log.warn(sb.toString());
			} else if (type.equals("debug")) {
				log.debug(sb.toString());
			} else if (type.equals("track")) {
				log.track(sb.toString());
			}
		}
	}

	public void load() {
		CmdFactory.getInstance().addCmd(LogCompent.class.getName(), this);
	}
}
