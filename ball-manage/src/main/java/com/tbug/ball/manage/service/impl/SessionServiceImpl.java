package com.tbug.ball.manage.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbug.ball.manage.service.SessionService;
import com.tbug.ball.manage.vo.UserOnline;
import com.tbug.ball.service.ServiceException;
import com.tbug.ball.service.Dto.user.TradeUserDto;

/**
 * 待完善
 * 
 * @author bootdo
 *
 */

@Service
public class SessionServiceImpl implements SessionService {
	
	@Autowired
	private SessionDAO sessionDAO;

	@Override
	public List<UserOnline> list() {
		List<UserOnline> list = new ArrayList<>();
		Collection<Session> sessions = sessionDAO.getActiveSessions();
		for (Session session : sessions) {
			UserOnline userOnline = new UserOnline();
			TradeUserDto userDO = new TradeUserDto();
			SimplePrincipalCollection principalCollection = new SimplePrincipalCollection();
			if (session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) == null) {
				continue;
			} else {
				principalCollection = (SimplePrincipalCollection) session
						.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
				userDO = (TradeUserDto) principalCollection.getPrimaryPrincipal();
				userOnline.setUsername(userDO.getLoginName());
			}
			userOnline.setId((String) session.getId());
			userOnline.setHost(session.getHost());
			userOnline.setStartTimestamp(session.getStartTimestamp());
			userOnline.setLastAccessTime(session.getLastAccessTime());
			userOnline.setTimeout(session.getTimeout());
			list.add(userOnline);
		}
		return list;
	}

	@Override
	public Collection<Session> sessionList() {
		return sessionDAO.getActiveSessions();
	}

	@Override
	public boolean forceLogout(String sessionId) throws ServiceException {
		try {
			Session session = sessionDAO.readSession(sessionId);
			if (session != null) {  
				session.stop();  
				sessionDAO.delete(session);  
			}
		} catch (Exception e){
			throw new ServiceException(e.getMessage(), e);
		}
		
		return true;
	}
}
