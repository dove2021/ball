package com.tbug.ball.manage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tbug.ball.manage.common.annotation.Log;
import com.tbug.ball.manage.controller.base.BaseController;
import com.tbug.ball.manage.util.R;
import com.tbug.ball.service.MenuService;
import com.tbug.ball.service.ScheduleService;
import com.tbug.ball.service.TradeService;
import com.tbug.ball.service.Dto.TreeDto;
import com.tbug.ball.service.Dto.schedule.ScheduleDto;
import com.tbug.ball.service.Dto.system.SysMenuDto;

@Controller
public class LoginController extends BaseController{

	@Autowired 
	TradeService tradeService;
	
	@Autowired 
	ScheduleService scheduleService;
	
	@Autowired
	MenuService menuService;
	
	@GetMapping({"/"})
	String welcome(Model model) {
		return "login";
	}
	@GetMapping("/login")
	String login() {
		return "login";
	}

	@GetMapping("/logout")
	String logout() {
		loginOff();
		return "redirect:/login";
	}

	@GetMapping("/main")
	String main() {
		return "main";
	}

	@Log("后台登陆")
	@ResponseBody
	@PostMapping("/login")
	R ajaxLogin(String username, String password) {
		if (StringUtils.isEmpty(username)){
			return R.error("用户名不能为空");
		}
		if (StringUtils.isEmpty(password)){
			return R.error("密码不能为空");
		}

		Subject currentUser = SecurityUtils.getSubject();
		if(!currentUser.isAuthenticated()){
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			token.setRememberMe(true);
			try{
				currentUser.login(token);
			} catch (UnknownAccountException uae) {
				logger.error(uae.getMessage(), uae);
				return R.error("用户名或密码错误！");
			} catch (IncorrectCredentialsException ice) {
				logger.error(ice.getMessage(), ice);
				return R.error("用户名或密码错误！");
			} catch (LockedAccountException lae) {
				logger.error(lae.getMessage(), lae);
				return R.error("登陆失败");
			}
			// ... catch more exceptions here (maybe custom ones specific to your application?
			catch (AuthenticationException ae) {
				//unexpected condition?  error?
				logger.error("登录错误：", ae);
				return R.error("登陆失败!");
			}
		}
		
		return R.ok();
	}
	
	@GetMapping({ "/index" })
	String index(Model model) {
		List<TreeDto<SysMenuDto>> menus = menuService.listMenuTree(getCurrentUser().getId());
		model.addAttribute("menus", menus);
		model.addAttribute("name", getCurrentUser().getLoginName());
		logger.info(getCurrentUser().getLoginName());
		return "index_v1";
	}
	
	@GetMapping("/403")
	String error403() {
		return "/error/403";
	}

	@ResponseBody
	@GetMapping("/list")
	List<ScheduleDto> scheduleList(){
		Map<String, Object> params = new HashMap<>();
		return scheduleService.listScheduleDtoByMap(params);
	}
	
}
