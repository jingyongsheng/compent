package com.zcbl.compent.config;

import com.zcbl.compent.config.factory.ConfigFactory;
import com.zcbl.compent.config.factory.ConfigInter;
import com.zcbl.compent.config.impl.GlobalConfig;

/**
 * @author jys 2016年11月7日
 */
public class ConfigClient
{
	public static void addConf(ConfigInter inter)
	{
		ConfigFactory.addConfigInter(inter);
	}

	public static GlobalConfig getGlobal()
	{
		return GlobalConfig.getInstance();
	}
}
