package com.zcbl.compent.data.center;

import java.util.List;

public interface DataCenter
{
	public int save(String sql) throws Exception;

	public int update(String sql) throws Exception;

	public List find(String sql) throws Exception;

	public int save(Object obj) throws Exception;

	public int update(Object obj) throws Exception;

	public List find(Object obj) throws Exception;
}
