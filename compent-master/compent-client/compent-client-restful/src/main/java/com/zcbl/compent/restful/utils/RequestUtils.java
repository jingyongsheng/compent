package com.zcbl.compent.restful.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class RequestUtils
{
	public static Map<String, Object> toToMap(Map<String, String[]> map)
	{
		if (map == null || map.isEmpty())
			return null;
		Set set = map.entrySet();
		Map.Entry entry;
		Iterator it = set.iterator();
		Map<String, Object> retMap = new HashMap<String, Object>();
		while (it.hasNext())
		{
			entry = (Map.Entry) it.next();
			String str = (String) entry.getKey();
			String[] param = (String[]) ((Entry) entry).getValue();
			StringBuffer sb = new StringBuffer();
			if (param != null && param.length > 0)
			{
				for (String content : param)
				{
					sb.append(content).append(",");
				}
			}
			String c = "";
			if (sb.length() > 0)
			{
				c = sb.substring(0, sb.length() - 1);
			}
			retMap.put(str, c);
		}
		return retMap;
	}
}
