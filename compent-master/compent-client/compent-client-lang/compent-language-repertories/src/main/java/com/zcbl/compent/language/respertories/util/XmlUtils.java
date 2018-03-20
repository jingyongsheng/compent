package com.zcbl.compent.language.respertories.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XmlUtils
{
	public static void parse(String content, Queue<String> queue, Map<String, Map<String, Object>> attrs,
			Map<String, String> values)
	{
		InputStream inputStream = null;
		try
		{
			inputStream = new ByteArrayInputStream(StringToHtml(content).getBytes("UTF-8"));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		SAXReader reader = new SAXReader();
		if (inputStream != null)
		{
			try
			{
				Document doc = reader.read(inputStream);
				Element root = doc.getRootElement();
				for (Iterator<Element> iter = root.elementIterator(); iter.hasNext();)
				{
					Element element = (Element) iter.next();
					int all = element.attributeCount();
					Map<String, Object> at = new HashMap<String, Object>();
					for (int i = 0; i < all; i++)
					{
						Attribute attr = element.attribute(i);
						attr.getValue();
						at.put(attr.getName(), attr.getValue());
					}
					if (all == 0)
					{
						continue;
					}
					queue.add(element.getName() + ":" + element.attributeValue("id"));
					attrs.put(element.getName() + ":" + element.attributeValue("id"), at);
					values.put(element.getName() + ":" + element.attributeValue("id"), element.getStringValue());
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			} finally
			{
				try
				{
					inputStream.close();
					reader = null;
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	public static String StringToHtml(String s)
	{
		s = Replace(s, "&amp;", "&");
		s = Replace(s, "&lt;", "<");
		s = Replace(s, "&gt;", ">");
		s = Replace(s, "&single_qu;", "'");
		s = Replace(s, "&quot;", "\"");
		return s;
	}

	public static String Replace(String source, String oldString, String newString)
	{
		if (source == null)
		{
			return null;
		}
		StringBuffer output = new StringBuffer();
		int lengOfsource = source.length();
		int lengOfold = oldString.length();
		int posStart;
		int pos;
		for (posStart = 0; (pos = source.indexOf(oldString, posStart)) >= 0; posStart = pos + lengOfold)
		{
			output.append(source.substring(posStart, pos));
			output.append(newString);
		}
		if (posStart < lengOfsource)
		{
			output.append(source.substring(posStart));
		}
		return output.toString();
	}
}
