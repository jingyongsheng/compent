package com.zcbl.compent.task.client.task.api;

import com.zcbl.compent.task.client.channel.api.Channel;

public interface Task
{
	public void excuteTask(Channel channel, String card);
}
