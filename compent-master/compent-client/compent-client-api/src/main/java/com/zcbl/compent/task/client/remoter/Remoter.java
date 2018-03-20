package com.zcbl.compent.task.client.remoter;

import com.zcbl.compent.task.client.channel.api.Channel;
import com.zcbl.compent.task.client.global.bean.Global;

public interface Remoter
{
	public Channel getChannel(Global global);
}
