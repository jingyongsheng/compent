package com.zcbl.compent.config.factory;

import java.io.File;
import java.io.FilenameFilter;

public class PatternFilenameUtils implements FilenameFilter
{

	public boolean accept(File dir, String name)
	{
		return (isCompent(name));
	}

	public boolean isCompent(String file)
	{
		if (file.toLowerCase().startsWith("compent"))
		{
			return true;
		} else
		{
			return false;
		}
	}
}
