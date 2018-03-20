package com.zcbl.compent.restful.manage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.zcbl.compent.restful.bridge.Resolver;
import com.zcbl.compent.restful.bridge.Url;

/**
 * @author jys 2016年11月10日
 */
public class CompentUrlManager
{
	private static CompentUrlManager cache = new CompentUrlManager();
	private Map<String, Url> map = new ConcurrentHashMap<String, Url>();
	private Resolver resolver = null;

	public static CompentUrlManager getInstance()
	{
		return cache;
	}

	private CompentUrlManager()
	{
	}

	public Resolver getResolver()
	{
		return resolver;
	}

	public void setResolver(Resolver resolver)
	{
		this.resolver = resolver;
	}

	public void addUrl(Url u)
	{
		map.put(u.getUrl(), u);
	}

	public Url getUrl(String url)
	{
		return map.get(url);
	}

	public void clear()
	{
		this.map.clear();
	}
}
