package com.zcbl.compent.data.center.log.bean;

public class Message {
	public String path;
	public String time;
	public String content;
	Leavel level;

	public Message(Leavel level, String path, String time, String content) {
		this.level = level;
		this.path = path;
		this.content = content;
		this.time = time;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Leavel getLevel() {
		return level;
	}

	public void setLevel(Leavel level) {
		this.level = level;
	}
}
