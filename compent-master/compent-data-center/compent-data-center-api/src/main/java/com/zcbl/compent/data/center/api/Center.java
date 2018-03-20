package com.zcbl.compent.data.center.api;

import com.zcbl.compent.data.center.api.bean.Query;
import com.zcbl.compent.data.center.api.bean.Result;

/**
 * @author jys 2016年11月7日
 */
public interface Center
{
	public void save(Result rs, Query obj);

	public void update(Result rs, Query obj);

	public void query(Result rs, Query source);
}
