package com.zcbl.compent.restful.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author jys 2016年11月10日
 */
public class EncodingFilter implements Filter
{
	private String encoding;

	public void destroy()
	{
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException
	{
		request.setCharacterEncoding(encoding);
		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException
	{
		this.encoding = filterConfig.getInitParameter("encoding");
	}
}
