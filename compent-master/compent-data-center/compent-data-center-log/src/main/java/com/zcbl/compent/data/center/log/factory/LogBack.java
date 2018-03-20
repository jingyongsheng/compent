package com.zcbl.compent.data.center.log.factory;

import com.zcbl.compent.data.center.log.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogBack implements Log {
	protected Logger logger;

	public LogBack(Class c) {
		logger = LoggerFactory.getLogger(c);
	}

	@Override
	public void error(String message) {
		logger.error(message);
	}

	@Override
	public void warn(String message) {
		logger.warn(message);
	}

	@Override
	public void info(String message) {
		logger.info(message);
	}

	@Override
	public void debug(String message) {
		logger.debug(message);
	}

	@Override
	public void track(String message) {
		logger.trace(message);
	}

}
