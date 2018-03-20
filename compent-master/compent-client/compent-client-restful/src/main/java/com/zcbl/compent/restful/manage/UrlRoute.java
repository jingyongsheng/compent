package com.zcbl.compent.restful.manage;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.zcbl.compent.restful.bridge.Url;
import com.zcbl.compent.restful.cache.HttpCache;
import com.zcbl.compent.restful.request.RequestCompent;
import com.zcbl.compent.restful.response.ResponseCompent;

/**
 * @author jys 2016年11月10日
 */
public class UrlRoute {
	private static UrlRoute route = new UrlRoute();

	private UrlRoute() {
	}

	public static UrlRoute getInstance() {
		return route;
	}

	public void getRoute(RequestCompent request, ResponseCompent response, Url url) {
		if (url.getStatus() != null) {
			if (url.getStatus().equals(Status.ENABLE.getValue())) {
				int key = HttpCache.getInstance().addHttp(request, response, url);
				try {
					CompentUrlManager.getInstance().getResolver().excute(key);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					HttpCache.getInstance().clear(key);
				}
				return;
			}
		}
		renderString(response, "error");
	}

	private void renderString(HttpServletResponse response, String string) {
		try {
			response.reset();
			response.getWriter().print(string);
			response.setCharacterEncoding("utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public enum Status {
		ENABLE("可用", "true"), DISABLE("不可用", "false");
		private String key;
		private String value;

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		private Status(String key, String value) {
			this.key = key;
			this.value = value;
		}
	}
}
