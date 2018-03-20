package com.zcbl.compent.db.center.core.route;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jys 2016年11月8日
 */
public class EntityRoute
{
	public String entityName;
	public String tableName;
	public Map<Method, String> map = new HashMap<Method, String>();
	public String key;
	


	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

	public Map<Method, String> getMap()
	{
		return map;
	}

	public void setMap(Map<Method, String> map)
	{
		this.map = map;
	}

	public String getTableName()
	{
		return tableName;
	}

	public void setTableName(String tableName)
	{
		this.tableName = tableName;
	}

	public String getEntityName()
	{
		return entityName;
	}

	public void setEntityName(String entityName)
	{
		this.entityName = entityName;
	}

}
