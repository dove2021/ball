package com.tbug.ball.manage.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tbug.ball.manage.controller.base.BaseController;
import com.tbug.ball.service.OrderService;
import com.tbug.ball.service.ScheduleService;
import com.tbug.ball.service.Dto.PageDto;
import com.tbug.ball.service.Dto.Query;
import com.tbug.ball.service.Dto.order.OrderCleanDto;
import com.tbug.ball.service.Dto.order.StakeOrderHDto;
import com.tbug.ball.service.Dto.schedule.ScheduleDto;

@Controller
@RequestMapping(OrderHController.prefix)
public class OrderHController extends BaseController {

	public static final String prefix = "operate/orderH";
	
	@Autowired
	OrderService orderService;
	@Autowired
	ScheduleService scheduleService;
	
	
	@GetMapping("/orderH")
	@RequiresPermissions("operate:orderH:orderH")
	String orderH(){
		return prefix + "/orderH";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("operate:orderH:detail")
	PageDto<StakeOrderHDto> list(@RequestParam Map<String, Object> params){
		Query query = new Query(params);
		List<StakeOrderHDto> dtoList = orderService.listStakeOrderHDtoByMap(query);
		int total = orderService.countStakeOrderHDtoByMap(params);
		
		PageDto<StakeOrderHDto> page = new PageDto<>();
		page.setRows(dtoList);
		page.setTotal(total);
		return page;
	}
	
	@GetMapping("/detail/{id}")
	@RequiresPermissions("operate:orderH:orderH")
	String detail(@PathVariable("id") Integer id, Model model){
		StakeOrderHDto dto = orderService.getStakeOrderHDtoById(id);
		OrderCleanDto clean = orderService.getOrderCleanById(dto.getId());
		ScheduleDto scheduleDto = scheduleService.getScheduleDtoById(dto.getScheduleId());
		model.addAttribute("orderH", dto);
		model.addAttribute("scheduleDto", scheduleDto);
		model.addAttribute("clean", clean);
		return prefix + "/detail";
	}
	
}
