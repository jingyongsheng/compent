package com.zcbl.modules.sys.dao;

import java.util.List;

import com.zcbl.common.persistence.CrudDao;
import com.zcbl.common.persistence.annotation.MyBatisDao;
import com.zcbl.modules.sys.entity.Menu;

/**
 * 菜单DAO接口

 * @version 2014-05-16
 */
@MyBatisDao
public interface MenuDao extends CrudDao<Menu> {

	public List<Menu> findByParentIdsLike(Menu menu);

	public List<Menu> findByUserId(Menu menu);
	
	public int updateParentIds(Menu menu);
	
	public int updateSort(Menu menu);
	
}
