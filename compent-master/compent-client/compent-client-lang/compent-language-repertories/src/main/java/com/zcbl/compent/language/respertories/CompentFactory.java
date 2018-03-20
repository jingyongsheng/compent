package com.zcbl.compent.language.respertories;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CompentFactory
{
	private Map<String, Map<String, Object>> compent = new ConcurrentHashMap<String, Map<String, Object>>();

	private static CompentFactory factory = new CompentFactory();

	private CompentFactory()
	{
	}

	public static CompentFactory getInstance()
	{
		return factory;
	}

	public Map<String, Map<String, Object>> getCompent()
	{
		return compent;
	}

	public void setCompent(Map<String, Map<String, Object>> compent)
	{
		this.compent = compent;
	}

}
