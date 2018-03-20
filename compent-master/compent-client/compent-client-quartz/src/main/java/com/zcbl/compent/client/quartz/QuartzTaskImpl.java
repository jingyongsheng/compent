package com.zcbl.compent.client.quartz;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.zcbl.compent.data.center.log.Log;
import com.zcbl.compent.data.center.log.factory.LogCompent;

@DisallowConcurrentExecution
public class QuartzTaskImpl implements Job {
	static Log log = LogCompent.getInstance().getLog(QuartzTaskImpl.class);
	public static final String TASK_KEY = "JOB_ID";
	private static QuartzTaskImpl impl = new QuartzTaskImpl();

	public static QuartzTaskImpl getInstance() {
		return impl;
	}

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		JobDataMap dataMap = arg0.getJobDetail().getJobDataMap();
		String jobid = (String) dataMap.get(TASK_KEY);
		TaskManager.getInstance().exe(jobid);
		log.info(jobid + " EXCUTE!");
	}
}
