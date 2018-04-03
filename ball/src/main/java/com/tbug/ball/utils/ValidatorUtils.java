package com.tbug.ball.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 检验规则工具类
 *
 */
public class ValidatorUtils {
	/**
	 * 校验密码
	 * 1.非空  2.长度大于6
	 * @param password
	 * @return true 符合规则
	 */
	public static boolean checkPasswordRule(String password) {
		//长度必须为大于等于6
		if(StringUtils.isBlank(password))
			return false;
		if(password.length()<6)
			return false;
		return true;
		/*
		//同时包含大写字母，小写字母，数字和特殊字符
		String strchar = "!,.@#$%^&*?_~";
		String strlow = "abcdefghijklmnopqrstuvwxyz";
		String strup = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String strnum = "0123456789";
		boolean charFlag = false,lowFlag = false,upFlag = false,numFlag = false;
		for(int i = 0;i<password.length();i++) {
			if(strchar.indexOf(password.charAt(i))!=-1) {
				charFlag = true;
			}else if(strlow.indexOf(password.charAt(i))!=-1) {
				lowFlag = true;
			}else if(strup.indexOf(password.charAt(i))!=-1) {
				upFlag = true;
			}else if(strnum.indexOf(password.charAt(i))!=-1) {
				numFlag = true;
			}
		}
		
		if(charFlag && lowFlag && upFlag && numFlag)
			return true;
		else
		    return false;
		*/
	}
	/**
	 * 验证用户名
	 * 1.非空	2.长度6至32位 3.以字母开头 4.包含大小写字母 、数字、-_.@符
	 * @param loginName
	 * @return true 符合规则
	 */
	public static boolean checkLoginNameRule(String loginName) {
		if(StringUtils.isBlank(loginName)) 
			return false;
		if(loginName.length() < 6) 
			return false;
		if(loginName.length() > 32) 
			return false;
		
		String str = ".@-_abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		
		//以字母开头
		char startChr = loginName.charAt(0);
		if(!(startChr>='A'&&startChr<='Z'||startChr>='a'&&startChr<='z'))
			return false;
		
		//可以为字母、数字、指定符号中的任意一种
		for(int i = 1;i<loginName.length();i++) {
			if(str.indexOf(loginName.charAt(i)) == -1)
				return false;
		}
		
		return true;
	}
	
	/**
	 * 验证手机号码
	 * @param str
	 * @return
	 */
	public static boolean isMobile(String phoneNo) {   
		Pattern p = null;  
		Matcher m = null;  
		boolean b = false;   
		p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号  
		m = p.matcher(phoneNo);
		b = m.matches();   
		return b;  
	}  

	
	public static void main(String[] args) {
		String loginName = "ffd_3se";
		boolean flag = ValidatorUtils.checkLoginNameRule(loginName);
		System.out.println(flag);
		String phoneNo = "12823232323";
		boolean flagNo = ValidatorUtils.isMobile(phoneNo);
		System.out.println(flagNo);
		
	}
}
