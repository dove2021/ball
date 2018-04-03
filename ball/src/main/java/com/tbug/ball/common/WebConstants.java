package com.tbug.ball.common;

public class WebConstants {

	public interface LoginConst{
		/** cookie生命时长 */
		int COOKIE_MAX_AGE_DAY_UNIT = 24 * 60 * 60;
		
		/** 客户登录错误次数 */
		String LOGIN_ERROR_NUM_KEY = "LOGIN_ERROR_NUM";
		
		/** 验证码发送时间key */
		String SEND_VERIFY_CODE_TIME_KEY = "SEND_VERIFY_CODE_TIME";
		
		/** 登录图形验证码key */
		String LOGIN_KAPTCHA_KEY = "LOGIN_KAPTCHA_KEY";
		
		/** 当前用户 */
		String CURR_USER_KEY = "CURR_USER";
	}
	
}
