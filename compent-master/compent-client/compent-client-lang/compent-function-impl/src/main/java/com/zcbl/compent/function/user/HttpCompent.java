package com.zcbl.compent.function.user;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import com.zcbl.compent.language.respertories.CmdFactory;
import com.zcbl.compent.language.respertories.Compent;
import com.zcbl.compent.language.respertories.Input;
import com.zcbl.compent.language.respertories.Output;
import com.zcbl.compent.language.respertories.UserCompent;
import com.zcbl.compent.task.client.annation.SysLoader;

@SysLoader
public class HttpCompent extends UserCompent
{

	public void execute(Compent compent, Input input, Output output)
	{
		if (input.getUser() != null && !input.getUser().isEmpty())
		{
			String inputLine = null;
			try
			{
				String type = (String) input.getUser().get("type");
				BufferedReader reader = getReader(input.getUser());
				if (type != null && type.equals("html"))
				{
					inputLine = readText(reader).toString();
				} else if (type != null && type.equals("json"))
				{

				} else if (type != null && type.equals("xml"))
				{

				} else
				{
					inputLine = readText(reader).toString();
				}
			} catch (Exception e)
			{
				e.printStackTrace();
				inputLine = "";
			}
			output.getUser().put("result", inputLine);
		}
	}

	private BufferedReader getReader(Map<String, Object> map)
	{
		String url = (String) map.get("url");
		String output = (String) map.get("output");
		String input = (String) map.get("input");
		String cache = (String) map.get("cache");
		String method = (String) map.get("method");
		String type = (String) map.get("content-type");
		String connection = (String) map.get("connection");
		String charset = (String) map.get("charset");
		String param = (String) map.get("param");
		if (url == null || url.equals(""))
		{
			return null;
		}
		try
		{
			URL u = new URL(url);
			HttpURLConnection httpConn = (HttpURLConnection) u.openConnection();
			if (output != null && !output.equals(""))
				httpConn.setDoOutput(Boolean.parseBoolean(output));
			if (input != null && !input.equals(""))
				httpConn.setDoInput(Boolean.parseBoolean(input));
			if (cache != null && !cache.equals(""))
				httpConn.setUseCaches(Boolean.parseBoolean(cache));
			if (method != null && !method.equals(""))
				httpConn.setRequestMethod(method);
			if (type != null && !type.equals(""))
				httpConn.setRequestProperty("Content-Type", type);
			if (connection != null && !connection.equals(""))
				httpConn.setRequestProperty("Connection", connection);
			if (charset != null && !charset.equals(""))
				httpConn.setRequestProperty("Charset", charset);
			httpConn.connect();
			if (param != null && !param.equals(""))
			{
				DataOutputStream dos = new DataOutputStream(httpConn.getOutputStream());
				dos.write(param.getBytes("utf-8"));
				dos.flush();
				dos.close();
			}
			int resultCode = httpConn.getResponseCode();
			if (HttpURLConnection.HTTP_OK == resultCode)
			{
				BufferedReader responseReader = new BufferedReader(
						new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
				return responseReader;
			} else
			{
				return null;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	private StringBuffer readText(BufferedReader reader)
	{

		StringBuffer sb = new StringBuffer();
		if (reader == null)
		{
			return sb;
		}
		String line = null;
		try
		{
			while ((line = reader.readLine()) != null)
			{
				sb.append(line);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				reader.close();
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return sb;
	}

	public void load()
	{
		CmdFactory.getInstance().addCmd(HttpCompent.class.getName(), this);
	}

}
