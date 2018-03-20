package com.zcbl.compent.task.client;

import java.util.List;

import com.zcbl.compent.config.factory.ConfigFactory;
import com.zcbl.compent.data.center.log.Log;
import com.zcbl.compent.data.center.log.factory.LogCompent;
import com.zcbl.compent.task.client.ansyis.api.Anysis;
import com.zcbl.compent.task.client.channel.api.Channel;
import com.zcbl.compent.task.client.factory.TaskClientFactory;
import com.zcbl.compent.task.client.global.bean.Global;
import com.zcbl.compent.task.client.remoter.Remoter;
import com.zcbl.compent.task.client.scan.AnnationScan;
import com.zcbl.compent.task.client.sys.Loader;
import com.zcbl.compent.task.client.task.api.Task;

public class CompentClient {
	static Log log = LogCompent.getInstance().getLog(CompentClient.class);
	private static CompentClient client = new CompentClient();

	public static CompentClient getInstance() {
		return client;
	}

	private CompentClient() {
		AnnationScan.scan();
		ConfigFactory.anysis();
	}

	public String run() {
		try {
			List<Loader> list = TaskClientFactory.getInstance().getLoader();
			if (!list.isEmpty()) {
				for (Loader loader : list) {
					loader.load();
				}
			}
			Global global = TaskClientFactory.getInstance().getConfig().getGlobal();
			if (global == null) {
				String message = "ths golbal instance is null";
				log.error(message);
				return message;
			}
			Remoter remoter = TaskClientFactory.getInstance().getRemoter();
			if (remoter == null) {
				String message = "the register instance  is null";
				log.error(message);
				return message;
			}
			Channel channel = remoter.getChannel(global);
			List<Task> task = TaskClientFactory.getInstance().getTask();
			if (task == null || task.isEmpty()) {
				String message = "the tasks instance is null";
				log.error(message);
				return message;
			}
			for (Task t : task) {
				t.excuteTask(channel, global.getCard());
			}
			List<Anysis> sis = TaskClientFactory.getInstance().getAnysis();
			if (sis == null || sis.isEmpty()) {
				String message = "the task anysis instance is null";
				log.error(message);
				return message;
			}
			for (Anysis t : sis) {
				t.anysis();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return "succcess";
	}
}
