package com.tbug.ball.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.tbug.ball.controller.base.BaseController;
import com.tbug.ball.service.CustomerService;
import com.tbug.ball.service.OrderService;
import com.tbug.ball.service.ScheduleService;
import com.tbug.ball.service.Dto.account.CustomerAccountDto;
import com.tbug.ball.service.Dto.order.OrderCleanExDto;
import com.tbug.ball.service.Dto.order.StakeOrderExDto;
import com.tbug.ball.service.Dto.schedule.ScheduleDto;
import com.tbug.ball.service.Dto.schedule.StakeDto;
import com.tbug.ball.service.Dto.user.CustomerDto;
import com.tbug.ball.service.common.DBContants.OrderCleanConst;

@Controller
public class IndexController extends BaseController{

	@Autowired
	ScheduleService scheduleService;
	@Autowired
	CustomerService customerService;
	@Autowired
	OrderService orderService;
	
	@GetMapping("/index")
	String index(Model model){
		if (!checkLogin()){
			return "index";
		}
		// 在售赛程
		CustomerDto customerDto = getCurrUser();
		List<ScheduleDto> dtoList = scheduleService.listScheduleInfoDtoByNormal(customerDto.getMemberId());
		List<StakeDto> stakeDtoList = scheduleService.listStake();
		CustomerAccountDto customerAccountDto = customerService.getCustomerAccountByCustomerId(customerDto.getId());
		model.addAttribute("scheduleList", dtoList);
		model.addAttribute("scheduleNum", dtoList.size());
		model.addAttribute("stakeDtoList", stakeDtoList);
		model.addAttribute("balance", customerAccountDto.getBalance());
		model.addAttribute("customerCode", customerDto.getCustomerCode());
		
		// 持仓
		Map<String, Object> params = new HashMap<>();
		params.put("customerId", customerDto.getId());
		List<StakeOrderExDto> stakeOrderExlist = orderService.listStakeOrderByMap(params);
		if (stakeOrderExlist.size() > 0){
			model.addAttribute("stakeOrderExlist", stakeOrderExlist);
			model.addAttribute("orderNum", stakeOrderExlist.size());
		}
		
		// 兑奖
		List<OrderCleanExDto> cleanList = orderService.listOrderCleanByCustomerId(customerDto.getId(), OrderCleanConst.IS_DRAW_N);
		if (cleanList.size() > 0){
			model.addAttribute("cleanList", cleanList);
			model.addAttribute("cleanNum", cleanList.size());
		}
		
		return "index";
	}
}
