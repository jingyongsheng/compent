package com.zcbl.compent.client.quartz;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class TimerStrategyFactory {
	public Map<String, Job> timer = new HashMap<String, Job>();
	static TimerStrategyFactory strategy = new TimerStrategyFactory();

	private TimerStrategyFactory() {
	}

	public static TimerStrategyFactory getIntance() {
		return strategy;
	}

	public Map<String, Job> getTimer() {
		return timer;
	}

	public void setTimer(Job job) {
		timer.put(job.getJobid(), job);
		if (job.getStatus() != null)
			if (job.getStatus().equals(JobStatus.ADD)) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(QuartzTaskImpl.TASK_KEY, job.getJobid());
				QuartzManager.addJob(job.getJobid(), QuartzTaskImpl.getInstance(), job.getTimer().poll(), map);
			} else if (job.getStatus().equals(JobStatus.UPDATE)) {
				QuartzManager.modifyJobTime(job.getJobid(), job.getTimer().poll());
			} else if (job.getStatus().equals(JobStatus.DELETE)) {
				QuartzManager.removeJob(job.getJobid());
			} else if (job.getStatus().equals(JobStatus.STOP)) {
				QuartzManager.pauseJob(job.getJobid());
			} else if (job.getStatus().equals(JobStatus.START)) {
				QuartzManager.resumeJob(job.getJobid());
			}
	}

	public Job getJob(String key) {
		return timer.get(key);
	}

	public Job createJob() {
		return new Job();
	}

	public class Job {
		Queue<String> timer = new LinkedList<String>();
		String jobid;
		JobStatus status;
		String jobGroup;
		String triger;
		String trigerGroup;
		String cron;

		public String getCron() {
			return cron;
		}

		public void setCron(String cron) {
			timer.clear();
			if (cron != null && !cron.equals("")) {
				String[] cr = cron.split("\\;");
				for (String c : cr) {
					timer.add(c);
				}
			}
			this.cron = cron;
		}

		public void setStatus(JobStatus status) {
			this.status = status;
		}

		public Queue<String> getTimer() {
			return timer;
		}

		public void setTimer(Queue<String> timer) {
			this.timer = timer;
		}

		public String getJobid() {
			return jobid;
		}

		public void setJobid(String jobid) {
			this.jobid = jobid;
		}

		public JobStatus getStatus() {
			return status;
		}

		public void setStatus(String status) {
			if (status != null) {
				switch (status) {
				case "add":
					this.status = JobStatus.ADD;
					break;
				case "delete":
					this.status = JobStatus.DELETE;
					break;
				case "update":
					this.status = JobStatus.UPDATE;
					break;
				case "start":
					this.status = JobStatus.START;
					break;
				case "stop":
					this.status = JobStatus.STOP;
					break;
				}
			}
		}

		public String getJobGroup() {
			return jobGroup;
		}

		public void setJobGroup(String jobGroup) {
			this.jobGroup = jobGroup;
		}

		public String getTriger() {
			return triger;
		}

		public void setTriger(String triger) {
			this.triger = triger;
		}

		public String getTrigerGroup() {
			return trigerGroup;
		}

		public void setTrigerGroup(String trigerGroup) {
			this.trigerGroup = trigerGroup;
		}
	}

	public enum JobStatus {
		ADD("添加", "add"), DELETE("删除", "delete"), UPDATE("修改", "update"), START("启动", "start"), STOP("暂停", "stop");
		private JobStatus(String key, String value) {
			this.key = key;
			this.value = value;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String key;
		public String value;
	}

}
