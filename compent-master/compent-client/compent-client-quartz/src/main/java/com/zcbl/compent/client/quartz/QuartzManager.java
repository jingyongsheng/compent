package com.zcbl.compent.client.quartz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;

import com.zcbl.compent.data.center.log.Log;
import com.zcbl.compent.data.center.log.factory.LogCompent;

public class QuartzManager {
	private static SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();
	private static String JOB_GROUP_NAME = "EXTJWEB_JOBGROUP_NAME";
	private static String TRIGGER_GROUP_NAME = "EXTJWEB_TRIGGERGROUP_NAME";
	static Log log = LogCompent.getInstance().getLog(QuartzManager.class);

	public static void getList() {
		try {
			Scheduler scheduler = gSchedulerFactory.getScheduler();
			GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
			Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
			List<ScheduleJob> jobList = new ArrayList<ScheduleJob>();
			for (JobKey jobKey : jobKeys) {
				List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
				for (Trigger trigger : triggers) {
					ScheduleJob job = new ScheduleJob();
					job.setJobName(jobKey.getName());
					job.setJobGroup(jobKey.getGroup());
					job.setDesc("trigger:" + trigger.getKey());
					Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
					job.setJobStatus(triggerState.name());
					if (trigger instanceof CronTrigger) {
						CronTrigger cronTrigger = (CronTrigger) trigger;
						String cronExpression = cronTrigger.getCronExpression();
						job.setCronExpression(cronExpression);
					}
					jobList.add(job);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static void addJob(String jobName, Job job, String time, Map<String, Object> obj) {
		if (jobName == null || jobName.equals("") || time == null || time.equals("") || job == null)
			return;
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			TriggerKey triggerKey = TriggerKey.triggerKey(jobName, TRIGGER_GROUP_NAME);
			CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);
			if (trigger == null) {
				JobDetail jobDetail = JobBuilder.newJob(job.getClass()).withIdentity(jobName, JOB_GROUP_NAME).build();
				jobDetail.getJobDataMap().putAll(obj);
				CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(time);
				trigger = TriggerBuilder.newTrigger().withIdentity(jobName, TRIGGER_GROUP_NAME)
						.withSchedule(cronScheduleBuilder).build();
				sched.scheduleJob(jobDetail, trigger);
				if (!sched.isShutdown()) {
					sched.start();
				}
			} else {
				log.info(jobName + " timer exists");
				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(time);
				trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
				sched.rescheduleJob(triggerKey, trigger);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static void addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName, Job job,
			String time, Map<String, Object> obj) {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			JobDetail jobDetail = JobBuilder.newJob(job.getClass()).withIdentity(jobName, jobGroupName).build();
			jobDetail.getJobDataMap().putAll(obj);
			CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(time);
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerName, triggerGroupName)
					.withSchedule(cronScheduleBuilder).build();
			sched.scheduleJob(jobDetail, trigger);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static void modifyJobTime(String jobName, String time) {
		if (jobName == null || jobName.equals("") || time == null || time.equals(""))
			return;
		try {
			Scheduler scheduler = gSchedulerFactory.getScheduler();
			TriggerKey triggerKey = TriggerKey.triggerKey(jobName, TRIGGER_GROUP_NAME);
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			if (trigger == null) {
				return;
			}
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(time);
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
			scheduler.rescheduleJob(triggerKey, trigger);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static void modifyJobTime(String triggerName, String triggerGroupName, String time) {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
			CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);
			if (trigger == null) {
				return;
			}
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(time);
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
			sched.rescheduleJob(triggerKey, trigger);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static void pauseJob(String jobName) {
		if (jobName == null || jobName.equals(""))
			return;
		Scheduler scheduler;
		try {
			scheduler = gSchedulerFactory.getScheduler();
			JobKey jobKey = JobKey.jobKey(jobName, JOB_GROUP_NAME);
			scheduler.pauseJob(jobKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	public static void resumeJob(String jobName) {
		if (jobName == null || jobName.equals(""))
			return;
		Scheduler scheduler;
		try {
			scheduler = gSchedulerFactory.getScheduler();
			JobKey jobKey = JobKey.jobKey(jobName, JOB_GROUP_NAME);
			scheduler.resumeJob(jobKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	public static void removeJob(String jobName) {
		if (jobName == null || jobName.equals(""))
			return;
		try {
			Scheduler scheduler = gSchedulerFactory.getScheduler();
			// TriggerKey triggerKey = TriggerKey.triggerKey(jobName,
			// TRIGGER_GROUP_NAME);
			// scheduler.pauseTrigger(triggerKey);
			// scheduler.unscheduleJob(triggerKey);
			JobKey jobKey = JobKey.jobKey(jobName, JOB_GROUP_NAME);
			scheduler.deleteJob(jobKey);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static void removeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName) {
		try {
			Scheduler scheduler = gSchedulerFactory.getScheduler();
			// TriggerKey triggerKey = TriggerKey.triggerKey(triggerName,
			// triggerGroupName);
			// scheduler.pauseTrigger(triggerKey);
			// scheduler.unscheduleJob(triggerKey);
			JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
			scheduler.deleteJob(jobKey);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static void startJobs() {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			sched.start();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static void shutdownJobs() {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			if (!sched.isShutdown()) {
				sched.shutdown();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
