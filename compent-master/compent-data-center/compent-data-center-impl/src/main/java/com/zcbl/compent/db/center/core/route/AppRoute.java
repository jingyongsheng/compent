package com.zcbl.compent.db.center.core.route;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AppRoute
{
	public String appName;
	public Map<String, EntityRoute> route = new ConcurrentHashMap<String, EntityRoute>();

	public String getAppName()
	{
		return appName;
	}

	public void setAppName(String appName)
	{
		this.appName = appName;
	}

	public Map<String, EntityRoute> getRoute()
	{
		return route;
	}

	public void setRoute(Map<String, EntityRoute> route)
	{
		this.route = route;
	}

}
