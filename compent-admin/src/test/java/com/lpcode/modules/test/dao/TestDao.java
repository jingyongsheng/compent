package com.lpcode.modules.test.dao;

import com.lpcode.modules.test.entity.Test;
import com.zcbl.common.persistence.CrudDao;
import com.zcbl.common.persistence.annotation.MyBatisDao;

/**
 * 测试DAO接口

 * @version 2013-10-17
 */
@MyBatisDao
public interface TestDao extends CrudDao<Test> {
	
}
