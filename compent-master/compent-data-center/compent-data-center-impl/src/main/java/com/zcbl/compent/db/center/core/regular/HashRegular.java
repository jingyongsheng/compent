package com.zcbl.compent.db.center.core.regular;

/**
 * @author jys 2016年11月8日
 */
public class HashRegular implements Regular
{

	private static HashRegular reg = new HashRegular();

	private HashRegular()
	{
	}

	public static HashRegular getInstance()
	{
		return reg;
	}

	@Override
	public String getDataSource(String app, String entity)
	{
		return null;
	}

	@Override
	public String getDatatable(String entity)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
