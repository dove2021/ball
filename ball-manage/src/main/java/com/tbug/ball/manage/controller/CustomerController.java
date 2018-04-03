package com.tbug.ball.manage.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tbug.ball.manage.common.annotation.Log;
import com.tbug.ball.manage.controller.base.BaseController;
import com.tbug.ball.manage.util.PageUtils;
import com.tbug.ball.manage.util.R;
import com.tbug.ball.service.AccountService;
import com.tbug.ball.service.BrokerService;
import com.tbug.ball.service.CustomerService;
import com.tbug.ball.service.MemberService;
import com.tbug.ball.service.PayService;
import com.tbug.ball.service.Dto.Query;
import com.tbug.ball.service.Dto.account.CustomerAccountDto;
import com.tbug.ball.service.Dto.pay.CustomerWithdrawDto;
import com.tbug.ball.service.Dto.pay.PaymentChannelDto;
import com.tbug.ball.service.Dto.user.BrokerDto;
import com.tbug.ball.service.Dto.user.CustomerDto;
import com.tbug.ball.service.Dto.user.CustomerExtDto;
import com.tbug.ball.service.Dto.user.MemberDto;
import com.tbug.ball.service.common.DBContants.CustomerConst;

@Controller
@RequestMapping(CustomerController.prefix)
public class CustomerController extends BaseController {

	public static final String prefix = "user/customer";
	
	@Autowired
	CustomerService customerService;
	@Autowired
	AccountService accountService;
	@Autowired
	BrokerService brokerService;
	@Autowired
	MemberService memberService;
	@Autowired
	PayService payService;
	
	@GetMapping("")
	String customer(){
		return prefix + "/customer";
	} 
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("user:customer:customer")
	PageUtils list(@RequestParam Map<String, Object> params){
		Query query = new Query(params);
		if (!StringUtils.isEmpty(query.get("signCode"))){
			BrokerDto brokerDto = brokerService.getBrokerDtoBySignCode((String) query.get("signCode"));
			query.put("levelCode", brokerDto == null ? "" : brokerDto.getLevelCode());
		}
		List<CustomerExtDto> customerDtoList = customerService.listCustomerByMap(query);
		Integer total = customerService.countCustomerByMap(query);
		
		PageUtils pageUtils = new PageUtils(customerDtoList, total);
		return pageUtils;
	}
	
	@GetMapping("/edit/{id}")
	String edit(@PathVariable("id") Integer id, Model model){
		CustomerDto customerDto = customerService.getCustomerDtoById(id);
		model.addAttribute("customerDto", customerDto);
		return prefix + "/edit";
	}
	
	@GetMapping("/detail/{id}")
	String detail(@PathVariable("id") Integer id, Model model){
		CustomerDto customerDto = customerService.getCustomerDtoById(id);
		MemberDto memberDto = memberService.getMemberDtoById(customerDto.getMemberId());
		BrokerDto brokerDto = brokerService.getBrokerDtoById(customerDto.getBrokerId());
		
		model.addAttribute("customerDto", customerDto);
		model.addAttribute("memberDto", memberDto);
		model.addAttribute("brokerDto", brokerDto);
		return prefix + "/detail";
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
	
	@GetMapping("/c/{id}")
	String c(@PathVariable("id") Integer id, Model model){
		CustomerAccountDto accountDto  = customerService.getCustomerAccountByCustomerId(id);
		model.addAttribute("accountDto", accountDto);
		
		return prefix + "/charge";
	}
	
	@Log("后台入金")
	@ResponseBody
	@GetMapping("/charge/{id}/{confirmAmount}/{channelId}/{operatorInfo}")
	R charge(@PathVariable("id") Integer id,
			 @PathVariable("confirmAmount") BigDecimal confirmAmount, 
			 @PathVariable("channelId") Integer channelId,  
			 @PathVariable("operatorInfo") String operatorInfo){
		try {
			accountService.customerCharge(id, 
										  confirmAmount, 
										  getCurrentUser().getLoginName(),
										  operatorInfo,
										  channelId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return R.error(e.getMessage());
		}
		return R.ok();
	}
	
	@GetMapping("/w/{id}")
	String w(@PathVariable("id") Integer id, Model model){
		CustomerWithdrawDto withdrawDto = accountService.getCustomerWithdrawDtoById(id);
		CustomerAccountDto accountDto  = customerService.getCustomerAccountByCustomerId(id);
		
		List<PaymentChannelDto> dtoList = payService.listPaymentChannelAll();
		if (!CollectionUtils.isEmpty(dtoList)){
			model.addAttribute("channelList", dtoList);
		}
		
		model.addAttribute("withdrawDto", withdrawDto);
		model.addAttribute("accountDto", accountDto);
		
		return prefix + "/withdraw";
	}
	
	@Log("后台出金")
	@ResponseBody
	@GetMapping("/withdraw/{id}/{amount}/{fee}/{channelId}")
	R withdraw(@PathVariable("id") Integer id,
			   @PathVariable("amount") BigDecimal amount,
			   @PathVariable("fee") BigDecimal fee,
			   @PathVariable("channelId") int channelId){
		try {
			CustomerAccountDto accountDto  = customerService.getCustomerAccountByCustomerId(id);
			if (accountDto.getBalance() == null){
				return R.error("用户不存在");
			}
			if (accountDto.getBalance().compareTo(amount.add(fee)) < 0){
				return R.error("余额不足");
			}
			accountService.customerWithdraw(channelId, id, amount, fee, getCurrentUser().getLoginName());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return R.error(e.getMessage());
		}
		return R.ok();
	}
	
}
