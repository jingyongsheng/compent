package com.zcbl.compent.client.remoter.memory;

import java.util.List;

import com.zcbl.compent.task.client.channel.api.Channel;

public class MemoryImpl implements Channel
{
	private static MemoryImpl impl = new MemoryImpl();

	public static MemoryImpl getInstance()
	{
		return impl;
	}

	@Override
	public List<String> getData(String card)
	{
		return null;
	}

}
