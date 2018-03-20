package com.zcbl.compent.restful.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zcbl.compent.task.client.CompentClient;

/**
 * @author jys 2016年11月10日
 */
public class RestfulFilter implements Filter {

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		FilterFactory factory = new FilterFactory(req, res, chain);
		factory.run();
		factory = null;
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		CompentClient.getInstance().run();
	}
}
