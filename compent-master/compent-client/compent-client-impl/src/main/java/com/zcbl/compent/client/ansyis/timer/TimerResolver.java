package com.zcbl.compent.client.ansyis.timer;

import com.zcbl.compent.client.quartz.TaskManager;
import com.zcbl.compent.client.quartz.Timer;
import com.zcbl.compent.data.center.log.Log;
import com.zcbl.compent.data.center.log.factory.LogCompent;
import com.zcbl.compent.language.respertories.CommandFactory;
import com.zcbl.compent.language.respertories.CommandFactory.CommandStack;
import com.zcbl.compent.task.client.annation.SysLoader;
import com.zcbl.compent.task.client.sys.Loader;

@SysLoader
public class TimerResolver implements Timer, Loader {
	static Log log = LogCompent.getInstance().getLog(TimerResolver.class);

	@Override
	public void load() {
		TaskManager.getInstance().setTimer(this);
	}

	@Override
	public void exe(String key) {
		try {
			CommandStack stack = CommandFactory.getInstance().buildCommandStack();
			stack = CommandFactory.getInstance().excuteCommand(key, stack);
			stack.clear();
			stack = null;
		} catch (Exception e) {
			log.info(e.getMessage());
		}
	}
}
