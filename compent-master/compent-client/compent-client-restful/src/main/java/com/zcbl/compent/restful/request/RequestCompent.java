package com.zcbl.compent.restful.request;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author jys 2016年11月10日
 */
public class RequestCompent extends HttpServletRequestWrapper
{
	private HttpServletRequest request;

	@SuppressWarnings("unchecked")
	public RequestCompent(HttpServletRequest request)
	{
		super(request);
		oldMap = request.getParameterMap();
		newMap = getParameterMap();
		this.request = request;
	}

	private Map<String, String[]> newMap;
	private Map<String, String[]> oldMap;
	ServletInputStream newStream = null;

	@Override
	public String getParameter(String name)
	{
		String[] vals = getParameterMap().get(name);
		if (vals != null && vals.length > 0)
			return vals[0];
		else
			return null;
	}

	public void setParameter(String key, String value)
	{
		String[] str = newMap.get(key);
		if (str != null && str.length > 0)
		{
			List<String> list = new ArrayList<String>(str.length + 1);
			for (int i = 0; i < str.length; i++)
			{
				list.add(str[i]);
			}
			list.add(value);
			str = list.toArray(new String[1]);
		} else
		{
			newMap.put(key, new String[]
			{ value });
		}
	}

	public void setParameter(Map<String, String[]> map)
	{
		newMap.putAll(map);
	}

	@Override
	public Enumeration getParameterNames()
	{
		Enumeration enumeration = new Enumeration<String>()
		{
			private final Iterator<String> i = newMap.keySet().iterator();

			public boolean hasMoreElements()
			{
				return i.hasNext();
			}

			public String nextElement()
			{
				return i.next();
			}
		};
		return enumeration;
	}

	public void setInputStream(String content)
	{
		if (content == null)
			return;
		byte[] buffer = null;
		try
		{
			buffer = content.getBytes("utf-8");
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
			return;
		}
		final ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
		newStream = new ServletInputStream()
		{
			@Override
			public int read() throws IOException
			{
				return bais.read();
			}

			@Override
			public synchronized void reset()
			{
				bais.reset();
			}
		};
	}

	@Override
	public ServletInputStream getInputStream()
	{
		if (newStream != null)
			return newStream;
		ServletInputStream stream = null;
		try
		{
			stream = request.getInputStream();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		byte[] buffer = null;
		try
		{
			byte[] b = new byte[1024];
			int k = 0;
			StringBuilder sber = new StringBuilder();
			while ((k = stream.read(b)) != -1)
			{
				sber.append(new String(b, 0, k, request.getCharacterEncoding()));
			}
			buffer = sber.toString().getBytes("utf-8");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		final ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
		newStream = new ServletInputStream()
		{
			@Override
			public int read() throws IOException
			{
				return bais.read();
			}

			@Override
			public synchronized void reset()
			{
				bais.reset();
			}
		};
		return newStream;
	}

	@Override
	public Map<String, String[]> getParameterMap()
	{
		if (newMap == null)
			newMap = sanitizeParamMap(oldMap);
		return newMap;

	}

	@Override
	public String[] getParameterValues(String name)
	{
		return getParameterMap().get(name);
	}

	private Map<String, String[]> sanitizeParamMap(Map<String, String[]> raw)
	{
		Map<String, String[]> res = new HashMap<String, String[]>();
		if (raw == null)
			return res;
		for (String key : (Set<String>) raw.keySet())
		{
			String[] rawVals = raw.get(key);
			String[] snzVals = new String[rawVals.length];
			for (int i = 0; i < rawVals.length; i++)
			{
				snzVals[i] = rawVals[i];
			}
			res.put(key, snzVals);
		}
		return res;
	}
}
