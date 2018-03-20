package com.zcbl.modules.component.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zcbl.common.service.CrudService;
import com.zcbl.common.utils.SpringContextHolder;
import com.zcbl.malaka.rpc.client.context.Route;
import com.zcbl.malaka.rpc.common.Malaka;
import com.zcbl.malaka.rpc.common.url.Response;
import com.zcbl.modules.component.dao.CompentTaskDao;
import com.zcbl.modules.component.entity.CompentTask;
import com.zcbl.modules.log.entity.CompentLog;
import com.zcbl.modules.log.service.ComponetLogService;

@Service
public class ComponetService extends CrudService<CompentTaskDao, CompentTask>
{
	ComponetLogService log = SpringContextHolder.getBean(ComponetLogService.class);

	@Transactional(readOnly = false)
	public void save(final CompentTask task)
	{
		Date date = new Date();
		task.setCreateTime(date);
		task.setUpdateTime(date);
		super.save(task);
		BrodCast cast = new BrodCast(task);
		cast.start();
	}

	private class BrodCast extends Thread
	{
		CompentTask task;

		public BrodCast(CompentTask task)
		{
			this.task = task;
		}

		public void run()
		{
			Map<String, String> map = new HashMap<String, String>();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d = task.getCreateTime();
			String f = format.format(d);
			map.put("card", task.getCard());
			map.put("type", task.getType());
			map.put("date", f);
			Response response = Malaka.remote("broadCast/receive").request(map).route(Route.BROADCAST).result();
			try
			{
				CompentLog l = new CompentLog();
				l.setTitle(task.getTitle());
				l.setContent(response.getParamter("result"));
				l.setCard(task.getCard());
				l.setType("push");
				log.save(l);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public CompentTask get(String id)
	{
		return super.get(id);
	}
}
