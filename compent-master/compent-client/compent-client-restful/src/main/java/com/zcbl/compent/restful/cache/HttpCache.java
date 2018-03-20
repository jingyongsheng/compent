package com.zcbl.compent.restful.cache;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.zcbl.compent.restful.bridge.Url;
import com.zcbl.compent.restful.request.RequestCompent;
import com.zcbl.compent.restful.response.ResponseCompent;
import com.zcbl.compent.restful.utils.RequestUtils;

public class HttpCache {
	public Map<Integer, Http> map = new ConcurrentHashMap<Integer, Http>();
	private static HttpCache cache = new HttpCache();
	static AtomicInteger no = new AtomicInteger();

	private HttpCache() {
	}

	public static HttpCache getInstance() {
		return cache;
	}

	public Integer addHttp(RequestCompent re, ResponseCompent res, Url url) {
		Http http = new Http(re, res, url);
		int k = no.incrementAndGet();
		map.put(k, http);
		return k;
	}

	public Http getHttp(Integer key) {
		return map.get(key);
	}

	public void clear(Integer key) {
		map.remove(key);
	}

	public void renderString(int key, String string) {
		Http http = map.get(key);
		if (http != null)
			try {
				http.getRes().reset();
				http.getRes().getWriter().print(string);
				http.getRes().setCharacterEncoding("utf-8");
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	public class Http {
		RequestCompent req;
		ResponseCompent res;
		Url uri;
		Map<String, Object> paramter;

		public Url getUri() {
			return uri;
		}

		public void setUri(Url url) {
			this.uri = url;
		}

		public Http(RequestCompent req, ResponseCompent res, Url url) {
			this.req = req;
			this.res = res;
			this.uri = url;
			init();
		}

		public Map<String, Object> getParamter() {
			return paramter;
		}

		public void setParamter(Map<String, Object> paramter) {
			this.paramter = paramter;
		}

		public RequestCompent getReq() {
			return req;
		}

		public void setReq(RequestCompent req) {
			this.req = req;
		}

		public ResponseCompent getRes() {
			return res;
		}

		public void setRes(ResponseCompent res) {
			this.res = res;
		}

		public void init() {
			res.setContentType("application/json;charset=utf-8");
			Map<String, Object> h = uri.getHeader();
			if (h != null && h.size() > 0) {
				for (Iterator<String> ite = h.keySet().iterator(); ite.hasNext();) {
					String key = ite.next();
					res.setHeader(key, (String) h.get(key));
				}
			}
			if (uri.getType() != null)
				res.setContentType(uri.getType());
			paramter = RequestUtils.toToMap(req.getParameterMap());
		}
	}
}
