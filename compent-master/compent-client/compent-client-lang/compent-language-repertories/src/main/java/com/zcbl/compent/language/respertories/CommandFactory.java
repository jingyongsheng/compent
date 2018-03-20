package com.zcbl.compent.language.respertories;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.zcbl.compent.language.respertories.expression.Lang;
import com.zcbl.compent.language.respertories.util.XmlUtils;

/**
 * @author jys 2016年11月15日
 */
public class CommandFactory
{
	private static CommandFactory factory = new CommandFactory();
	private Map<String, Queue<Command>> userCommandset = new ConcurrentHashMap<String, Queue<Command>>();

	private CommandFactory()
	{
	}

	public static CommandFactory getInstance()
	{
		return factory;
	}

	private Queue<Command> getCommandSet(String key)
	{
		return userCommandset.get(key);
	}

	public void anysisCommand(String key, String content) throws Exception
	{
		if (content == null || content.equals(""))
			return;
		content = XmlUtils.StringToHtml(content);
		content = "<cmd>" + content + "</cmd>";
		Queue<Command> queue = new LinkedList<Command>();
		InputStream inputStream = null;
		try
		{
			inputStream = new ByteArrayInputStream(content.getBytes("UTF-8"));
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
				for (@SuppressWarnings("unchecked")
				Iterator<Element> iter = root.elementIterator(); iter.hasNext();)
				{
					Element element = (Element) iter.next();
					Lang lang = LanguageFactory.getInstance().getLang(element.getName());
					if (lang != null)
					{
						Queue<Command> q = lang.anysisElement(element);
						if (!q.isEmpty())
							queue.addAll(q);
					}
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
		userCommandset.put(key, queue);
	}

	public class CommandStack
	{
		Map<String, Map<String, Object>> pointer = new ConcurrentHashMap<String, Map<String, Object>>();

		public Map<String, Object> getPointer(String key)
		{
			return pointer.get(key);
		}

		public void setPointer(String pointer, Map<String, Object> result)
		{
			this.pointer.put(pointer, result);
		}

		public Map<String, Map<String, Object>> getMap()
		{
			return pointer;
		}

		public void clear()
		{
			this.pointer.clear();
		}
	}

	public CommandStack buildCommandStack()
	{
		return new CommandStack();
	}

	public CommandStack excuteCommand(String key, CommandStack s) throws Exception
	{
		Queue<Command> queue = getCommandSet(key);
		if (queue == null || queue.isEmpty())
			return s;
		for (Command command : queue)
		{
			command.excute(s);
		}
		return s;
	}

}
