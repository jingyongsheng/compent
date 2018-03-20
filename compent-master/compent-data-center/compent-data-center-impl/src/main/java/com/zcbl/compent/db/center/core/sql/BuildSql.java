package com.zcbl.compent.db.center.core.sql;

import com.zcbl.compent.db.center.core.entity.Entity;

/**
 * @author jys 2016年11月8日
 */
public interface BuildSql
{
	public String toSaveSql(Entity entity);

	public String toUpdateSql(Entity entity);

	public String toQuerySql(Entity entity);

	public String toCountSql(Entity entity);

}
