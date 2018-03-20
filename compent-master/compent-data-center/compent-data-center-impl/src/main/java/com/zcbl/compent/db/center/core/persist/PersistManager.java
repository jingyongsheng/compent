package com.zcbl.compent.db.center.core.persist;

import java.util.List;
import java.util.Map;

import com.zcbl.compent.db.client.DbClient;

public class PersistManager {
	public static int save(String dataSource, String sql, Object[] obj) {
		if (dataSource == null || sql == null || sql.equals(""))
			return -1;
		return DbClient.getInstance().save(dataSource, sql, obj);
	}

	public static int update(String dataSource, String sql, Object[] obj) {
		if (dataSource == null || sql == null || sql.equals(""))
			return -1;
		return DbClient.getInstance().update(dataSource, sql, obj);
	}

	public static List<Map<String, Object>> query(String dataSource, String sql, Object[] obj) {
		if (dataSource == null || sql == null || sql.equals(""))
			return null;
		try {
			return DbClient.getInstance().query(dataSource, sql, obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static int count(String dataSource, String sql, Object[] obj) {
		if (dataSource == null || sql == null || sql.equals(""))
			return -1;
		try {
			return DbClient.getInstance().count(dataSource, sql, obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
