package com.zcbl.compent.client.ansyis.timer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zcbl.compent.client.quartz.TimerStrategyFactory;
import com.zcbl.compent.client.quartz.TimerStrategyFactory.Job;
import com.zcbl.compent.config.ConfigClient;
import com.zcbl.compent.data.center.log.Log;
import com.zcbl.compent.data.center.log.factory.LogCompent;
import com.zcbl.compent.language.respertories.CommandFactory;
import com.zcbl.compent.task.client.annation.AnysisCompent;
import com.zcbl.compent.task.client.ansyis.api.Anysis;

@AnysisCompent
public class TimerAnsyisImpl implements Anysis {
	static Log log = LogCompent.getInstance().getLog(TimerAnsyisImpl.class);

	@Override
	public void anysis() {
		List<Map<String, Object>> list = ConfigClient.getGlobal().getGlobalByType("timer");
		if (list == null || list.isEmpty()) {
			return;
		}
		for (Map<String, Object> m : list) {
			if (m != null && !m.isEmpty()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.putAll(m);
				String id = (String) map.get("unique");
				String cron = (String) map.get("cron");
				String jobGroup = (String) map.get("job_group");
				String triger = (String) map.get("triger");
				String trigerGroup = (String) map.get("triger_group");
				String action = (String) map.get("action");
				String cmd = (String) m.get("cmd");
				if (cmd != null && !cmd.equals("")) {
					try {
						CommandFactory.getInstance().anysisCommand(id, cmd);
					} catch (Exception e) {
						log.error(e.getMessage());
					}
				}
				Job j = TimerStrategyFactory.getIntance().getJob(id);
				if (j == null)
					j = TimerStrategyFactory.getIntance().createJob();
				j.setJobGroup(jobGroup);
				j.setJobid(id);
				j.setStatus(action);
				j.setTriger(triger);
				j.setTrigerGroup(trigerGroup);
				j.setCron(cron);
				TimerStrategyFactory.getIntance().setTimer(j);
			}
		}
	}
}