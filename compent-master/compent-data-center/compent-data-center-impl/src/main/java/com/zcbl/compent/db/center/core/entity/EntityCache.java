package com.zcbl.compent.db.center.core.entity;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

import com.zcbl.compent.db.center.core.utils.CamelUtils;

public class EntityCache
{
	private Map<String, Entity> _entity = new ConcurrentHashMap<String, Entity>();

	private EntityCache()
	{
	}

	private static EntityCache cache = new EntityCache();

	public static EntityCache getInstance()
	{
		return cache;
	}

	public void addEntity(String entity, String tableName, String prikey)
	{
		Entity en = _entity.get(entity);
		if (en == null)
		{
			en = new Entity();
			en.setPrikey(prikey);
			en.setTableName(tableName);
			_entity.put(entity, en);
		} else
		{
			en.setPrikey(prikey);
			en.setTableName(tableName);
		}
	}

	public void addEntityField(String entity, String fielName, String dbFieldName)
	{
		Entity en = _entity.get(entity);
		if (en == null)
		{
			en = new Entity();
			Map<String, String> m = new HashMap<String, String>();
			m.put(fielName, dbFieldName);
			en.setFiled(m);
			_entity.put(entity, en);
		} else
		{
			Map<String, String> m = en.getFiled();
			if (m != null)
			{
				m.put(fielName, dbFieldName);
			} else
			{
				m = new HashMap<String, String>();
				m.put(fielName, dbFieldName);
				en.setFiled(m);
			}
		}
	}

	public Entity getOrAddEntity(Class c)
	{
		Entity t = _entity.get(c.getName());
		if (t != null)
		{
			return t;
		} else
		{
			t = new Entity();
			t.setFiled(toMap(c));
			t.setTableName(CamelUtils.camelToUnderline(c.getName()));
		}
		return t;
	}

	public Entity getOrAddEntity(Object obj)
	{
		Class c = obj.getClass();
		Entity t = _entity.get(c.getName());
		if (t != null)
		{
			toSetValue(t, obj);
			return t;
		} else
		{
			t = new Entity();
			t.setFiled(toMap(c));
			toSetValue(t, obj);
			t.setTableName(CamelUtils.camelToUnderline(c.getName()));
		}
		return t;
	}

	private Map<String, String> toMap(Class c)
	{
		Map<String, String> map = new HashMap<String, String>();
		Field[] fields = c.getDeclaredFields();
		for (Field field : fields)
		{
			String obj;
			try
			{
				obj = CamelUtils.camelToUnderline(field.getName());
				if (obj != null)
				{
					map.put(field.getName(), obj);
				}
			} catch (IllegalArgumentException e)
			{
				e.printStackTrace();
			}
		}
		return map;
	}

	private void toSetValue(Entity entity, Object obj)
	{
		Class c = obj.getClass();
		Map<String, Object> map = new HashMap<String, Object>();
		Field[] fields = c.getDeclaredFields();
		Queue<Object> list = new LinkedList<Object>();
		for (Field field : fields)
		{
			Object b;
			try
			{
				b = field.get(obj);
				if (b != null)
				{
					list.add(b);
					map.put(field.getName(), b);
				}
			} catch (IllegalArgumentException e)
			{
				e.printStackTrace();
			} catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
		}
		entity.setValues(map);
	}
}
