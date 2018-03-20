package com.zcbl.compent.language.respertories;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreeMaker
{
	private static Configuration configuration = new Configuration();

	public static String processTemplate(String temContent, Map<String, Map<String, Object>> map)
	{
		StringWriter writer = new StringWriter();
		try
		{
			StringTemplateLoader stringLoader = new StringTemplateLoader();
			stringLoader.putTemplate("template", temContent);
			configuration.setTemplateLoader(stringLoader);
			Template template = configuration.getTemplate("template", "utf-8");
			template.process(map, writer);
		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (TemplateException e)
		{
			e.printStackTrace();
		}
		return writer.toString();
	}
}
