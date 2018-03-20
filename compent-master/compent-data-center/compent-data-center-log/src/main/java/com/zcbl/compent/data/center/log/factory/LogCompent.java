package com.zcbl.compent.data.center.log.factory;

import com.zcbl.compent.data.center.log.Log;

/**
 * @author jys 2016年11月10日
 */
public class LogCompent {
	private static LogCompent factory = new LogCompent();

	private LogCompent() {
	}

	public static LogCompent getInstance() {
		return factory;
	}

	public Log getLog(Class c) {
		return new LogBack(c);
	}
}
