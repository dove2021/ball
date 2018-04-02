package com.tbug.ball.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tbug.ball.service.Dto.user.CustomerDto;
import com.tbug.ball.utils.ShiroUtils;

/**
 * spring mvc 拦截器
 * @author T_BUG
 *
 */
public class AccessInterceptor extends HandlerInterceptorAdapter {

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		super.afterConcurrentHandlingStarted(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		if(session == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return false;
		}
		
		CustomerDto user = ShiroUtils.getUser();
		if(user == null || StringUtils.isEmpty(user.getCustomerCode())){
			if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
				response.setContentType("application/json;charset=UTF-8");
				String json = "{\"statusCode\":\"301\", \"message\":\"会话超时\"}";				
				PrintWriter pw = response.getWriter();   
				pw.print(json);
				pw.close();
			} else {
				response.sendRedirect(request.getContextPath() + "/login");
			}
			return false;
		}
		return true;
	}
	
	
}
