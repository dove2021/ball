package com.tbug.ball.manage.service;

import java.util.Collection;
import java.util.List;

import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;

import com.tbug.ball.manage.vo.UserOnline;
import com.tbug.ball.service.ServiceException;

@Service
public interface SessionService {
	
	List<UserOnline> list();

	Collection<Session> sessionList();
	
	boolean forceLogout(String sessionId) throws ServiceException;
}
