package com.zcbl.compent.client.quartz;

public class TaskManager {
	private static TaskManager cache = new TaskManager();
	private Timer timer = null;

	private TaskManager() {
	}

	public static TaskManager getInstance() {
		return cache;
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public void exe(String taskid) {
		timer.exe(taskid);
	}
}
