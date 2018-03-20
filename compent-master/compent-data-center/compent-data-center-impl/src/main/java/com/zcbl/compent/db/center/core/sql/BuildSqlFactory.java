package com.zcbl.compent.db.center.core.sql;

import com.zcbl.compent.db.center.core.sql.dialog.Mysql;

public class BuildSqlFactory
{
	public static BuildSql getSqlFactory()
	{
		BuildSql sql = Mysql.getInstance();
		return sql;
	}
}
