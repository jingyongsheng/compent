package com.zcbl.modules.component.dao;

import java.util.List;

import com.zcbl.common.persistence.CrudDao;
import com.zcbl.common.persistence.annotation.MyBatisDao;
import com.zcbl.modules.component.entity.CompentTask;

@MyBatisDao
public interface CompentTaskDao extends CrudDao<CompentTask>
{

	// public int save(CompentTask task);
	//
	// public int update(CompentTask task);
	//
	// public CompentTask get(int id);
	public List<CompentTask> findListByCard(CompentTask task);
}