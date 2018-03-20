package com.zcbl.compent.db.center.core.bridge;

import com.zcbl.compent.data.center.api.Center;
import com.zcbl.compent.data.center.api.bean.Query;
import com.zcbl.compent.data.center.api.bean.Result;

/**
 * @author jys 2016年11月8日
 */
public class SqlExcuteImpl implements Center {
	static SqlExcuteImpl impl = new SqlExcuteImpl();

	private SqlExcuteImpl() {
	}

	public static SqlExcuteImpl getIntance() {
		return impl;
	}

	@Override
	public void save(Result rs, Query data) {
		String sql = (String) data.getEntity();
		// int r = PersistManager.save(dataSource, sql, obj);
		// rs.setCode(r);
	}

	@Override
	public void update(Result rs, Query data) {
		String sql = (String) data.getEntity();
		// PersistManager.save(dataSource, sql, obj);
	}

	@Override
	public void query(Result rs, Query data) {
		String sql = (String) data.getEntity();
		// PersistManager.save(dataSource, sql, obj);
	}
}
