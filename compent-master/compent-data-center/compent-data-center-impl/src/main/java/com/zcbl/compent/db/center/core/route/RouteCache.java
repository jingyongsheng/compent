package com.zcbl.compent.db.center.core.route;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.zcbl.compent.db.center.core.regular.RegularFactory;
import com.zcbl.compent.db.center.core.utils.CamelUtils;

/**
 * @author jys 2016年11月8日
 */
public class RouteCache {
	private static RouteCache cache = new RouteCache();
	private static Map<String, AppRoute> _route = new ConcurrentHashMap<String, AppRoute>();
	Lock lock = new ReentrantLock();

	private RouteCache() {
	}

	public static RouteCache getInstance() {
		return cache;
	}

	/**
	 * add datasource
	 * 
	 * @param app
	 * @param entity
	 * @param dataSource
	 * @param dataTable
	 */
	public void addDataSource(String app, String entity, String dataSource, String dataTable, String key,
			Method method) {
		if (app == null)
			return;
		AppRoute a = _route.get(app);
		if (a == null) {
			lock.lock();
			try {
				a = new AppRoute();
				a.setAppName(app);
				Map<String, EntityRoute> m = new HashMap<String, EntityRoute>();
				EntityRoute route = new EntityRoute();
				route.getMap().put(method, dataSource);
				route.setEntityName(entity);
				route.setTableName(dataTable);
				m.put(entity, route);
				a.setRoute(m);
				_route.put(app, a);
			} finally {
				lock.unlock();
			}
		} else {
			EntityRoute route = a.getRoute().get(entity);
			if (route != null) {
				route.getMap().put(method, dataSource);
				route.setEntityName(entity);
				route.setTableName(dataTable);
			} else {
				route = new EntityRoute();
				route.getMap().put(method, dataSource);
				route.setEntityName(entity);
				route.setTableName(dataTable);
				a.getRoute().put(entity, route);
			}
		}
	}

	/**
	 * 
	 * @param app
	 * @param entity
	 * @return
	 */
	public String getDataSource(String app, String entity, Method method) {
		if (app == null)
			return null;
		String dataSource;
		AppRoute a = _route.get(app);
		if (a == null) {
			return null;
		} else {
			dataSource = a.getRoute().get(entity).getMap().get(method);
		}
		return dataSource;
	}

	/**
	 * 
	 * @param app
	 * @param entity
	 * @return
	 */
	public String getOrAddDataSource(String app, String entity, Method method) {
		String dataSource;
		AppRoute a = _route.get(app);
		if (a == null) {
			lock.lock();
			try {
				a = new AppRoute();
				a.setAppName(app);
				Map<String, EntityRoute> m = new HashMap<String, EntityRoute>();
				EntityRoute route = new EntityRoute();
				dataSource = RegularFactory.getRegular().getDataSource(app, entity);
				route.getMap().put(method, dataSource);
				route.setEntityName(entity);
				route.setTableName(CamelUtils.camelToUnderline(entity));
				m.put(entity, route);
				a.setRoute(m);
				_route.put(app, a);
			} finally {
				lock.unlock();
			}
		} else {
			dataSource = a.getRoute().get(entity).getMap().get(method);
		}
		return dataSource;
	}
}
