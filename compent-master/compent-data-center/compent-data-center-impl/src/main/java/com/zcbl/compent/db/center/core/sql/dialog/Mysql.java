package com.zcbl.compent.db.center.core.sql.dialog;

import java.util.Queue;

import com.zcbl.compent.db.center.core.entity.Entity;
import com.zcbl.compent.db.center.core.sql.BuildSql;

/**
 * @author jys 2016年11月8日
 */
public class Mysql implements BuildSql {

	private static Mysql mysql = new Mysql();

	public static Mysql getInstance() {
		return mysql;
	}

	private Mysql() {
	}

	@Override
	public String toSaveSql(Entity entity) {
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into ").append(entity.getTableName()).append(" ( ");
		StringBuffer v = new StringBuffer();
		Queue<String> fields = entity.getFiledQueue();
		if (!fields.isEmpty()) {
			for (String field : fields) {
				sb.append(field);
				v.append("?");
				sb.append(",");
				v.append(",");
			}
			String fix = sb.substring(0, sb.length() - 1);
			String vv = v.substring(0, v.length() - 1);
			v.delete(0, sb.length());
			v.append(vv);
			sb.delete(0, sb.length());
			sb.append(fix);
		}
		sb.append(" )").append(" values ( ").append(v).append(" )");
		return sb.toString();
	}

	@Override
	public String toUpdateSql(Entity entity) {
		StringBuffer sb = new StringBuffer();
		sb.append(" update ").append(entity.getTableName()).append(" set ");
		Queue<String> values = entity.getFiledQueue();
		Object key = entity.getValues().get(entity.getPrikey());
		if (!values.isEmpty()) {
			for (String field : values) {
				if (field.equals(entity.getPrikey())) {
					entity.getValues().remove(field);
					continue;
				}
				sb.append(field);
				sb.append(" = ? ");
				sb.append(",");
			}
			String fix = sb.substring(0, sb.length() - 1);
			sb.delete(0, sb.length());
			sb.append(fix);
		}
		sb.append(" where ").append(entity.getPrikey()).append(" = ").append(key);
		return sb.toString();
	}

	@Override
	public String toQuerySql(Entity entity) {
		StringBuffer sb = new StringBuffer();
		if (entity.getSql() != null) {
			sb.append(" select ").append(entity.getSql()).append(" from ").append(entity.getTableName())
					.append(" where 1=1 ").append("");
		} else {
			sb.append("select * from ").append(entity.getTableName()).append(" where 1=1 ").append("");
		}
		Queue<String> values = entity.getFiledQueue();
		for (String field : values) {
			sb.append(" and ");
			sb.append(field);
			sb.append(" = ? ");
		}
		if (entity.getOrderBy() != null) {
			sb.append(entity.getOrderBy());
		}
		if (entity.getLength() != 0) {
			sb.append(" limit ").append(entity.getStart()).append(",").append(entity.getLength());
		}
		return sb.toString();
	}

	@Override
	public String toCountSql(Entity entity) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select count(*) from ").append(entity.getTableName());
		return sb.toString();
	}
}
