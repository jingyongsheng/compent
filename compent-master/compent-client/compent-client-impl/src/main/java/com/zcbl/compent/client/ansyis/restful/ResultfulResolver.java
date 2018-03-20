package com.zcbl.compent.client.ansyis.restful;

import com.zcbl.compent.data.center.log.Log;
import com.zcbl.compent.data.center.log.factory.LogCompent;
import com.zcbl.compent.function.user.RequestCompent;
import com.zcbl.compent.language.respertories.CmdFactory;
import com.zcbl.compent.language.respertories.CommandFactory;
import com.zcbl.compent.language.respertories.CommandFactory.CommandStack;
import com.zcbl.compent.language.respertories.TemplateFactory;
import com.zcbl.compent.restful.bridge.Resolver;
import com.zcbl.compent.restful.cache.HttpCache;
import com.zcbl.compent.restful.cache.HttpCache.Http;
import com.zcbl.compent.restful.manage.CompentUrlManager;
import com.zcbl.compent.task.client.annation.SysLoader;
import com.zcbl.compent.task.client.sys.Loader;

@SysLoader
public class ResultfulResolver implements Resolver, Loader {
	static Log log = LogCompent.getInstance().getLog(ResultfulResolver.class);

	@Override
	public void load() {
		CompentUrlManager.getInstance().setResolver(this);
	}

	@Override
	public void excute(int key) {
		Http http = HttpCache.getInstance().getHttp(key);
		try {
			log.info(http.getUri().getUrl());
			CommandStack stack = CommandFactory.getInstance().buildCommandStack();
			String userKey = CmdFactory.getInstance().getUserKey(RequestCompent.class.getName());
			if (http.getParamter() != null && userKey != null)
				stack.setPointer(userKey, http.getParamter());
			stack = CommandFactory.getInstance().excuteCommand(http.getUri().getUrl(), stack);
			StringBuffer sb = TemplateFactory.getInstance().getResult(http.getUri().getUrl(), stack);
			stack.clear();
			HttpCache.getInstance().renderString(key, sb.toString());
		} catch (Exception e) {
			log.info(e.getMessage());
		}
	}
}
