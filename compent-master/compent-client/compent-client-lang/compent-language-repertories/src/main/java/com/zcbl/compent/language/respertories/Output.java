package com.zcbl.compent.language.respertories;

import java.util.HashMap;
import java.util.Map;

public class Output
{
	Map<String, Object> user = new HashMap<String, Object>();

	public Map<String, Object> getUser()
	{
		return user;
	}

	public void setUser(Map<String, Object> user)
	{
		this.user = user;
	}

	public void clear()
	{
		user.clear();
	}

}
