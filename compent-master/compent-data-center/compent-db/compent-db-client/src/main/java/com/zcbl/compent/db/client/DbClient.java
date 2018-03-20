package com.zcbl.compent.db.client;

import java.util.List;
import java.util.Map;

import com.zcbl.compent.db.pool.JdbcUtil;

/**
 * @author jys 2016年11月8日
 */
public class DbClient {
	private static DbClient client = new DbClient();

	private DbClient() {
	}

	public static DbClient getInstance() {
		return client;
	}

	public int save(String sourceName, String sql, Object[] obj) {
		return JdbcUtil.getInstance().executeUpdate(sourceName, sql, obj);
	}

	public int update(String sourceName, String sql, Object[] obj) {
		return JdbcUtil.getInstance().executeUpdate(sourceName, sql, obj);
	}

	public List<Map<String, Object>> query(String sourceName, String sql, Object[] obj) throws Exception {
		return JdbcUtil.getInstance().query(sourceName, sql, obj);
	}

	public int count(String sourceName, String sql, Object[] obj) {
		return JdbcUtil.getInstance().count(sourceName, sql, obj);
	}
}
