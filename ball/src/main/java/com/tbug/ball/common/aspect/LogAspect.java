package com.tbug.ball.common.aspect;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.tbug.ball.common.annotation.Log;
import com.tbug.ball.service.LogService;
import com.tbug.ball.service.Dto.system.SysLogDto;
import com.tbug.ball.service.Dto.user.CustomerDto;
import com.tbug.ball.utils.HttpContextUtils;
import com.tbug.ball.utils.IPUtils;
import com.tbug.ball.utils.ShiroUtils;

@Aspect
@Component
public class LogAspect {
	
	@Autowired
	LogService logService;

	@Pointcut("@annotation(com.tbug.ball.common.annotation.Log)")
	public void logPointCut() {}

    @Before("logPointCut()")  
    public void before() {  
    }  
	
	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long beginTime = System.currentTimeMillis();
		try {
			// 执行方法
			Object result = point.proceed();

			// 保存日志
			saveLog(point,(System.currentTimeMillis() - beginTime),"Y");
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
			if (null != params && params.length() > 5000){
				sysLog.setParams(params.substring(0, 5000));
			} else {
				sysLog.setParams(params);
			}
		} catch (Exception e) {
			
		}
		// 获取request
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		// 设置IP地址
		sysLog.setIp(IPUtils.getIpAddr(request));
		// 用户名
		CustomerDto customerDto = ShiroUtils.getUser();
		if (null == customerDto) {
			if (null != sysLog.getParams()) {
				sysLog.setUserId(-1L);
				sysLog.setUsername(sysLog.getParams().length() > 50 ? sysLog.getParams().substring(0, 50) : sysLog.getParams());
			} else {
				sysLog.setUserId(-1L);
				sysLog.setUsername("null");
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
