package com.zcbl.modules.component.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zcbl.common.utils.SpringContextHolder;
import com.zcbl.malaka.rpc.common.annation.Malaka;
import com.zcbl.malaka.rpc.common.annation.Url;
import com.zcbl.malaka.rpc.common.url.Request;
import com.zcbl.malaka.rpc.common.url.Response;
import com.zcbl.modules.component.dao.CompentTaskDao;
import com.zcbl.modules.component.entity.CompentTask;

@Malaka("compent")
public class ComponetRpcService
{
	CompentTaskDao dao = SpringContextHolder.getBean(CompentTaskDao.class);
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Url("/getCompent")
	public void request(Request request, Response response)
	{
		String card = request.getParamter("card");
		CompentTask task = new CompentTask();
		task.setCard(card);
		List<CompentTask> taskList = dao.findListByCard(task);
		StringBuffer sb = new StringBuffer();
		sb.append("<zc-compent>");
		if (taskList != null && taskList.size() > 0)
		{
			for (CompentTask t : taskList)
			{
				sb.append("<compent>");
				sb.append("<compent_type>").append(t.getType().replaceAll(" ", "")).append("</compent_type>");
				sb.append("<unique>").append(t.getTitle().replaceAll(" ", "")).append("</unique>");
				sb.append(t.getContent());
				sb.append("<status>").append(t.getStatus().equals("1") ? "true" : "false").append("</status>");
				sb.append("<card>").append(t.getCard().replaceAll(" ", "")).append("</card>");
				sb.append("</compent>");
			}
		}
		sb.append("</zc-compent>");
		response.setParamter("result", sb.toString());
	}
}
