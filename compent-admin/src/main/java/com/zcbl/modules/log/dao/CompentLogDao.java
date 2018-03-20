package com.zcbl.modules.log.dao;

import java.util.List;

import com.zcbl.common.persistence.CrudDao;
import com.zcbl.common.persistence.annotation.MyBatisDao;
import com.zcbl.modules.log.entity.CompentLog;

@MyBatisDao
public interface CompentLogDao extends CrudDao<CompentLog>{

	public List<CompentLog> findListByCard(CompentLog task);
}