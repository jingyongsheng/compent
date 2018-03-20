package com.zcbl.compent.client.remoter.rpc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zcbl.compent.data.center.log.Log;
import com.zcbl.compent.data.center.log.factory.LogCompent;
import com.zcbl.compent.task.client.channel.api.Channel;
import com.zcbl.compent.task.client.global.bean.Global;
import com.zcbl.malaka.rpc.MalakaApplication;
import com.zcbl.malaka.rpc.common.Malaka;
import com.zcbl.malaka.rpc.common.url.Response;

public class RpcImpl implements Channel {
	static Log log = LogCompent.getInstance().getLog(RpcImpl.class);
	static MalakaApplication malaka;
	private static RpcImpl impl = new RpcImpl();
	static byte[] b = new byte[0];

	public static RpcImpl getInstance(Global g) {
		if (malaka == null) {
			synchronized (b) {
				if (malaka == null) {
					malaka = new MalakaApplication(g.getServer(), g.getPort(), g.getIp());
				}
			}
		}
		return impl;
	}

	@Override
	public List<String> getData(String card) {
		List<String> list = new ArrayList<String>();
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("card", card);
			Response response = Malaka.remote("compent/getCompent").request(map).result();
			list.add(response.getParamter("result"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
