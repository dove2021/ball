package com.tbug.ball.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.filter.authc.PassThruAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import com.alibaba.fastjson.JSON;
import com.tbug.ball.common.AjaxResult;

public class AccessAuthenticationFilter extends PassThruAuthenticationFilter {

	@Override
	protected void redirectToLogin(ServletRequest request,
			ServletResponse response) throws IOException {
		HttpServletRequest req = (HttpServletRequest) request;
		if("XMLHttpRequest".equals(req.getHeader("X-Requested-With"))){
			response.setContentType("application/json;charset=UTF-8");
			String json = JSON.toJSONString(AjaxResult.error("会话超时"));	
			PrintWriter pw = response.getWriter();   
			pw.print(json);
			pw.close();
		}else{
			WebUtils.issueRedirect(request, response, "/login");
		}
	}

}
