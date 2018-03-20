package com.zcbl.compent.client.remoter.http;

import java.util.List;

import com.zcbl.compent.task.client.channel.api.Channel;

public class HttpImpl implements Channel
{
	private static HttpImpl impl = new HttpImpl();

	public static HttpImpl getInstance()
	{
		return impl;
	}

	@Override
	public List<String> getData(String card)
	{
		return null;
	}
	

}
