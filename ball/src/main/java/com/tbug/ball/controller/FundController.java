package com.tbug.ball.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tbug.ball.controller.base.BaseController;
import com.tbug.ball.service.CustomerService;
import com.tbug.ball.service.Dto.PageDto;
import com.tbug.ball.service.Dto.Query;
import com.tbug.ball.service.Dto.account.CustomerAccountDto;
import com.tbug.ball.service.Dto.flow.CustomerAccountFlowDto;

@Controller
public class FundController extends BaseController {

	@Autowired
	CustomerService customerService;
	
	@GetMapping("/fund")
	public String fund(){
		return "fund/fund";
	}
	
	@ResponseBody
	@GetMapping("/fund/list")
	PageDto<CustomerAccountFlowDto> fundList(@RequestParam Map<String, Object> params){
		CustomerAccountDto account = customerService.getCustomerAccountByCustomerId(getCurrUser().getId());
		Query query = new Query(params);
		query.put("accountId", account.getId());
		List<CustomerAccountFlowDto> list = customerService.listCustomerAccountFlowByMap(query);
		int total = customerService.countCustomerAccountFlowByMap(params);
		Map<String, Object> sum = customerService.sumCustomerAccountFlowByMap(params);
		
		PageDto<CustomerAccountFlowDto> page = new PageDto<>();
		page.setRows(list);
		page.setTotal(total);
		page.setParams(sum);
		
		return page;
	}
	
	@GetMapping("/fund/detail/{id}")
	String detail(@PathVariable("id") Integer id, Model model){
		CustomerAccountDto account = customerService.getCustomerAccountByCustomerId(getCurrUser().getId());
		CustomerAccountFlowDto dto = customerService.getCustomerAccountFlowDtoById(id);
		if (!dto.getAccountId().equals(account.getId())){
			return "error/500";
		}
		model.addAttribute("flow", dto);
		return "fund/detail";
	}
}
