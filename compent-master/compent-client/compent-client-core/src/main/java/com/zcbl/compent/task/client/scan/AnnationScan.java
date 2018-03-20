package com.zcbl.compent.task.client.scan;

import java.util.Set;

import com.zcbl.compent.task.client.annation.AnysisCompent;
import com.zcbl.compent.task.client.annation.GlobalCompent;
import com.zcbl.compent.task.client.annation.SysLoader;
import com.zcbl.compent.task.client.annation.RemoterCompent;
import com.zcbl.compent.task.client.annation.TaskCompent;
import com.zcbl.compent.task.client.factory.TaskClientFactory;

public class AnnationScan {
	public final static String pack = "com.zcbl.compent";

	public static void scan() {
		Set<Class<?>> set = ClassUtils.getClasses(pack);
		if (set != null && !set.isEmpty()) {
			for (Class c : set) {
				try {
					if (c != null && null != c.getAnnotation(RemoterCompent.class)) {
						if (TaskClientFactory.getInstance().getRemoter() != null) {
							throw new Exception("more remoter instance!");
						}
						TaskClientFactory.getInstance()
								.setRemoter((com.zcbl.compent.task.client.remoter.Remoter) c.newInstance());
					} else if (c != null && null != c.getAnnotation(GlobalCompent.class)) {
						if (TaskClientFactory.getInstance().getConfig() != null) {
							throw new Exception("more config instance!");
						}
						TaskClientFactory.getInstance()
								.setConfig((com.zcbl.compent.task.client.global.api.GlobalConfig) c.newInstance());
					} else if (c != null && null != c.getAnnotation(AnysisCompent.class)) {
						TaskClientFactory.getInstance()
								.setAnysis((com.zcbl.compent.task.client.ansyis.api.Anysis) c.newInstance());
					} else if (c != null && null != c.getAnnotation(TaskCompent.class)) {
						TaskClientFactory.getInstance()
								.setTask((com.zcbl.compent.task.client.task.api.Task) c.newInstance());
					} else if (c != null && null != c.getAnnotation(SysLoader.class)) {
						TaskClientFactory.getInstance()
								.setLoader((com.zcbl.compent.task.client.sys.Loader) c.newInstance());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
