package com.zcbl.compent.db.pool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.apache.log4j.Logger;

/**
 * @author jys 2016年11月8日
 */
public class JdbcUtil {
	private static Logger logger = Logger.getLogger(JdbcUtil.class);

	private JdbcUtil() {
	}

	private static JdbcUtil jdbcUtil = new JdbcUtil();

	public static JdbcUtil getInstance() {
		return jdbcUtil;
	}

	private DaoJdbc daoJdbc = new DaoJdbcimpl();

	public DaoJdbc getDaoJdbc() {
		return daoJdbc;
	}

	public Connection getConnection(String name) {
		Connection conn = null;
		try {
			conn = DBConnectionManager.getInstance().getConnection(name);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return conn;
	}

	public void close(String name, ResultSet rs, Statement satement, Connection con) {
		daoJdbc.closeAll(name, rs, satement, con);
	}

	@SuppressWarnings("unchecked")
	public <E> E query(String name, String sql, Object... obj) {
		ResultSet rs = daoJdbc.queryAll(name, sql, getConnection(name), obj);
		try {
			return (E) getResultList(rs);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				close(name, rs, rs.getStatement(), rs.getStatement().getConnection());
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
			}
			if (logger.isInfoEnabled())
				logger.info("!");
		}
		return null;
	}

	public ResultSet getResultSet(String name, String sql, Object... obj) {
		ResultSet rs = daoJdbc.queryAll(name, sql, getConnection(name), obj);
		return rs;
	}

	public int executeUpdate(String name, String sql, Object... obj) {
		int k = 0;
		k = getDaoJdbc().executeUpdate(name, sql, getConnection(name), obj);
		return k;
	}

	public int count(String name, String sql, Object... obj) {
		ResultSet rs = getResultSet(name, sql, obj);
		try {
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				close(name, rs, rs.getStatement(), rs.getStatement().getConnection());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	public int executeBatchUpdate(String name, Queue<String> sqls, Object... obj) {
		int k = 0;
		k = getDaoJdbc().executeBatchUpdate(name, sqls, getConnection(name), obj);
		return k;
	}

	private Map<String, Object> getResultMap(ResultSet rs) throws SQLException {
		Map<String, Object> rawMap = new HashMap<String, Object>();
		ResultSetMetaData rsmd = rs.getMetaData();
		int count = rsmd.getColumnCount();
		for (int i = 1; i <= count; i++) {
			String key = rsmd.getColumnLabel(i);
			Object value = rs.getObject(key);
			rawMap.put(key, value);
		}
		return rawMap;
	}

	private List<Map<String, Object>> getResultList(ResultSet rs) throws SQLException {
		List<Map<String, Object>> rawList = new ArrayList<Map<String, Object>>();
		while (rs.next()) {
			Map<String, Object> rawMap = getResultMap(rs);
			rawList.add(rawMap);
		}
		return rawList;
	}

}