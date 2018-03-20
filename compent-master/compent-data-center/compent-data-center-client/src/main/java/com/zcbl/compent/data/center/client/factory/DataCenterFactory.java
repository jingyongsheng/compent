package com.zcbl.compent.data.center.client.factory;

import com.zcbl.compent.data.center.api.Center;
import com.zcbl.compent.db.center.core.bridge.CompentEntityExcuteImpl;
import com.zcbl.compent.db.center.core.bridge.EntityExcuteImpl;

/**
 * @author jys 2016年11月9日
 */
public class DataCenterFactory
{
	private static DataCenterFactory factory = new DataCenterFactory();

	private DataCenterFactory()
	{
	}

	public Center getObjectFactory()
	{
		return EntityExcuteImpl.getIntance();
	}

	public Center getSqlFactory()
	{
		return EntityExcuteImpl.getIntance();
	}

	public Center getEntityFactory()
	{
		return CompentEntityExcuteImpl.getIntance();
	}

	public static DataCenterFactory getIntance()
	{
		return factory;
	}
}
