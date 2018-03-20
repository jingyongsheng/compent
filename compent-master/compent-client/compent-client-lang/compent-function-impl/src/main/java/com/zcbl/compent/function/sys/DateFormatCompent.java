package com.zcbl.compent.function.sys;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.zcbl.compent.language.respertories.CmdFactory;
import com.zcbl.compent.language.respertories.Compent;
import com.zcbl.compent.language.respertories.Input;
import com.zcbl.compent.language.respertories.Output;
import com.zcbl.compent.language.respertories.SystemCompent;
import com.zcbl.compent.task.client.annation.SysLoader;

@SysLoader
public class DateFormatCompent extends SystemCompent {

	public void execute(Compent compent, Input input, Output output) {
		String format = (String) input.getSys().get("format");
		if (format == null) {
			format = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat simple = new SimpleDateFormat(format);
		output.getUser().put("result", simple.format(new Date()));
	}

	public void load() {
		CmdFactory.getInstance().addCmd(DateFormatCompent.class.getName(), this);
	}
}
