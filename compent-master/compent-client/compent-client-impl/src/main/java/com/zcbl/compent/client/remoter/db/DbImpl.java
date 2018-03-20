package com.zcbl.compent.client.remoter.db;

import java.util.List;

import com.zcbl.compent.task.client.channel.api.Channel;

public class DbImpl implements Channel
{
	private static DbImpl impl = new DbImpl();

	public static DbImpl getInstance()
	{
		return impl;
	}

	@Override
	public List<String> getData(String card)
	{
		return null;
	}
	

}
