package com.zcbl.compent.task.client.global.bean;

public enum Transport {
	DB("数据库","db"), HTTP("http协议", "http"), MEMORY("配置", "memory"), RPC("远程连接", "rpc");
	private String key;
	private String value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private Transport(String key, String value) {
		this.key = key;
		this.value = value;
	}
}
