package com.tbug.ball.manage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tbug.ball.manage.controller.base.BaseController;
import com.tbug.ball.manage.util.R;
import com.tbug.ball.service.RoleService;
import com.tbug.ball.service.ServiceException;
import com.tbug.ball.service.TradeService;
import com.tbug.ball.service.Dto.system.SysRoleDto;
import com.tbug.ball.service.Dto.user.TradeUserDto;
import com.tbug.ball.service.common.DBContants.TradeUserConst;

@Controller
@RequestMapping(TradeController.prefix)
public class TradeController extends BaseController{
	public static final String prefix = "user/trade";
	
	@Autowired TradeService tradeService;
	
	@Autowired RoleService roleService;
	
	@GetMapping()
	String trade(){
		return prefix + "/trade";
	}
	
	@GetMapping("/list")
	@ResponseBody
	List<TradeUserDto> list(HttpServletRequest request){
		
		String loginName = request.getParameter("loginName");
		
		Map<String, Object> params = new HashMap<>();
		if (!StringUtils.isEmpty(loginName)){
			params.put("loginName", loginName);
		}
		List<TradeUserDto> tradeUserDtoList = tradeService.listTradeUserDto(params);
		
		return tradeUserDtoList;
	}

	@GetMapping("/add")
	String add(Model model){
		List<SysRoleDto> roleList = roleService.list();
		model.addAttribute("roles", roleList);
		return prefix + "/add";
	}
	
	@ResponseBody
	@PostMapping("/save")
	R ajaxSave(HttpServletRequest request){
		
		String loginName = request.getParameter("loginName");
		String password = request.getParameter("password1");
		String roles = request.getParameter("roleIds");
		
		try {
			tradeService.saveTradeUserDto("admin", loginName, password, roles.split(","));
		} catch (ServiceException e) {
			logger.error(e.getMessage(), e);
			return R.error(e.getMessage());
		}
		
		return R.ok();
	}
	
	@GetMapping("/edit/{id}")
	String edit(@PathVariable("id") Integer id, Model model){
		TradeUserDto tradeUserDto = tradeService.getTradeUserDtoById(id);
		model.addAttribute("tradeUserDto", tradeUserDto);
		
		return prefix + "/edit";
	}
	
	@ResponseBody
	@PostMapping("/update")
	R update(HttpServletRequest request){
		String id = request.getParameter("id");
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");
		if (StringUtils.isEmpty(id)){
			return R.error("用户id不能为空");
		}
		if (StringUtils.isEmpty(password1)){
			return R.error("新密码不能为空");
		}
		if (StringUtils.isEmpty(password2)){
			return R.error("确认密码不能为空");
		}
		if (!password1.equals(password2)){
			return R.error("俩次输入的密码不同");
		}
		
		try {
			tradeService.updTradeUserPwd(Integer.valueOf(id), "", password1);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return R.error(e.getMessage());
		}
		
		return R.ok();
	}
	
	@GetMapping("/reRole/{id}")
	String role(@PathVariable("id") Integer id, Model model){
		TradeUserDto tradeUserDto = tradeService.getTradeUserDtoById(id);
		model.addAttribute("tradeUserDto", tradeUserDto);
		
		List<SysRoleDto> roles = roleService.list(id);
		model.addAttribute("roles", roles);
		
		return prefix + "/reRole";
	}
	
	@ResponseBody
	@PostMapping("/reRole/update")
	R reRoleAjax(HttpServletRequest request){
		String id = request.getParameter("id");
		String roles = request.getParameter("roleIds");
		try {
			String[] rs = roles.split(",");
			Integer[] roleIds = new Integer[rs.length];
			for (int i = 0; i < rs.length; i++){
				roleIds[i] = Integer.valueOf(rs[i]);
			}
			roleService.reRole(Integer.valueOf(id), roleIds);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return R.error(e.getMessage());
		}
		return R.ok();
	}
	
	@ResponseBody
	@GetMapping("/freeze/{id}")
	R freeze(@PathVariable("id") Integer id){
		if (id == null){
			return R.error("用户冻结失败");
		}
		TradeUserDto tradeUserDto = new TradeUserDto();
		tradeUserDto.setId(id);
		tradeUserDto.setStatus(TradeUserConst.STATUS_FREEZE);
		
		try {
			if (tradeService.updTradeUserDto(tradeUserDto)){
				R.error("冻结失败");
			}
		} catch (ServiceException e) {
			logger.error(e.getMessage(), e);
			R.error(e.getMessage());
		}
		
		return R.ok();
	}
	
}
