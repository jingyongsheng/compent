package com.zcbl.compent.data.center.client;

import java.util.List;

import com.zcbl.compent.data.center.DataCenter;
import com.zcbl.compent.data.center.api.Center;
import com.zcbl.compent.data.center.api.bean.Query;
import com.zcbl.compent.data.center.api.bean.Result;
import com.zcbl.compent.data.center.api.bean.Type;
import com.zcbl.compent.data.center.client.factory.DataCenterFactory;

/**
 * @author jys 2016年11月8日
 */
public class DataCenterClient implements DataCenter {
	static DataCenterClient client = new DataCenterClient();
	Center object = DataCenterFactory.getIntance().getObjectFactory();
	Center sql = DataCenterFactory.getIntance().getSqlFactory();
	Center compent = DataCenterFactory.getIntance().getEntityFactory();

	private DataCenterClient() {
	}

	public static DataCenterClient getInstance() {
		return client;
	}

	public int save(String sql) throws Exception {
		Result rs = buildResult();
		Query entity = buildEntity(Type.SQL, sql);
		this.sql.save(rs, entity);
		return rs.getCode();
	}

	public int update(String sql) throws Exception {
		Result rs = buildResult();
		Query entity = buildEntity(Type.SQL, sql);
		this.sql.update(rs, entity);
		return rs.getCode();
	}

	public List find(String sql) throws Exception {
		Result rs = buildResult();
		Query entity = buildEntity(Type.SQL, sql);
		this.sql.query(rs, entity);
		return rs.getList();
	}

	public int save(Object obj) throws Exception {
		Result rs = buildResult();
		Query entity = buildEntity(Type.OBJECT, obj);
		object.save(rs, entity);
		return rs.getCode();
	}

	public int update(Object obj) throws Exception {
		Result rs = buildResult();
		Query entity = buildEntity(Type.OBJECT, obj);
		object.update(rs, entity);
		return rs.getCode();
	}

	public List find(Object obj) throws Exception {
		Result rs = buildResult();
		Query entity = buildEntity(Type.OBJECT, obj);
		object.query(rs, entity);
		return rs.getList();
	}

	public Result save(Query obj) throws Exception {
		Result rs = buildResult();
		compent.save(rs, obj);
		return rs;
	}

	public Result update(Query obj) throws Exception {
		Result rs = buildResult();
		compent.update(rs, obj);
		return rs;
	}

	public Result find(Query obj) throws Exception {
		Result rs = buildResult();
		compent.query(rs, obj);
		return rs;
	}

	private Query buildEntity(Type type, Object obj) {
		Query entity = new Query();
		entity.setType(type);
		entity.setEntity(obj);
		return entity;
	}

	private Result buildResult() {
		return new Result();
	}
}
