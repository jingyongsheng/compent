package com.zcbl.compent.data.center.log;

/**
 * @author jys 2016年11月7日
 */
public interface Log {
	public void error(String message);

	public void warn(String message);

	public void info(String message);

	public void debug(String message);

	public void track(String message);
}
