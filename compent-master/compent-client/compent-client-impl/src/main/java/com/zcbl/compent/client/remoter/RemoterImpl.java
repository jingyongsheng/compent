package com.zcbl.compent.client.remoter;

import com.zcbl.compent.client.remoter.db.DbImpl;
import com.zcbl.compent.client.remoter.http.HttpImpl;
import com.zcbl.compent.client.remoter.memory.MemoryImpl;
import com.zcbl.compent.client.remoter.rpc.RpcImpl;
import com.zcbl.compent.task.client.annation.RemoterCompent;
import com.zcbl.compent.task.client.channel.api.Channel;
import com.zcbl.compent.task.client.global.bean.Global;
import com.zcbl.compent.task.client.global.bean.Transport;
import com.zcbl.compent.task.client.remoter.Remoter;

@RemoterCompent
public class RemoterImpl implements Remoter
{

	public Channel getChannel(Global global)
	{
		Channel c = null;
		if (global != null)
		{
			if (global.getTransport().equals(Transport.RPC))
			{
				c = RpcImpl.getInstance(global);
			} else if (global.getTransport().equals(Transport.DB))
			{
				c = DbImpl.getInstance();
			} else if (global.getTransport().equals(Transport.MEMORY))
			{
				c = MemoryImpl.getInstance();
			} else if (global.getTransport().equals(Transport.HTTP))
			{
				c = HttpImpl.getInstance();
			}
		}
		return c;
	}
}