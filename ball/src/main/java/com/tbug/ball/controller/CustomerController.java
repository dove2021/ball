package com.tbug.ball.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.tbug.ball.common.AjaxResult;
import com.tbug.ball.common.annotation.Log;
import com.tbug.ball.controller.base.BaseController;
import com.tbug.ball.service.BrokerService;
import com.tbug.ball.service.CustomerService;
import com.tbug.ball.service.MemberService;
import com.tbug.ball.service.Dto.user.BrokerDto;
import com.tbug.ball.service.Dto.user.CustomerDto;
import com.tbug.ball.service.Dto.user.MemberDto;
import com.tbug.ball.service.common.DBContants.MemberConst;
import com.tbug.ball.utils.CookieUtils;
import com.tbug.ball.utils.IPUtils;
import com.tbug.ball.utils.ValidatorUtils;

@Controller
@RequestMapping(CustomerController.prefix)
public class CustomerController extends BaseController {

	public static final String prefix = "/customer";
	
	@Autowired
	CustomerService customerService;
	@Autowired
	MemberService memberService;
	@Autowired
	BrokerService brokerService;
	
	@Log("客户注册进入")
	@GetMapping("/register/{signCode}")
	String register(@PathVariable("signCode") String signCode, Model model){
		if (StringUtils.isEmpty(signCode) || "0".equals(signCode) || null == brokerService.getBrokerDtoBySignCode(signCode)){
			Map<String, Object>  params = new HashMap<>();
			params.put("status", MemberConst.STATUS_NORMAL);
			List<MemberDto> memberList = memberService.listMemberDtoByMap(params);
			model.addAttribute("memberList", memberList);
			model.addAttribute("url_signCode", "0"); 
			return prefix + "/register";
		}
		BrokerDto brokerDto = brokerService.getBrokerDtoBySignCode(signCode);
		MemberDto memberDto = memberService.getMemberDtoById(brokerDto.getMemberId());
		
		model.addAttribute("brokerDto", brokerDto);
		model.addAttribute("memberDto", memberDto);
		model.addAttribute("url_signCode", signCode);
		return prefix + "/register";
	}

	@Log("客户注册提交")
	@ResponseBody
	@PostMapping("/register")
	AjaxResult customerRegister(HttpServletRequest request, HttpServletResponse response){
		String url_signCode = request.getParameter("url_signCode");
		String signCode = request.getParameter("signCode");
		String memberCode = request.getParameter("memberCode");
		String loginName = request.getParameter("loginName");
		String password = request.getParameter("password");
		
		if (StringUtils.isEmpty(url_signCode)){
			return AjaxResult.error("数据异常");
		}
		if (StringUtils.isEmpty(signCode)){
			return AjaxResult.error("邀请码不能为空");
		}
		if (StringUtils.isEmpty(memberCode)){
			return AjaxResult.error("会员单位不能为空");
		}
		if (StringUtils.isEmpty(loginName)){
			return AjaxResult.error("用户名不能为空");
		}
		if (loginName.length() < 6 || loginName.length() > 32){
			return AjaxResult.error("用户名为6~32位");
		}
		if (StringUtils.isEmpty(password)){
			return AjaxResult.error("密码不能为空");
		}
		if (password.length() < 6 || password.length() > 32){
			return AjaxResult.error("密码为6~32位");
		}
		try {
			customerService.createCustomer(loginName, password, memberCode, signCode, IPUtils.getIpAddr(request));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return AjaxResult.error(e.getMessage());
		}
		// 加入cookie
		CookieUtils.addCookie(response, "userName", loginName, 14*24*3600);
		
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
}
