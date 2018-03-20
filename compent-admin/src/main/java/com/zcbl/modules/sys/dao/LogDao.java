package com.zcbl.modules.sys.dao;

import com.zcbl.common.persistence.CrudDao;
import com.zcbl.common.persistence.annotation.MyBatisDao;
import com.zcbl.modules.sys.entity.Log;

/**
 * 日志DAO接口

 * @version 2014-05-16
 */
@MyBatisDao
public interface LogDao extends CrudDao<Log> {

}
