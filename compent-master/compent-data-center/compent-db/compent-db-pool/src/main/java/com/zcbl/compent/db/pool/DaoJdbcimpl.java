package com.zcbl.compent.db.pool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Queue;

/**
 * @author jys 2016年11月8日
 */
public class DaoJdbcimpl implements DaoJdbc {

	public boolean closeAll(String name, ResultSet rst, Statement stmt, Connection conn) {
		boolean flag = false;
		if (rst != null) {
			closeResultSet(rst);
		}
		if (stmt != null) {
			closeStatement(stmt);
		}
		if (conn != null) {
			closeConnection(name, conn);
			flag = true;
		}
		return flag;
	}

	public boolean closeConnection(String name, Connection conn) {
		if (conn != null) {
			DBConnectionManager.getInstance().freeConnection(name, conn);
			return true;
		}
		return false;
	}

	public boolean closeResultSet(ResultSet rst) {
		if (rst != null) {
			try {
				rst.close();
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public boolean closeStatement(Statement stmt) {
		if (stmt != null) {
			if (stmt instanceof PreparedStatement) {
				try {
					((PreparedStatement) stmt).close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * @param sql
	 * @param obj
	 * @return
	 */
	public int executeUpdate(String name, String sql, Connection conn, Object... obj) {
		int i = 0;
		PreparedStatement psts = null;
		try {
			validateConn(name, conn);
			conn.setAutoCommit(false);
			psts = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			if (obj != null && obj.length > 0) {
				for (int j = 0; j < obj.length; j++) {
					psts.setObject((j + 1), obj[j]);
				}
			}
			i = psts.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
			closeAll(name, null, psts, conn);
		}
		return i;
	}

	/**
	 * 
	 * @param sql
	 * @param obj
	 * @return ResultSet
	 */

	public ResultSet queryAll(String name, String sql, Connection conn, Object... obj) {
		PreparedStatement psts = null;
		ResultSet rs = null;
		try {
			validateConn(name, conn);
			psts = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			if (obj != null && obj.length > 0) {
				for (int j = 0; j < obj.length; j++) {
					psts.setObject((j + 1), obj[j]);
				}
			}
			rs = psts.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	@Override
	public int executeBatchUpdate(String name, Queue<String> values, Connection conn, Object... object) {
		int[] i = null;
		Statement state = null;
		try {
			validateConn(name, conn);
			conn.setAutoCommit(false);
			state = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			for (String sql : values) {
				state.addBatch(sql);
			}
			i = state.executeBatch();
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
			closeAll(name, null, state, conn);
		}
		if (i == null)
			return 0;
		return i.length;
	}

	@Override
	public int executePreparBatchUpdate(String name, List<Queue<String>> values, Connection conn, String sql) {
		int[] i = null;
		PreparedStatement state = null;
		try {
			validateConn(name, conn);
			conn.setAutoCommit(false);
			state = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			for (Queue<String> queue : values) {
				int k = 0;
				for (String str : queue) {
					k++;
					state.setString(k, str);
				}
				state.addBatch();
			}
			i = state.executeBatch();
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
			closeAll(name, null, state, conn);
		}
		return i.length;
	}

	private void validateConn(String name, Connection connection) {
		if (connection == null)
			return;
		try {
			if (connection.isClosed()) {
				DBConnectionManager.getInstance().closeConnection(name, connection);
				connection = DBConnectionManager.getInstance().getConnection(name);
				validateConn(name, connection);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
	}
}