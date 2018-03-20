package com.zcbl.compent.db.center.core.bridge;

import java.util.List;
import java.util.Map;
import com.zcbl.compent.data.center.api.Center;
import com.zcbl.compent.data.center.api.bean.Query;
import com.zcbl.compent.data.center.api.bean.Result;
import com.zcbl.compent.db.center.core.entity.Entity;
import com.zcbl.compent.db.center.core.persist.PersistManager;
import com.zcbl.compent.db.center.core.route.Method;
import com.zcbl.compent.db.center.core.route.RouteCache;
import com.zcbl.compent.db.center.core.sql.BuildSqlFactory;

/**
 * @author jys 2016年11月8日
 */
public class CompentEntityExcuteImpl implements Center {
	static CompentEntityExcuteImpl impl = new CompentEntityExcuteImpl();

	private CompentEntityExcuteImpl() {
	}

	public static CompentEntityExcuteImpl getIntance() {
		return impl;
	}

	@Override
	public void save(Result rs, Query data) {
		Entity entity = (Entity) data.getEntity();
		String sql = BuildSqlFactory.getSqlFactory().toSaveSql(entity);
		int r = PersistManager.save(
				RouteCache.getInstance().getDataSource(data.getApp(), data.getEntityName(), Method.WRITE), sql,
				entity.getValueQueue().toArray());
		rs.setCode(r);
	}

	@Override
	public void update(Result rs, Query data) {
		Entity entity = (Entity) data.getEntity();
		String sql = BuildSqlFactory.getSqlFactory().toUpdateSql(entity);
		rs.setCode(PersistManager.save(
				RouteCache.getInstance().getDataSource(data.getApp(), data.getEntityName(), Method.WRITE), sql,
				entity.getValueQueue().toArray()));
	}

	@Override
	public void query(Result rs, Query data) {
		Entity entity = (Entity) data.getEntity();
		String sql = BuildSqlFactory.getSqlFactory().toQuerySql(entity);
		if (data.isCount()) {
			String count = BuildSqlFactory.getSqlFactory().toCountSql(entity);
			rs.setCount(PersistManager.count(
					RouteCache.getInstance().getDataSource(data.getApp(), data.getEntityName(), Method.READ), count,
					entity.getValueQueue().toArray()));
		}
		List<Map<String, Object>> list = PersistManager.query(
				RouteCache.getInstance().getDataSource(data.getApp(), data.getEntityName(), Method.READ), sql,
				entity.getValueQueue().toArray());
		rs.setList(list);
	}
}
