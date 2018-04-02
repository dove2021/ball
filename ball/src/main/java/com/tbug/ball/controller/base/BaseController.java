package com.tbug.ball.controller.base;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.tbug.ball.service.Dto.user.CustomerDto;

public class BaseController {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected HttpServletRequest getRequest() {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();  
		return ((ServletRequestAttributes) ra).getRequest();
	}

	protected CustomerDto getCurrUser(){
        Subject subject = SecurityUtils.getSubject();
        return (CustomerDto) subject.getPrincipal();
	}
	
	protected void loginOff(){
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
	}
	
	protected boolean checkLogin(){
		Subject subject = SecurityUtils.getSubject();
		return subject.isAuthenticated();
	}
	
    @InitBinder
    public void dateBinder(WebDataBinder binder) {
        MyDateEditor editor = new MyDateEditor();
        binder.registerCustomEditor(Date.class, editor);
    }
    
    private class MyDateEditor extends PropertyEditorSupport {
        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = null;
            try {
                date = format.parse(text);
            } catch (ParseException e) {
                format = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    date = format.parse(text);
                } catch (ParseException e1) {
                    logger.error("系统异常:"+e1,e1);
                }
            }
            setValue(date);
        }
    }
}
