package com.zcbl.compent.db.pool;

import java.io.Serializable;

/**
 * @author jys
 * 2016年11月8日
 */
public class DataSource implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String maxconn;
	public String minconn;
	public String type;
	public String url;
	public String driver;
	public String username;
	public String password;
	public String id;

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getMaxconn()
	{
		return maxconn;
	}

	public void setMaxconn(String maxconn)
	{
		this.maxconn = maxconn;
	}

	public String getMinconn()
	{
		return minconn;
	}

	public void setMinconn(String minconn)
	{
		this.minconn = minconn;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getDriver()
	{
		return driver;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public void setDriver(String driver)
	{
		this.driver = driver;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

}
