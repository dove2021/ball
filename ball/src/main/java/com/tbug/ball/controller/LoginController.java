package com.tbug.ball.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tbug.ball.common.AjaxResult;
import com.tbug.ball.common.annotation.Log;
import com.tbug.ball.controller.base.BaseController;
import com.tbug.ball.service.CustomerService;
import com.tbug.ball.utils.CookieUtils;

@Controller
public class LoginController extends BaseController {

	@Autowired
	CustomerService customerService;
	
	@GetMapping("/login")
	public String login(){
		return "/login/login";
	}
	
	@Log("客户登陆")
	@ResponseBody
	@PostMapping("/login")
	AjaxResult loginAjax(HttpServletRequest request,HttpServletResponse response){
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		if (StringUtils.isEmpty(userName)){
			return AjaxResult.error("用户名不能为空");
		}
		if (StringUtils.isEmpty(password)){
			return AjaxResult.error("密码不能为空");
		}
		Subject subject = SecurityUtils.getSubject();
		if(!subject.isAuthenticated()){
			UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
			token.setRememberMe(false);
			try{
				subject.login(token);
			} catch (UnknownAccountException uae) {
				logger.error(uae.getMessage(), uae);
				return AjaxResult.error("用户名或密码错误！");
			} catch (IncorrectCredentialsException ice) {
				logger.error(ice.getMessage(), ice);
				return AjaxResult.error("用户名或密码错误！");
			} catch (LockedAccountException lae) {
				logger.error(lae.getMessage(), lae);
				return AjaxResult.error("账户状态异常");
			}
			catch (AuthenticationException ae) {
				logger.error("登录错误：", ae);
				return AjaxResult.error("登陆失败!");
			}
		}
		// 加入cookie
		CookieUtils.addCookie(response, "userName", userName, 14*24*3600);
		
		return AjaxResult.ok();
	}
	
	@ResponseBody
	@GetMapping("/login/check")
	public AjaxResult loginCheck(){
		if (checkLogin()){
			return AjaxResult.ok();
		}
		return AjaxResult.error("请先登陆");
	}
	
	@ResponseBody
	@GetMapping("/login/off")
	public AjaxResult off(){
		if (!checkLogin()){
			return AjaxResult.error("您还未登录");
		}
		loginOff();
		return AjaxResult.ok();
	}
	
	@GetMapping("/unauthorized")
	public String unauthorized(){
		return "error/500";
	}
	
}
