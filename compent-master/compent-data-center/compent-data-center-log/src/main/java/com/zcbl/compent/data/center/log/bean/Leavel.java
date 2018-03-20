package com.zcbl.compent.data.center.log.bean;

public enum Leavel
{
	ERROR("错误信息", "0"), WARN("警告信息", "1"), INFO("普通信息", "2"), DEBUG("调试信息", "3"), TRACK("轨迹信息", "4");
	private String key;
	private String value;

	public String getKey()
	{
		return key;
	}

	public void setKey(String key)
	{
		this.key = key;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	private Leavel(String key, String value)
	{
		this.key = key;
		this.value = value;
	}
}