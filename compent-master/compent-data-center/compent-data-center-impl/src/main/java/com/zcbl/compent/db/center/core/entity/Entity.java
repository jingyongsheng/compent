package com.zcbl.compent.db.center.core.entity;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jys 2016年11月8日
 */
public class Entity {
	private Map<String, String> filed = new ConcurrentHashMap<String, String>();
	private Map<String, Object> values = new ConcurrentHashMap<String, Object>();
	private Map<String, String> query = new ConcurrentHashMap<String, String>();
	private String tableName;
	private String prikey;
	private int start;
	private int length;
	private String entityName;
	private String sql;
	private String orderBy;

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Queue<String> getFiledQueue() {
		Queue<String> queue = new LinkedList<String>();
		Set<String> set = values.keySet();
		Iterator<String> ite = set.iterator();
		while (ite.hasNext()) {
			queue.add(filed.get(ite.next()));
		}
		return queue;
	}

	public Queue<Object> getValueQueue() {
		Queue<Object> queue = new LinkedList<Object>();
		Set<String> set = values.keySet();
		Iterator<String> ite = set.iterator();
		while (ite.hasNext()) {
			queue.add(values.get(ite.next()));
		}
		return queue;
	}

	public String getEntityName() {
		return entityName;
	}

	public Map<String, String> getQuery() {
		return query;
	}

	public void setQuery(Map<String, String> query) {
		this.query = query;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
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

	public String getPrikey() {
		return prikey;
	}

	public void setPrikey(String prikey) {
		this.prikey = prikey;
	}

	public Map<String, String> getFiled() {
		return filed;
	}

	public void setFiled(Map<String, String> filed) {
		this.filed = filed;
	}

	public Map<String, Object> getValues() {
		return values;
	}

	public void setValues(Map<String, Object> values) {
		this.values = values;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void clear() {
		values.clear();
	}

}
