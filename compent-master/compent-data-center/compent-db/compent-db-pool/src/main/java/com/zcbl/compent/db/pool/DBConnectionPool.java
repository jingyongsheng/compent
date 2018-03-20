package com.zcbl.compent.db.pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jys 2016年11月8日
 */
public class DBConnectionPool {
	private AtomicInteger sessions = new AtomicInteger(0);
	private Queue<Connection> freeConnections = new ConcurrentLinkedQueue<Connection>();
	private int minConn;
	private int maxConn;
	private String name;
	private String password;
	private String url;
	private String driver;
	private String user;
	private Timer timer;
	private boolean start = false;
	private byte[] b = new byte[0];
	private byte[] t = new byte[0];

	/** 
	  * 
	  */
	public DBConnectionPool() {
		// TODO Auto-generated constructor stub
	}

	public void initPool() {
		if (minConn > 0) {
			for (int i = 0; i < minConn; i++) {
				Connection con = newConnection();
				addSessions(con);
			}
		}
		TimerEvent();
	}

	public void addSessions(Connection con) {
		if (con != null) {
			freeConnections.add(con);
			this.sessions.getAndIncrement();
		}
	}

	public void closeSessions(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				freeConnections.remove(con);
				this.sessions.getAndDecrement();
				con = null;
			}
		}
	}

	/**
	 * 
	 * @param driver
	 * @param name
	 * @param URL
	 * @param user
	 * @param password
	 * @param maxConn
	 */
	public DBConnectionPool(String name, String driver, String URL, String user, String password, int maxConn) {
		this.name = name;
		this.driver = driver;
		this.url = URL;
		this.user = user;
		this.password = password;
		this.maxConn = maxConn;
	}

	/**
	 * 
	 * @param con
	 */
	public void freeConnection(Connection con) {
		try {
			if (!con.isClosed() && con != null) {
				this.freeConnections.add(con);
			} else {
				closeSessions(con);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param timeout
	 * @return
	 */
	public Connection getConnection() {
		Connection con = null;
		if (!this.freeConnections.isEmpty()) {
			con = (Connection) this.freeConnections.poll();
			if (con == null)
				con = getConnection();
			else {
				return con;
			}
		} else if (this.maxConn == 0) {
			return null;
		} else {
			if (this.maxConn > sessions.get()) {
				synchronized (b) {
					if (this.maxConn > sessions.get()) {
						con = newConnection();
						if (con != null)
							this.sessions.getAndIncrement();
					}
				}
			}
			if (con == null) {
				try {
					Thread.sleep(300);
				} catch (Exception e) {
					e.printStackTrace();
				}
				con = getConnection();
			}
		}
		return con;
	}

	/**
	 * 
	 * 
	 * @return
	 */
	public Connection getConnection(Long timeout) {
		Connection con = null;
		if (!this.freeConnections.isEmpty()) {
			con = (Connection) this.freeConnections.poll();
			if (con == null) {
				con = getConnection(timeout);
			} else {
				return con;
			}
		} else if (this.maxConn == 0) {
			return null;
		} else {
			if (this.maxConn > sessions.get()) {
				synchronized (b) {
					if (this.maxConn > sessions.get()) {
						con = newConnection();
						if (con != null)
							this.sessions.getAndIncrement();
					}
				}
			}
			if (con == null) {
				try {
					Thread.sleep(300);
				} catch (Exception e) {
					e.printStackTrace();
				}
				con = getConnection(timeout);
			}
		}
		return con;
	}

	/**
	 * 
	 */
	public void release() {
		Iterator<Connection> allConns = this.freeConnections.iterator();
		while (allConns.hasNext()) {
			Connection con = (Connection) allConns.next();
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				closeSessions(con);
			}
		}
		this.freeConnections.clear();

	}

	/**
	 * 
	 * @return
	 */
	private Connection newConnection() {
		Connection con = null;
		try {
			Class.forName(driver);
			java.util.Properties prop = new java.util.Properties();
			prop.put("user", user);
			prop.put("password", password);
			if (driver.equals("oracle.jdbc.driver.OracleDriver")) {
				prop.put("oracle.jdbc.V8Compatible", "true");
			}
			con = DriverManager.getConnection(url, prop);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return con;
	}

	/**
	 */
	public void TimerEvent() {
		if (!start)
			synchronized (t) {
				if (!start) {
					start = true;
					timer = new Timer();
					timer.schedule(new TimerTask() {
						public void run() {
							if (!freeConnections.isEmpty()) {
								for (int i = 0; i < maxConn + 1; i++) {
									Connection c = freeConnections.poll();
									if (c != null) {
										ResultSet rs = null;
										Statement stmt = null;
										try {
											c.setAutoCommit(true);
											stmt = c.createStatement();
											rs = stmt.executeQuery("SELECT 1 FROM DUAL");
											rs.next();
										} catch (Exception e) {
											closeSessions(c);
										} finally {
											JdbcUtil.getInstance().close(name, rs, stmt, c);
										}
									}
								}
							}
						}
					}, 1000, 1000000);
				}
			}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	}

	/**
	 * @return the driver
	 */
	public String getDriver() {
		return driver;
	}

	/**
	 * @param driver
	 *            the driver to set
	 */
	public void setDriver(String driver) {
		this.driver = driver;
	}

	/**
	 * @return the maxConn
	 */
	public int getMaxConn() {
		return maxConn;
	}

	/**
	 * @param maxConn
	 *            the maxConn to set
	 */
	public void setMaxConn(int maxConn) {
		this.maxConn = maxConn;
	}

	/**
	 * @return the minConn
	 */
	public int getMinConn() {
		return minConn;
	}

	/**
	 * @param minConn
	 *            the minConn to set
	 */
	public void setMinConn(int minConn) {
		this.minConn = minConn;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

}