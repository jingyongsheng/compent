package com.zcbl.compent.db.pool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Queue;

/**
 * @author jys
 * 2016年11月8日
 */
public interface DaoJdbc
{

	/**
	 * 
	 * @param conn
	 * @param stmt
	 * @param rst
	 * @return
	 */
	public boolean closeAll(String name, ResultSet rst, Statement stmt, Connection conn);

	/**
	 * @param conn
	 * @return
	 */
	public boolean closeConnection(String name, Connection conn);

	/**
	 * @param stmt
	 * @return
	 */
	public boolean closeStatement(Statement stmt);

	/**
	 * @param rst
	 * @return
	 */
	public boolean closeResultSet(ResultSet rst);

	/**
	 * 
	 * @param sql
	 * @param conn
	 * @param obj
	 * @return
	 */
	public int executeUpdate(String name, String sql, Connection conn, Object... obj);

	public int executeBatchUpdate(String name, Queue<String> values, Connection conn, Object... obj);

	public int executePreparBatchUpdate(String name, List<Queue<String>> values, Connection conn, String sql);

	/**
	 * 
	 * @param sql
	 * @param conn
	 * @param obj
	 * @return
	 */
	public ResultSet queryAll(String name, String sql, Connection conn, Object... obj);

}