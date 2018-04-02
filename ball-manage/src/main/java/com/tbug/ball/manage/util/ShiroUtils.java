package com.tbug.ball.manage.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.tbug.ball.service.Dto.user.TradeUserDto;

public class ShiroUtils {
	public static Subject getSubjct() {
		return SecurityUtils.getSubject();
	}
	public static TradeUserDto getUser() {
		return (TradeUserDto)getSubjct().getPrincipal();
	}
	public static Integer getUserId() {
		return getUser().getId();
	}
	public static void logout() {
		getSubjct().logout();
	}
}
