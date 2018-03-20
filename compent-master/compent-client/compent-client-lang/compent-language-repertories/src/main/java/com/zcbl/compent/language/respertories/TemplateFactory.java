package com.zcbl.compent.language.respertories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zcbl.compent.language.respertories.CommandFactory.CommandStack;

public class TemplateFactory
{
	private static TemplateFactory factory = new TemplateFactory();
	public Map<String, Queue<Template>> queue = new ConcurrentHashMap<String, Queue<Template>>();
	public Map<String, String> content = new ConcurrentHashMap<String, String>();

	private TemplateFactory()
	{
	}

	public static TemplateFactory getInstance()
	{
		return factory;
	}

	public void addTemplate(String key, String content)
	{
		this.content.put(key, content);
	}

	public interface Lang
	{
		public void excute(Template tempate, CommandStack stack) throws Exception;

		public void conditions(Template t) throws Exception;
	}

	private void getAnysisTemplate(String content, Queue<Template> start) throws Exception
	{
		Pattern p = Pattern.compile("\\<#.*?\\>");
		Map<String, Map<Template, Template>> lang = new HashMap<String, Map<Template, Template>>();
		Matcher m = p.matcher(content);
		while (m.find())
		{
			String str = m.group();
			Template temp = new Template();
			temp.setEnd(m.end());
			temp.setStart(m.start());
			temp.setOriginal(str);
			temp.setTag_s(m.start());
			temp.setTag_start(str);
			start.add(temp);
			String key = "";
			// temp.setLang(LanguageFactory.getInstance().getLang(key));
			temp.setTag(key);
			Map<Template, Template> t = lang.get(key);
			if (t == null)
			{
				t = new ConcurrentHashMap<Template, Template>();
				lang.put(key, t);
				t.put(temp, null);
			} else
			{
				t.put(temp, null);
			}
		}
		Pattern pp = Pattern.compile("\\</#.*?\\>");
		Matcher mm = pp.matcher(content);
		while (mm.find())
		{
			String str = mm.group();
			Template temp = new Template();
			temp.setEnd(mm.end());
			temp.setStart(mm.start());
			temp.setTag_e(mm.end());
			temp.setOriginal(str);
			temp.setTag_end(str);
			String key = "";
			Map<Template, Template> map = lang.get(key);
			if (map != null && map.size() > 0)
			{
				Set<Template> set = map.keySet();
				Iterator<Template> ite = set.iterator();
				while (ite.hasNext())
				{
					Template tem = ite.next();
					Template tt = map.get(tem);
					if (tt == null)
					{
						map.put(tem, temp);
						break;
					}
				}
			} else
			{
				throw new Exception("no start tag");
			}
		}
		validate(lang);
		validateQueue(start);
	}

	private void validate(Map<String, Map<Template, Template>> lang) throws Exception
	{
		if (lang != null && lang.size() > 0)
		{
			Set<String> set = lang.keySet();
			Iterator<String> ite = set.iterator();
			while (ite.hasNext())
			{
				String key = ite.next();
				Map<Template, Template> map = lang.get(key);
				if (map != null && map.size() > 0)
				{
					Set<Template> s = map.keySet();
					Iterator<Template> t = s.iterator();
					while (t.hasNext())
					{
						Template k = t.next();
						Template v = map.get(k);
						if (v == null)
						{
							throw new Exception(k.getTag_start() + " lost end tag");
						} else
						{
							k.setTag_e(v.getTag_s());
							k.setTag_end(v.getTag_end());
						}
					}
				}
			}
		}
	}

	private void validateQueue(Queue<Template> t) throws Exception
	{
		if (t != null && !t.isEmpty())
		{
			int last = 0;
			List<Template> list = new ArrayList<Template>();
			int k = 0;
			for (Template tem : t)
			{
				k++;
				int start = tem.getTag_s();
				int end = tem.getTag_e();
				if (start < last && end > last)
				{
					throw new Exception(tem.getTag_start() + " positions is arcoss");
				} else if (start < last && end < last)
				{
					for (int i = k; i < t.size(); i++)
					{
						addTemplateList(tem, t, list);
					}
				}
				last = end;
			}
			t.removeAll(list);
		}
	}

	private void addTemplateList(Template temp, Queue<Template> t, List<Template> tempList)
	{
		Template te = t.peek();
		if (temp.getTag_s() < te.getTag_s() && temp.getTag_e() > te.getTag_e())
		{
			List<Template> list = temp.getTemplate();
			list.add(te);
			tempList.add(te);
			addTemplateList(te, t, tempList);
		}
	}

	public void anysis(String key, final String content)
	{
		try
		{
			Queue<Template> start = new LinkedList<Template>();
			getAnysisTemplate(content, start);
			this.queue.put(key, start);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// public StringBuffer getResult(String key, CommandStack stack)
	// {
	// Queue<Template> queue = this.queue.get(key);
	// StringBuffer sb = new StringBuffer();
	// if (queue != null)
	// if (queue != null)
	// {
	// for (Template t : queue)
	// {
	// if (t.origin = false)
	// {
	// try
	// {
	// t.getLang().excute(t, stack);
	// } catch (Exception e)
	// {
	// return sb.append(e.getMessage());
	// }
	// } else
	// {
	// sb.append(t.getResult());
	// }
	// }
	// }
	// return sb;
	// }

	public StringBuffer getResult(String key, CommandStack stack)
	{
		String content = this.content.get(key);
		if (content == null)
			return null;
		StringBuffer sb = new StringBuffer();
		sb.append(FreeMaker.processTemplate(content, stack.getMap()));
		return sb;
	}

	public class Template
	{
		public String original;
		public boolean origin = false;
		public String result;
		public int start;
		public int end;
		public Lang lang;
		public String tag_start;
		public String tag_end;
		public int tag_s;
		public int tag_e;
		public String tag;
		public List<Template> template = new ArrayList<Template>();
		public Map<String, String> condition = new HashMap<String, String>();

		public Map<String, String> getCondition()
		{
			return condition;
		}

		public void setCondition(Map<String, String> condition)
		{
			this.condition = condition;
		}

		public List<Template> getTemplate()
		{
			return template;
		}

		public void setTemplate(List<Template> template)
		{
			this.template = template;
		}

		public String getTag()
		{
			return tag;
		}

		public void setTag(String tag)
		{
			this.tag = tag;
		}

		public String getTag_start()
		{
			return tag_start;
		}

		public void setTag_start(String tag_start)
		{
			this.tag_start = tag_start;
		}

		public String getTag_end()
		{
			return tag_end;
		}

		public void setTag_end(String tag_end)
		{
			this.tag_end = tag_end;
		}

		public int getTag_s()
		{
			return tag_s;
		}

		public void setTag_s(int tag_s)
		{
			this.tag_s = tag_s;
		}

		public int getTag_e()
		{
			return tag_e;
		}

		public void setTag_e(int tag_e)
		{
			this.tag_e = tag_e;
		}

		public Lang getLang()
		{
			return lang;
		}

		public void setLang(Lang lang)
		{
			this.lang = lang;
		}

		public int getStart()
		{
			return start;
		}

		public void setStart(int start)
		{
			this.start = start;
		}

		public int getEnd()
		{
			return end;
		}

		public void setEnd(int end)
		{
			this.end = end;
		}

		public boolean isOrigin()
		{
			return origin;
		}

		public void setOrigin(boolean origin)
		{
			this.origin = origin;
		}

		public String getOriginal()
		{
			return original;
		}

		public void setOriginal(String original)
		{
			this.original = original;
		}

		public String getResult()
		{
			return result;
		}

		public void setResult(String result)
		{
			this.result = result;
		}

	}

	public static void main(String[] args)
	{
	}

}
