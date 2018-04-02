package com.tbug.ball.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.tbug.ball.service.Dto.user.CustomerDto;

public class ShiroUtils {
	public static Subject getSubjct() {
		return SecurityUtils.getSubject();
	}
	public static CustomerDto getUser() {
		return (CustomerDto)getSubjct().getPrincipal();
	}
	public static Integer getUserId() {
		return getUser().getId();
	}
	public static void logout() {
		getSubjct().logout();
	}
}
