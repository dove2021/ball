package com.tbug.ball.realm;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tbug.ball.service.BrokerService;
import com.tbug.ball.service.CustomerService;
import com.tbug.ball.service.MemberService;
import com.tbug.ball.service.Dto.user.BrokerDto;
import com.tbug.ball.service.Dto.user.CustomerDto;
import com.tbug.ball.service.Dto.user.MemberDto;
import com.tbug.ball.service.common.DBContants.BrokerConst;
import com.tbug.ball.service.common.DBContants.CustomerConst;
import com.tbug.ball.service.common.DBContants.DB;
import com.tbug.ball.service.common.DBContants.MemberConst;

public class AuthorizationRealm extends AuthorizingRealm {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	CustomerService customerService;
	@Autowired
	MemberService memberService;
	@Autowired
	BrokerService brokerService;

	/**
	 * 控制权限
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		SimpleAuthorizationInfo authorization = new SimpleAuthorizationInfo(); 
		Set<String> stringPermissions = new HashSet<>();
		authorization.setStringPermissions(stringPermissions);
		
		return authorization;
	}

	/**
	 * 登录
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		try {
			if(token == null) return null;
			String username = (String) token.getPrincipal();
			
			CustomerDto user = customerService.cutomerLogin(username);
			if (!CustomerConst.STATUS_NORMAL.equals(user.getStatus())){
				throw new LockedAccountException("用户状态异常");
			}
			MemberDto memberDto = memberService.getMemberDtoById(user.getMemberId());
			if (!MemberConst.STATUS_NORMAL.equals(memberDto.getStatus())){
				throw new LockedAccountException("所属会员状态异常");
			}
			if (null != user.getBrokerId() && DB.NULL_INT != user.getBrokerId().intValue()){
				BrokerDto brokerDto = brokerService.getBrokerDtoById(user.getBrokerId());
				if (!BrokerConst.STATUS_NORMAL.equals(brokerDto.getStatus())){
					throw new LockedAccountException("所属代理状态异常");
				}
			}
			
			ByteSource bsSalt = ByteSource.Util.bytes(user.getSalt());
			
			SimpleAuthenticationInfo AuthenticationInfo = 
					new SimpleAuthenticationInfo(user, Md5Hash.fromHexString(user.getPassword()), bsSalt, getName());
			return AuthenticationInfo;
		} catch (Exception e) {
			logger.error("认证出错：", e);
			throw new AuthenticationException("用户名或密码错误！");
		}
	}
	
}
