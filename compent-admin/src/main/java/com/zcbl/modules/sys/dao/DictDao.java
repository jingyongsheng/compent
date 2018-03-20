package com.zcbl.modules.sys.dao;

import java.util.List;

import com.zcbl.common.persistence.CrudDao;
import com.zcbl.common.persistence.annotation.MyBatisDao;
import com.zcbl.modules.sys.entity.Dict;

/**
 * 字典DAO接口

 * @version 2014-05-16
 */
@MyBatisDao
public interface DictDao extends CrudDao<Dict> {

	public List<String> findTypeList(Dict dict);
	
}
