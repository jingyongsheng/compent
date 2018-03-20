package com.zcbl.compent.restful.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zcbl.compent.restful.bridge.Url;
import com.zcbl.compent.restful.manage.CompentUrlManager;
import com.zcbl.compent.restful.manage.UrlRoute;
import com.zcbl.compent.restful.request.RequestCompent;
import com.zcbl.compent.restful.response.ResponseCompent;

public class FilterFactory {
	private HttpServletRequest httpServletRequest;
	private HttpServletResponse httpServletResponse;
	private FilterChain chain;

	public FilterFactory(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
		this.httpServletRequest = request;
		this.httpServletResponse = response;
		this.chain = chain;
	}

	public FilterFactory(HttpServletRequest request, HttpServletResponse response) {
		this.httpServletRequest = request;
		this.httpServletResponse = response;
	}

	public void run() throws IOException, ServletException {
		Url url = CompentUrlManager.getInstance().getUrl(getPath());
		if (url != null) {
			RequestCompent rcompent = new RequestCompent(httpServletRequest);
			ResponseCompent pcoment = new ResponseCompent(httpServletResponse);
			UrlRoute.getInstance().getRoute(rcompent, pcoment, url);
			String content = new String(pcoment.getReuslt(), "utf-8");
			PrintWriter writer = httpServletResponse.getWriter();
			writer.print(content);
			writer.close();
		} else {
			if (chain != null) {
				chain.doFilter(httpServletRequest, httpServletResponse);
			} else {
				renderString(httpServletResponse, "error");
			}
		}
	}

	private void renderString(HttpServletResponse response, String string) {
		try {
			response.reset();
			response.getWriter().print(string);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getPath() {
		String path = httpServletRequest.getServletPath();
		String pathInfo = httpServletRequest.getPathInfo();
		if (pathInfo != null && pathInfo.length() > 0) {
			path = path + pathInfo;
		}
		StringBuffer sb = new StringBuffer();
		sb.append(path);
		if (sb.length() > 1 && path.endsWith("/")) {
			sb.substring(0, path.length() - 1);
		}
		return sb.toString();
	}

}
