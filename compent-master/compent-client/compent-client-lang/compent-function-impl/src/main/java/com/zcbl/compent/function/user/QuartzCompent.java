package com.zcbl.compent.function.user;

import java.util.HashMap;
import java.util.Map;

import com.zcbl.compent.client.quartz.QuartzManager;
import com.zcbl.compent.client.quartz.QuartzTaskImpl;
import com.zcbl.compent.client.quartz.TimerStrategyFactory;
import com.zcbl.compent.client.quartz.TimerStrategyFactory.Job;
import com.zcbl.compent.language.respertories.CmdFactory;
import com.zcbl.compent.language.respertories.Compent;
import com.zcbl.compent.language.respertories.Input;
import com.zcbl.compent.language.respertories.Output;
import com.zcbl.compent.language.respertories.UserCompent;
import com.zcbl.compent.task.client.annation.SysLoader;

@SysLoader
public class QuartzCompent extends UserCompent {

	public void execute(Compent compent, Input input, Output output) {
		String type = (String) input.getSys().get("type");
		String key = (String) input.getSys().get("key");
		String stop = (String) input.getSys().get("stop");
		if (type == null || type.equals("") || key == null || key.equals(""))
			return;
		Job job = TimerStrategyFactory.getIntance().getJob(key);
		if (job != null) {
			if (type.equals("update")) {
				String t = job.getTimer().poll();
				if (t == null) {
					if (stop != null && stop.equals("yes")) {
						QuartzManager.removeJob(key);
					}
				} else {
					QuartzManager.modifyJobTime(key, t);
				}
			} else if (type.equals("stop")) {
				QuartzManager.pauseJob(key);
			} else if (type.equals("start")) {
				QuartzManager.resumeJob(key);
			} else if (type.equals("delete")) {
				QuartzManager.removeJob(key);
			} else if (type.equals("add")) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(QuartzTaskImpl.TASK_KEY, job.getJobid());
				QuartzManager.addJob(job.getJobid(), QuartzTaskImpl.getInstance(), job.getTimer().poll(), map);
			}
		}
	}

	public void load() {
		CmdFactory.getInstance().addCmd(QuartzCompent.class.getName(), this);
	}
}
