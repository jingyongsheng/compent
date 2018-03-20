package com.zcbl.compent.task.client.global.bean;

public class Global
{
	public String card;
	public Transport transport;
	public String app;
	public String server;
	public String port;
	public String ip;
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort()
	{
		return port;
	}

	public void setPort(String port)
	{
		this.port = port;
	}

	public String getServer()
	{
		return server;
	}

	public void setServer(String server)
	{
		this.server = server;
	}

	public String getApp()
	{
		return app;
	}

	public void setApp(String app)
	{
		this.app = app;
	}

	public String getCard()
	{
		return card;
	}

	public void setCard(String card)
	{
		this.card = card;
	}

	public Transport getTransport()
	{
		return transport;
	}

	public void setTransport(Transport transport)
	{
		this.transport = transport;
	}

}
