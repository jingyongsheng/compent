package com.zcbl.compent.language.respertories;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jys 2016年11月15日
 */
public class CmdFactory
{
	private static CmdFactory cmd = new CmdFactory();
	private Map<String, Cmd> user = new ConcurrentHashMap<String, Cmd>();
	private Map<String, Cmd> sys = new ConcurrentHashMap<String, Cmd>();
	private Map<String, String> keyMapping = new ConcurrentHashMap<String, String>();

	private CmdFactory()
	{
	}

	public static CmdFactory getInstance()
	{
		return cmd;
	}

	public Cmd getUserCmd(String name)
	{
		return user.get(name);
	}

	public Map<String, Cmd> getSysCmd()
	{
		return sys;
	}

	public void buildCmd(String key, String name)
	{
		Cmd cmd = user.get(name);
		if (cmd != null)
		{
			user.put(key, cmd);
			keyMapping.put(name, key);
		} else
		{
			cmd = this.sys.get(name);
			if (cmd != null)
			{
				this.sys.put(key, cmd);
				keyMapping.put(name, key);
			}
		}
	}

	public String getUserKey(String sysKey)
	{
		return keyMapping.get(sysKey);
	}

	public void addCmd(String key, Cmd cmd)
	{
		if (cmd instanceof SystemCompent)
		{
			this.sys.put(key, cmd);
		} else
		{
			this.user.put(key, cmd);
		}
	}

	public void clear()
	{
		user.clear();
		keyMapping.clear();
	}
}
