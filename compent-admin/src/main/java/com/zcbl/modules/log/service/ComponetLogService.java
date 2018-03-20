package com.zcbl.modules.log.service;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zcbl.common.service.CrudService;
import com.zcbl.modules.log.dao.CompentLogDao;
import com.zcbl.modules.log.entity.CompentLog;

@Service
public class ComponetLogService extends CrudService<CompentLogDao, CompentLog>
{

	@Transactional(readOnly = false)
	public void save(CompentLog task)
	{
		Date date = new Date();
		task.setCreateTime(date);
		task.setUpdateTime(date);
		super.save(task);
	}

	public CompentLog get(String id)
	{
		return super.get(id);
	}
}
