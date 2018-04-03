package com.tbug.ball.service.common.security;

import java.util.UUID;

public class PasswordHelper {
	public static String generateSalt() {
		UUID uuid=UUID.randomUUID();
		return uuid.toString();
	}
	public static String encryptPassword(String salt, String password){
		return MD5Util.MD5Encode(salt+password,"utf-8");
	}
	
	public static void main(String[] args){
		String salt=generateSalt();
		String password=encryptPassword(salt,"app");
		
		System.out.println("salt="+salt);
		System.out.println("password="+password);
	}
}
