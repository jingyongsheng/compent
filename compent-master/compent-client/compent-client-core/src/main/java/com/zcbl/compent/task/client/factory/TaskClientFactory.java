package com.zcbl.compent.task.client.factory;

import java.util.ArrayList;
import java.util.List;

import com.zcbl.compent.task.client.ansyis.api.Anysis;
import com.zcbl.compent.task.client.global.api.GlobalConfig;
import com.zcbl.compent.task.client.remoter.Remoter;
import com.zcbl.compent.task.client.sys.Loader;
import com.zcbl.compent.task.client.task.api.Task;

/**
 * @author jys
 *
 */
public class TaskClientFactory
{
	private static TaskClientFactory factory = new TaskClientFactory();

	private TaskClientFactory()
	{
	}

	public static TaskClientFactory getInstance()
	{
		return factory;
	}

	private Remoter remoter;
	private GlobalConfig config;
	private List<Task> task = new ArrayList<Task>();
	private List<Anysis> anysis = new ArrayList<Anysis>();
	private List<Loader> loader = new ArrayList<Loader>();
	

	public Remoter getRemoter()
	{
		return remoter;
	}

	public void setRemoter(Remoter remoter)
	{
		this.remoter = remoter;
	}

	public List<Loader> getLoader()
	{
		return loader;
	}

	public void setLoader(Loader loader)
	{
		this.loader.add(loader);
	}

	public GlobalConfig getConfig()
	{
		return config;
	}

	public void setConfig(GlobalConfig config)
	{
		this.config = config;
	}

	public List<Task> getTask()
	{
		return task;
	}

	public void setTask(Task task)
	{
		this.task.add(task);
	}

	public List<Anysis> getAnysis()
	{
		return anysis;
	}

	public void setAnysis(Anysis anysis)
	{
		this.anysis.add(anysis);
	}

}
