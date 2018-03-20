package com.zcbl.compent.language.respertories;

import java.util.HashMap;
import java.util.Map;

public class Input
{
	Map<String, Object> user = new HashMap<String, Object>();
	Map<String, Object> sys = new HashMap<String, Object>();

	public Map<String, Object> getUser()
	{
		return user;
	}

	public void setUser(Map<String, Object> user)
	{
		this.user = user;
	}

	public Map<String, Object> getSys()
	{
		return sys;
	}

	public void setSys(Map<String, Object> sys)
	{
		this.sys = sys;
	}

}
