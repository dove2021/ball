package com.tbug.ball.manage.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.reger.dubbo.annotation.Inject;
import com.tbug.ball.manage.controller.base.BaseController;
import com.tbug.ball.service.OrderService;
import com.tbug.ball.service.Dto.PageDto;
import com.tbug.ball.service.Dto.Query;
import com.tbug.ball.service.Dto.order.StakeOrderExDto;

@Controller
@RequestMapping(OrderController.prefix)
public class OrderController extends BaseController {

	public static final String prefix = "/operate/order";
	
	@Inject
	OrderService orderService;
	
	@GetMapping()
	String order(){
		return prefix + "/order";
	}
	
	@ResponseBody
	@GetMapping("/list")
	PageDto<StakeOrderExDto> list(@RequestParam Map<String, Object> params){
		Query query = new Query(params);
		List<StakeOrderExDto> list = orderService.listStakeOrderByMap(query);
		int total = orderService.countStakeOrderByMap(params);
		
		PageDto<StakeOrderExDto> page = new PageDto<>();
		page.setRows(list);
		page.setTotal(total);
		return page;
	}
}
