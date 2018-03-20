package com.zcbl.compent.db.client.init;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.zcbl.compent.db.pool.DataSource;
import com.zcbl.compent.db.pool.DataSourceCache;

/**
 * @author jys 2016年11月8日
 */
public class InitPool
{

	private static InitPool pool = new InitPool();

	private InitPool()
	{
	}

	public static InitPool getInstance()
	{
		return pool;
	}

	public Jdbc buildJdbc(String id, String url, String driver, String username, String password, String type,
			String maxconn, String minconn, String status)
	{
		Jdbc jdbc = new Jdbc();
		jdbc.setDriver(driver);
		jdbc.setId(id);
		jdbc.setMaxconn(maxconn);
		jdbc.setMinconn(minconn);
		jdbc.setType(type);
		jdbc.setUrl(url);
		jdbc.setUsername(username);
		jdbc.setPassword(password);
		jdbc.setStatus(status);
		return jdbc;
	}

	public void init(Map<String, Jdbc> map)
	{
		if (map != null && !map.isEmpty())
		{
			Set<String> set = map.keySet();
			Iterator<String> ite = set.iterator();
			while (ite.hasNext())
			{
				String key = ite.next();
				Jdbc jdbc = map.get(key);
				DataSource source = new DataSource();
				source.setDriver(jdbc.getDriver());
				source.setId(jdbc.getId());
				source.setMaxconn(jdbc.getMaxconn());
				source.setMinconn(jdbc.getMinconn());
				source.setPassword(jdbc.getPassword());
				source.setType(jdbc.getType());
				source.setUrl(jdbc.getUrl());
				source.setUsername(jdbc.getUsername());
				DataSourceCache.getInstance().addService(source);
			}
		}
	}

	public class Jdbc
	{
		public String maxconn;
		public String minconn;
		public String type;
		public String url;
		public String driver;
		public String username;
		public String password;
		public String id;
		public String status;

		public String getStatus()
		{
			return status;
		}

		public void setStatus(String status)
		{
			this.status = status;
		}

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
}
