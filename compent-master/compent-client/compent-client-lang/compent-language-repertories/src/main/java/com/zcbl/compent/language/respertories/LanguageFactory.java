package com.zcbl.compent.language.respertories;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.zcbl.compent.language.respertories.expression.Lang;

public class LanguageFactory
{
	private static LanguageFactory factory = new LanguageFactory();

	private LanguageFactory()
	{
	}

	public static LanguageFactory getInstance()
	{
		return factory;
	}

	public Map<String, Lang> map = new ConcurrentHashMap<String, Lang>();

	public void addLang(String key, Lang l)
	{
		map.put(key, l);
	}

	public Lang getLang(String key)
	{
		return map.get(key);
	}

	public Map<String, Lang> getLang()
	{
		return map;
	}
}
