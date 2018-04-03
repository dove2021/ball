package com.tbug.ball.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang3.StringEscapeUtils;


public class XSSFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		chain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) request), response);
	}

	@Override
	public void destroy() {
	}

    final class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
    	public XssHttpServletRequestWrapper(HttpServletRequest request) {
    		super(request);
    	}

    	@Override
    	public String getHeader(String name) {
    		return StringEscapeUtils.escapeHtml4(super.getHeader(name));
    	}

    	@Override
    	public String getQueryString() {
    		return StringEscapeUtils.escapeHtml4(super.getQueryString());
    	}

    	@Override
    	public String getParameter(String name) {
    		return StringEscapeUtils.escapeHtml4(super.getParameter(name));
    	}

    	@Override
    	public String[] getParameterValues(String name) {
    		String[] values = super.getParameterValues(name);
    		if (values != null) {
    			int length = values.length;
    			String[] escapseValues = new String[length];
    			for (int i = 0; i < length; i++) {
    				String tempValue = StringEscapeUtils.escapeHtml4(values[i]);
    				escapseValues[i] = tempValue;
    			}
    			return escapseValues;
    		}
    		return super.getParameterValues(name);
    	}
    }
}
