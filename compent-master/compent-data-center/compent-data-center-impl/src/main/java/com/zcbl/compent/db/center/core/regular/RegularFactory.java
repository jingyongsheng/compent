package com.zcbl.compent.db.center.core.regular;

/**
 * @author jys 2016年11月8日
 */
public class RegularFactory
{
	public static Regular getRegular()
	{
		return HashRegular.getInstance();
	}
}
