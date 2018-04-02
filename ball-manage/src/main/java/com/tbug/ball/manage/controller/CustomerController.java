package com.tbug.ball.manage.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.reger.dubbo.annotation.Inject;
import com.tbug.ball.manage.common.annotation.Log;
import com.tbug.ball.manage.controller.base.BaseController;
import com.tbug.ball.manage.util.PageUtils;
import com.tbug.ball.manage.util.R;
import com.tbug.ball.service.AccountService;
import com.tbug.ball.service.CustomerService;
import com.tbug.ball.service.Dto.user.CustomerDto;
import com.tbug.ball.service.common.DBContants.CustomerConst;

@Controller
@RequestMapping(CustomerController.prefix)
public class CustomerController extends BaseController {

	public static final String prefix = "/user/customer";
	
	@Inject
	CustomerService customerService;
	
	@Inject
	AccountService sccountService;
	
	@GetMapping("")
	String customer(){
		return prefix + "/customer";
	} 
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("user:customer:customer")
	PageUtils list(HttpServletRequest request){
		String offset = request.getParameter("offset");
		String limit = request.getParameter("limit");
		String phoneNo = request.getParameter("phoneNo");
		Map<String, Object> params = new HashMap<>();
		if (!StringUtils.isEmpty(offset)){
			params.put("offset", offset);
		}
		if (!StringUtils.isEmpty(limit)){
			params.put("limit", limit);
		}
		if (!StringUtils.isEmpty(phoneNo)){
			params.put("phoneNo", phoneNo);
		}
		List<CustomerDto> customerDtoList = customerService.listCustomerByMap(params);
		Integer total = customerService.countCustomerByMap(params);
		
		PageUtils pageUtils = new PageUtils(customerDtoList, total);
		return pageUtils;
	}
	
	@GetMapping("/edit/{id}")
	String edit(@PathVariable("id") Integer id, Model model){
		CustomerDto customerDto = customerService.getCustomerDtoById(id);
		model.addAttribute("customerDto", customerDto);
		return prefix + "/edit";
	}
	
	@ResponseBody
	@PostMapping("/update")
	R update(HttpServletRequest request){
		String id = request.getParameter("id");
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");
		if (StringUtils.isEmpty(id)){
			return R.error("会员id不存在");
		}
		if (StringUtils.isEmpty(password1)){
			return R.error("先密码不能为空");
		}
		if (StringUtils.isEmpty(id)){
			return R.error("确认密码不能为空");
		}
		if (!password1.equals(password2)){
			return R.error("新密码与确认密码不一致");
		}
		try {
			if (customerService.customerRePwd(Integer.valueOf(id), "", password1)){
				return R.ok();
			}
			return R.error("更新失败");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return R.error(e.getMessage());
		}
	}
	
	@ResponseBody
	@GetMapping("/freeze/{id}")
	R freeze(@PathVariable("id") Integer id){
		if (null == id){
			return R.error("数据不能为空");
		}
		try {
			if (customerService.updCustomerStatus(id, CustomerConst.STATUS_FREEZE)){
				return R.ok();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return R.error(e.getMessage());
		}
		
		return R.ok();
	}
	
	@ResponseBody
	@PostMapping("/batch/freeze")
	R batchFreeze(@RequestParam("ids[]") Integer[] ids){
		if (null == ids || ids.length <= 0){
			return R.error("数据不能为空");
		}
		
		int num = 0;
		for (Integer id : ids){
			try {
				if (customerService.updCustomerStatus(id, CustomerConst.STATUS_FREEZE)){
					num ++;	
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}	
		}
		return R.ok("已冻结 " + num + " 个客户");
	}
	
	@ResponseBody
	@PostMapping("/batch/normal")
	R batchNormal(@RequestParam("ids[]") Integer[] ids){
		if (null == ids || ids.length <= 0){
			return R.error("数据不能为空");
		}
		int num = 0;
		for (Integer id : ids){
			try {
				if (customerService.updCustomerStatus(id, CustomerConst.STATUS_NORMAL)){
					num ++;
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return R.ok("已恢复" + num + " 个客户");
	}
	
	@Log("手工入金")
	@ResponseBody
	@GetMapping("/charge/{id}/{amount}")
	R charge(@PathVariable("id") Integer id,@PathVariable("amount") BigDecimal amount){
		
		try {
			sccountService.customerCharge(id, amount);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return R.error(e.getMessage());
		}
		return R.ok();
	}
	
	@Log("手工出金")
	@ResponseBody
	@GetMapping("/withdraw/{id}/{amount}")
	R withdraw(@PathVariable("id") Integer id,@PathVariable("amount") BigDecimal amount){
		try {
			sccountService.customerWithdraw(id, amount);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return R.error(e.getMessage());
		}
		return R.ok();
	}
	
}
