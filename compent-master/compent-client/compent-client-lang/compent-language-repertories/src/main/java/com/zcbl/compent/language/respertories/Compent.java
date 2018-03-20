package com.zcbl.compent.language.respertories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Compent implements Cloneable
{
	public String pointer;
	public String funName;
	List<Args> args = new ArrayList<Args>();
	public Map<String, Object> attrs = new HashMap<String, Object>();

	public Map<String, Object> getAttrs()
	{
		return attrs;
	}

	public void setAttrs(Map<String, Object> attrs)
	{
		this.attrs = attrs;
	}

	public List<Args> getArgs()
	{
		return args;
	}

	public void setArgs(List<Args> args)
	{
		this.args = args;
	}

	public String getPointer()
	{
		return pointer;
	}

	public void setPointer(String pointer)
	{
		this.pointer = pointer;
	}

	public String getFunName()
	{
		return funName;
	}

	public void setFunName(String funName)
	{
		this.funName = funName;
	}

	public Args buildArgs()
	{
		return new Args();
	}

	public class Args
	{
		public String Key;
		public String originl;

		public String getOriginl()
		{
			return originl;
		}

		public void setOriginl(String originl)
		{
			this.originl = originl;
		}

		public String getKey()
		{
			return Key;
		}

		public void setKey(String key)
		{
			Key = key;
		}
	}

	public Object clone()
	{
		try
		{
			return super.clone();
		} catch (CloneNotSupportedException e)
		{
			return null;
		}
	}
}
