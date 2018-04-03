package com.tbug.ball.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.code.kaptcha.Constants;
import com.tbug.ball.common.AjaxResult;
import com.tbug.ball.common.annotation.Log;
import com.tbug.ball.controller.base.BaseController;
import com.tbug.ball.service.BrokerService;
import com.tbug.ball.service.CustomerService;
import com.tbug.ball.service.MemberService;
import com.tbug.ball.service.Dto.user.BrokerDto;
import com.tbug.ball.service.Dto.user.CustomerDto;
import com.tbug.ball.service.Dto.user.MemberDto;
import com.tbug.ball.utils.AESUtil;
import com.tbug.ball.utils.CookieUtils;
import com.tbug.ball.utils.IPUtils;
import com.tbug.ball.utils.ValidatorUtils;

@Controller
@RequestMapping(CustomerController.prefix)
public class CustomerController extends BaseController {

	public static final String prefix = "customer";
	
	@Autowired
	CustomerService customerService;
	@Autowired
	MemberService memberService;
	@Autowired
	BrokerService brokerService;
	
	@Log("客户注册进入")
	@GetMapping("/register/{signCode}")
	String register(@PathVariable("signCode") String signCode, Model model){
		BrokerDto brokerDto = brokerService.getBrokerDtoBySignCode(signCode);
		
		if (StringUtils.isEmpty(signCode) || null == brokerDto){
			return "error/500";
		}
		
		MemberDto memberDto = memberService.getMemberDtoById(brokerDto.getMemberId());
		model.addAttribute("brokerDto", brokerDto);
		model.addAttribute("memberDto", memberDto);
		return prefix + "/register";
	}

	@Log("客户注册提交")
	@ResponseBody
	@PostMapping("/register")
	AjaxResult customerRegister(HttpServletRequest request, HttpServletResponse response){
		String signCode = request.getParameter("signCode");
		String loginName = request.getParameter("loginName");
		String password = request.getParameter("password");
		String kaptcha = request.getParameter("kaptcha");
		
		if (StringUtils.isEmpty(signCode)){
			return AjaxResult.error("邀请码不能为空");
		}
		if (StringUtils.isEmpty(loginName)){
			return AjaxResult.error("用户名不能为空");
		}
		if (loginName.length() < 2 || loginName.length() > 32){
			return AjaxResult.error("用户名至少2位");
		}
		if (StringUtils.isEmpty(password)){
			return AjaxResult.error("密码不能为空");
		}
		if (password.length() < 6 || password.length() > 32){
			return AjaxResult.error("密码为6~32位");
		}
		if (StringUtils.isEmpty(kaptcha)){
			return AjaxResult.error("验证码不能为空");
		}
		
		String code = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
		long time = (long) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_CONFIG_DATE);
		if (!kaptcha.equals(code)){
			request.removeAttribute(Constants.KAPTCHA_SESSION_KEY);
			request.removeAttribute(Constants.KAPTCHA_SESSION_CONFIG_DATE);
			return AjaxResult.error("验证码错误");
		}
		if ((new Date().getTime() - time) > 10 * 60 * 1000){
			request.removeAttribute(Constants.KAPTCHA_SESSION_KEY);
			request.removeAttribute(Constants.KAPTCHA_SESSION_CONFIG_DATE);
			return AjaxResult.error("验证码验证超时");
		}
		request.removeAttribute(Constants.KAPTCHA_SESSION_KEY);
		request.removeAttribute(Constants.KAPTCHA_SESSION_CONFIG_DATE);
		
		try {
			customerService.createCustomer(loginName, password, "", signCode, IPUtils.getIpAddr(request));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return AjaxResult.error(e.getMessage());
		}
		
		// 加入cookie
		CookieUtils.addCookie(response, "userName", AESUtil.encryptStr(loginName), 14*24*3600);
		logger.info("创建用户成功, 用户名：{}", loginName);
		
		return AjaxResult.ok();
	}
	
	@GetMapping("/set")
	String set(Model model){
		CustomerDto curr = getCurrUser();
		CustomerDto customerDto = customerService.getCustomerDtoById(curr.getId());
		model.addAttribute("customer", customerDto);
		return prefix + "/customer";
	}
	
	@GetMapping("/rePhone")
	String rePhone(Model model){
		CustomerDto curr = getCurrUser();
		CustomerDto customerDto = customerService.getCustomerDtoById(curr.getId());
		model.addAttribute("customer", customerDto);
		return prefix + "/rePhone";
	}
	
	@ResponseBody
	@PostMapping("/rePhone")
	AjaxResult rePhoneUpd(String phoneNo){
		if (StringUtils.isEmpty(phoneNo)){
			return AjaxResult.error("手机号不能为空");
		}
		if (!ValidatorUtils.isMobile(phoneNo)){
			return AjaxResult.error("手机号码有误");
		}
		try {
			customerService.updCustomerPhoneNo(getCurrUser().getId(), phoneNo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return AjaxResult.error(e.getMessage());
		}
		return AjaxResult.ok();
	}
	
	@GetMapping("/rePwd")
	String rePwd(Model model){
		CustomerDto curr = getCurrUser();
		CustomerDto customerDto = customerService.getCustomerDtoById(curr.getId());
		model.addAttribute("customer", customerDto);
		
		return prefix + "/rePwd";
	}
	
	@ResponseBody
	@PostMapping("/rePwd")
	AjaxResult ajaxRePwd(HttpServletRequest request){
		String password = request.getParameter("password");
		String password1 = request.getParameter("password1");
		if (StringUtils.isEmpty(password)){
			return AjaxResult.error("原密码不能为空");
		}
		if (StringUtils.isEmpty(password1)){
			return AjaxResult.error("新密码不能为空");
		}
		if (password1.length() < 6 || password1.length() > 32){
			return AjaxResult.error("密码长度为6~32位");
		}
		try {
			customerService.customerRePwd(getCurrUser().getId(), password, password1);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return AjaxResult.error(e.getMessage());
		}
		
		return AjaxResult.ok();
	}
	
	@GetMapping("tell")
	String tell(){
		return prefix + "/tell";
	}
}
