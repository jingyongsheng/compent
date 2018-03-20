package com.zcbl.compent.task.client.register;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.zcbl.compent.task.client.CompentClient;
import com.zcbl.compent.task.client.factory.TaskClientFactory;
import com.zcbl.compent.task.client.global.bean.Global;
import com.zcbl.malaka.rpc.common.annation.Malaka;
import com.zcbl.malaka.rpc.common.annation.Url;
import com.zcbl.malaka.rpc.common.url.Request;
import com.zcbl.malaka.rpc.common.url.Response;

@Malaka("broadCast")
public class BroadReceiveImpl {
	ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

	@Url("/receive")
	public void request(Request request, Response response) {
		String card = request.getParamter("card");
		Global global = TaskClientFactory.getInstance().getConfig().getGlobal();
		if (card != null && (global.getCard().equals(card) || card.equals("all"))) {
			singleThreadExecutor.execute(new Runnable() {
				public void run() {
					try {
						CompentClient.getInstance().run();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			response.setParamter("result", "success");
		} else {
			response.setParamter("result", "message dropping");
		}
	}
}