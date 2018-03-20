package com.zcbl.compent.client.global;

import java.util.List;
import java.util.Map;

import com.zcbl.compent.config.ConfigClient;
import com.zcbl.compent.task.client.annation.GlobalCompent;
import com.zcbl.compent.task.client.global.api.GlobalConfig;
import com.zcbl.compent.task.client.global.bean.Global;
import com.zcbl.compent.task.client.global.bean.Transport;

@GlobalCompent
public class GlobalImpl implements GlobalConfig {
	static Global global;
	public static byte[] b = new byte[0];

	public Global getGlobal() {
		synchronized (b) {
			if (global == null)
				global = new Global();
			List<Map<String, Object>> list = ConfigClient.getGlobal().getGlobalByType("register");
			if (list == null || list.isEmpty()) {
				return global;
			}
			for (Map<String, Object> m : list) {
				if (m != null && !m.isEmpty()) {
					String app = (String) m.get("app");
					String transport = (String) m.get("transport");
					String server = (String) m.get("server");
					String id = (String) m.get("unique");
					String port = (String) m.get("port");
					String ip = (String) m.get("ip");
					global.setIp(ip);
					global.setServer(server);
					global.setApp(app);
					global.setCard(id);
					global.setTransport(getTransport(transport));
					global.setPort(port);
				}
			}
		}
		return global;
	}

	public static Transport getTransport(String key) {
		switch (key) {
		case "db":
			return Transport.DB;
		case "rpc":
			return Transport.RPC;
		case "http":
			return Transport.HTTP;
		case "memory":
			return Transport.MEMORY;
		default:
			return Transport.DB;
		}
	}
}