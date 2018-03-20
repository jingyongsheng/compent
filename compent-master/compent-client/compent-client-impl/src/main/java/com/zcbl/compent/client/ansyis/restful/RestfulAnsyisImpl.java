package com.zcbl.compent.client.ansyis.restful;

import java.util.List;
import java.util.Map;

import com.zcbl.compent.config.ConfigClient;
import com.zcbl.compent.language.respertories.CommandFactory;
import com.zcbl.compent.language.respertories.TemplateFactory;
import com.zcbl.compent.restful.bridge.Url;
import com.zcbl.compent.restful.manage.CompentUrlManager;
import com.zcbl.compent.task.client.annation.AnysisCompent;
import com.zcbl.compent.task.client.ansyis.api.Anysis;

@AnysisCompent
public class RestfulAnsyisImpl implements Anysis {

	@Override
	public void anysis() {

		List<Map<String, Object>> list = ConfigClient.getGlobal().getGlobalByType("restful");
		if (list == null || list.isEmpty()) {
			return;
		}
		CompentUrlManager.getInstance().clear();
		for (Map<String, Object> m : list) {
			if (m != null && !m.isEmpty()) {
				String cmd = (String) m.get("cmd");
				m.remove("cmd");
				String t = (String) m.get("result_type");
				m.remove("result_type");
				String status = (String) m.get("status");
				m.remove("status");
				String content = (String) m.get("result_content");
				m.remove("result_content");
				String id = (String) m.get("unique");
				m.remove("unique");
				m.remove("card");
				try {
					Url uri = new Url(id, status, t, m);
					CompentUrlManager.getInstance().addUrl(uri);
					if (cmd != null && !cmd.equals(""))
						CommandFactory.getInstance().anysisCommand(id, cmd);
					if (content != null)
						TemplateFactory.getInstance().addTemplate(id, content);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}