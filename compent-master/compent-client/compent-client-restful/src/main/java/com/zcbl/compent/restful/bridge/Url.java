package com.zcbl.compent.restful.bridge;

import java.util.HashMap;
import java.util.Map;

public class Url {
	private String url;
	private String status;
	private String type;
	private Map<String, Object> header = new HashMap<String, Object>();

	public Map<String, Object> getHeader() {
		return header;
	}

	public void setHeader(Map<String, Object> header) {
		this.header = header;
	}

	public Url(String url, String status, String type) {
		this.url = url;
		this.status = status;
		this.type = type;
	}

	public Url(String url, String status, String type, Map<String, Object> map) {
		this.url = url;
		this.status = status;
		this.type = type;
		this.header = map;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
