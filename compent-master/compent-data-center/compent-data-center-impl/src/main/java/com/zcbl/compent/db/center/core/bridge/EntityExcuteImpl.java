package com.zcbl.compent.db.center.core.bridge;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zcbl.compent.data.center.api.Center;
import com.zcbl.compent.data.center.api.bean.Query;
import com.zcbl.compent.data.center.api.bean.Result;
import com.zcbl.compent.db.center.core.entity.Entity;
import com.zcbl.compent.db.center.core.entity.EntityCache;
import com.zcbl.compent.db.center.core.persist.PersistManager;
import com.zcbl.compent.db.center.core.route.Method;
import com.zcbl.compent.db.center.core.route.RouteCache;
import com.zcbl.compent.db.center.core.sql.BuildSqlFactory;

/**
 * @author jys 2016年11月8日
 */
public class EntityExcuteImpl implements Center {
	static EntityExcuteImpl impl = new EntityExcuteImpl();

	private EntityExcuteImpl() {
	}

	public static EntityExcuteImpl getIntance() {
		return impl;
	}

	@Override
	public void save(Result rs, Query data) {
		Entity entity = EntityCache.getInstance().getOrAddEntity(data.getEntity());
		String sql = null;
		if (entity.getSql() == null) {
			sql = BuildSqlFactory.getSqlFactory().toSaveSql(entity);
		} else {
			sql = entity.getSql();
		}
		int r = PersistManager.save(
				RouteCache.getInstance().getOrAddDataSource(data.getApp(), data.getEntityName(), Method.WRITE), sql,
				entity.getValueQueue().toArray());
		rs.setCode(r);
	}

	@Override
	public void update(Result rs, Query data) {
		Entity entity = EntityCache.getInstance().getOrAddEntity(data.getEntity());
		String sql = BuildSqlFactory.getSqlFactory().toUpdateSql(entity);
		rs.setCode(PersistManager.save(
				RouteCache.getInstance().getOrAddDataSource(data.getApp(), data.getEntityName(), Method.WRITE), sql,
				entity.getValueQueue().toArray()));
	}

	@Override
	public void query(Result rs, Query data) {
		Object c = data.getEntity();
		Entity entity = EntityCache.getInstance().getOrAddEntity(c);
		String sql = null;
		sql = BuildSqlFactory.getSqlFactory().toQuerySql(entity);
		if (data.getLength() != 0) {
			String count = BuildSqlFactory.getSqlFactory().toCountSql(entity);
			rs.setCount(PersistManager.count(
					RouteCache.getInstance().getOrAddDataSource(data.getApp(), data.getEntityName(), Method.READ),
					count, entity.getValueQueue().toArray()));
		}
		List<Map<String, Object>> list = PersistManager.query(
				RouteCache.getInstance().getOrAddDataSource(data.getApp(), data.getEntityName(), Method.READ), sql,
				entity.getValueQueue().toArray());
		List rt = new ArrayList();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				rt.add(ObjTransfer.transfer(entity, c.getClass(), list.get(i)));
			}
		}
	}

	private static class ObjTransfer {
		public static Object transfer(Entity entity, Class clazz, Map<String, Object> map) {
			if (map == null || map.isEmpty())
				return null;
			try {
				Field[] fields = clazz.getDeclaredFields();
				Object obj = clazz.newInstance();
				Set<String> set = map.keySet();
				Iterator<String> ite = set.iterator();
				while (ite.hasNext()) {
					String fi = ite.next();
					for (int j = 0; j < fields.length; j++) {
						Field f = fields[j];
						String name = entity.getFiled().get(f.getName());
						if (name.equalsIgnoreCase(fi)) {
							boolean flag = f.isAccessible();
							f.setAccessible(true);
							f.set(obj, map.get(name));
							f.setAccessible(flag);
						}
					}
				}
				return obj;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}
}
