package com.tbug.ball.manage.common.aspect;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.tbug.ball.manage.common.annotation.Log;
import com.tbug.ball.manage.util.HttpContextUtils;
import com.tbug.ball.manage.util.IPUtils;
import com.tbug.ball.manage.util.ShiroUtils;
import com.tbug.ball.service.LogService;
import com.tbug.ball.service.Dto.system.SysLogDto;
import com.tbug.ball.service.Dto.user.TradeUserDto;

@Aspect
@Component
public class LogAspect {
	
	@Autowired
	LogService logService;

	@Pointcut("@annotation(com.tbug.ball.manage.common.annotation.Log)")
	public void logPointCut() {
	}

	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long beginTime = System.currentTimeMillis();
		try {
			// 执行方法
			Object result = point.proceed();

			// 保存日志
			saveLog(point,(System.currentTimeMillis() - beginTime),"success");
			return result;
		} catch (Throwable e){
			String msg = e.getMessage();
			if (StringUtils.isEmpty(msg)){
				msg = "";
			} else if (msg.length() > 1000){
				msg = msg.substring(0, 1000);
			}
			saveLog(point, (System.currentTimeMillis() - beginTime), msg);
			throw new Throwable(e);
		}
	}

    @After("logPointCut()")  
    public void after() {  
    }  
	
    @AfterReturning("logPointCut()")
    public void afterReturning(){
    }
    
    @AfterThrowing("logPointCut()")
    public void afterThrowing(){
    }
    
	private void saveLog(ProceedingJoinPoint joinPoint, long time, String msg) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		SysLogDto sysLog = new SysLogDto();
		Log syslog = method.getAnnotation(Log.class);
		if (syslog != null) {
			// 注解上的描述
			sysLog.setOperation(syslog.value());
		}
		// 请求的方法名
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		sysLog.setMethod(className + "." + methodName + "()");
		// 请求的参数
		Object[] args = joinPoint.getArgs();
		try {
			String params = JSON.toJSONString(args);
			sysLog.setParams(params);
		} catch (Exception e) {

		}
		// 获取request
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		// 设置IP地址
		sysLog.setIp(IPUtils.getIpAddr(request));
		// 用户名
		TradeUserDto tradeUserDto = ShiroUtils.getUser();
		if (null == tradeUserDto) {
			if (null != sysLog.getParams()) {
				sysLog.setUserId(-1L);
				sysLog.setUsername(sysLog.getParams());
			} else {
				sysLog.setUserId(-1L);
				sysLog.setUsername("获取用户信息为空");
			}
		} else {
			sysLog.setUserId(ShiroUtils.getUserId().longValue());
			sysLog.setUsername(ShiroUtils.getUser().getLoginName());
		}
		sysLog.setTime((int) time);
		// 系统当前时间
		Date date = new Date();
		sysLog.setGmtCreate(date);
		sysLog.setMsg(msg);
		
		// 保存系统日志
		logService.save(sysLog);
	}
}
