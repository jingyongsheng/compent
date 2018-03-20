package com.zcbl.compent.client.ansyis.compent;

import java.util.Map;

import com.zcbl.compent.config.ConfigClient;
import com.zcbl.compent.data.center.log.Log;
import com.zcbl.compent.data.center.log.factory.LogCompent;
import com.zcbl.compent.language.respertories.CompentFactory;
import com.zcbl.compent.task.client.annation.AnysisCompent;
import com.zcbl.compent.task.client.ansyis.api.Anysis;

@AnysisCompent
public class CompentImpl implements Anysis {
	static Log log = LogCompent.getInstance().getLog(CompentImpl.class);

	@Override
	public void anysis() {
		Map<String, Map<String, Object>> map = ConfigClient.getGlobal().getGlobal();
		CompentFactory.getInstance().setCompent(map);
	}
}