package com.tbug.ball.manage.config.shiro;

import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.tbug.ball.manage.util.ShiroUtils;
import com.tbug.ball.service.MenuService;
import com.tbug.ball.service.ServiceException;
import com.tbug.ball.service.TradeService;
import com.tbug.ball.service.Dto.user.TradeUserDto;
import com.tbug.ball.service.common.DBContants.TradeUserConst;

public class UserRealm extends AuthorizingRealm {
	private static final Logger logger = LoggerFactory.getLogger(UserRealm.class);
	
	@Autowired
	TradeService tradeService;
	@Autowired
	MenuService menuService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		Integer userId = ShiroUtils.getUserId();
		Set<String> perms = menuService.listPerms(userId);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(perms);
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal();

		// 查询用户信息
		TradeUserDto user = null;
		try {
			user = tradeService.getTradeUserDtoByLoginName(username);
		} catch (ServiceException e) {
			logger.error(e.getMessage(), e);
			throw new UnknownAccountException("账号不存在1");
		}

		// 账号不存在
		if (user == null || StringUtils.isEmpty(user.getUserCode())) {
			throw new UnknownAccountException("账号不存在2");
		}

		// 账号锁定
		if (!TradeUserConst.STATUS_NORMAL.equals(user.getStatus())) {
			throw new LockedAccountException("账号状态异常");
		}
		
		ByteSource bsSalt = ByteSource.Util.bytes(user.getSalt());
		
		SimpleAuthenticationInfo info = 
				new SimpleAuthenticationInfo(user,Md5Hash.fromHexString(user.getPassword()),bsSalt, getName());
		
		return info;
	}

}
