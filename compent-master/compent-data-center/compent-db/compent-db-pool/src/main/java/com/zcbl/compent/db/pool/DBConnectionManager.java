package com.zcbl.compent.db.pool;

import java.sql.Connection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author jys 2016年11月8日
 */
public class DBConnectionManager {
	static private DBConnectionManager instance = new DBConnectionManager();
	Map<String, DataSource> dataSources;
	private Hashtable<String, DBConnectionPool> pools = new Hashtable<String, DBConnectionPool>();

	private DBConnectionManager() {
	}

	static public DBConnectionManager getInstance() {
		instance.init();
		return instance;
	}

	public void freeConnection(String name, Connection con) {
		DBConnectionPool pool = (DBConnectionPool) pools.get(name);
		if (pool != null)
			pool.freeConnection(con);
	}

	public void closeConnection(String name, Connection con) {
		DBConnectionPool pool = (DBConnectionPool) pools.get(name);
		if (pool != null)
			pool.closeSessions(con);
	}

	public Connection getConnection(String name) {
		DBConnectionPool pool = null;
		Connection con = null;
		pool = (DBConnectionPool) pools.get(name);
		if (pool == null) {
			DataSource datasource = dataSources.get(name);
			if (datasource == null)
				return null;
			else {
				this.createPools(datasource);
				pool = (DBConnectionPool) pools.get(name);
				if (pool == null)
					return null;
			}
		}
		con = pool.getConnection();
		return con;
	}

	/**
	 * 
	 * @param name
	 * @param time
	 * @return
	 */
	public Connection getConnection(String name, long timeout) {
		DBConnectionPool pool = null;
		Connection con = null;
		pool = (DBConnectionPool) pools.get(name);
		if (pool == null) {
			DataSource datasource = dataSources.get(name);
			if (datasource == null)
				return null;
			else {
				this.createPools(datasource);
				pool = (DBConnectionPool) pools.get(name);
				if (pool == null)
					return null;
			}
		}
		con = pool.getConnection(timeout);
		return con;
	}

	/**
	 */
	public void release() {
		Enumeration<DBConnectionPool> allpools = pools.elements();
		while (allpools.hasMoreElements()) {
			DBConnectionPool pool = (DBConnectionPool) allpools.nextElement();
			if (pool != null)
				pool.release();
		}
		pools.clear();
	}

	/**
	 * 
	 * @param props
	 */
	private void createPools(DataSource source) {
		DBConnectionPool dbpool = pools.get(source.getId());
		if (dbpool == null) {
			dbpool = new DBConnectionPool();
			dbpool.setName(source.getId());
			dbpool.setDriver(source.getDriver());
			dbpool.setUrl(source.getUrl());
			dbpool.setUser(source.getUsername());
			dbpool.setPassword(source.getPassword());
			dbpool.setMaxConn(Integer.parseInt(source.getMaxconn()));
			dbpool.setMinConn(Integer.parseInt(source.getMinconn()));
			dbpool.initPool();
			pools.put(source.getId(), dbpool);
		} else {
			dbpool.setName(source.getId());
			dbpool.setDriver(source.getDriver());
			dbpool.setUrl(source.getUrl());
			dbpool.setUser(source.getUsername());
			dbpool.setPassword(source.getPassword());
			dbpool.setMaxConn(Integer.parseInt(source.getMaxconn()));
			dbpool.setMinConn(Integer.parseInt(source.getMinconn()));
		}
	}

	private void init() {
		this.loadDrivers();
		Set<String> set = dataSources.keySet();
		Iterator<String> alldriver = set.iterator();
		while (alldriver.hasNext()) {
			DataSource source = dataSources.get(alldriver.next());
			this.createPools(source);
		}
	}

	/**
	 * 
	 * @param props
	 */
	private void loadDrivers() {
		dataSources = DataSourceCache.getInstance().getMap();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
}