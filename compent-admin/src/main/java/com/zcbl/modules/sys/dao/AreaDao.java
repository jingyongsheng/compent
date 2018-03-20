package com.zcbl.modules.sys.dao;

import com.zcbl.common.persistence.TreeDao;
import com.zcbl.common.persistence.annotation.MyBatisDao;
import com.zcbl.modules.sys.entity.Area;

/**
 * 区域DAO接口

 * @version 2014-05-16
 */
@MyBatisDao
public interface AreaDao extends TreeDao<Area> {
	
}
