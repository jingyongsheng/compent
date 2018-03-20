package com.zcbl.compent.data.center.api.bean;

/**
 * @author jys 2016年11月7日
 */
public class Query {
	private String app;// 应用
	private Object entity;// 实体
	private int start;// 开始记录
	private int length;// 长度
	public Type type;// 类型
	public String join;// and or
	public boolean count;// 是否返回总数

	public boolean isCount() {
		return count;
	}

	public void setCount(boolean count) {
		this.count = count;
	}

	public String getJoin() {
		return join;
	}

	public void setJoin(String join) {
		this.join = join;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public Object getEntity() {
		return entity;
	}

	public void setEntity(Object entity) {
		this.entity = entity;
	}

	private String entityName;

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getEntityName() {
		if (entityName == null) {
			entityName = entity.getClass().getName();
		}
		return entityName;
	}

}
