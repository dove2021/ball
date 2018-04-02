package com.tbug.ball.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tbug.ball.common.AjaxResult;
import com.tbug.ball.common.annotation.Log;
import com.tbug.ball.controller.base.BaseController;
import com.tbug.ball.service.OrderService;
import com.tbug.ball.service.ScheduleService;
import com.tbug.ball.service.Dto.PageDto;
import com.tbug.ball.service.Dto.Query;
import com.tbug.ball.service.Dto.order.OrderCleanDto;
import com.tbug.ball.service.Dto.order.StakeOrderDto;
import com.tbug.ball.service.Dto.order.StakeOrderExDto;
import com.tbug.ball.service.Dto.order.StakeOrderHDto;
import com.tbug.ball.service.Dto.schedule.PreOrderDto;
import com.tbug.ball.service.Dto.schedule.ScheduleDto;
import com.tbug.ball.service.Dto.user.CustomerDto;

@Controller
public class OrderController extends BaseController {

	@Autowired
	OrderService orderService;
	@Autowired
	ScheduleService scheduleService;
	
	@Log("下注")
	@ResponseBody
	@PostMapping("/order/stake")
	AjaxResult orderStake(PreOrderDto preOrderDto){
		if (null == preOrderDto){
			return AjaxResult.error("入参不能为空");
		}
		if (null == preOrderDto.getScheduleId()){
			return AjaxResult.error("请选择 赛程");
		}
		if (StringUtils.isEmpty(preOrderDto.getStakeType())){
			return AjaxResult.error("请选择 下注方向");
		}
		if (StringUtils.isEmpty(preOrderDto.getStakeCode())){
			return AjaxResult.error("请选择 下注品种");
		}
		if (null == preOrderDto.getOrderNum() || preOrderDto.getOrderNum().intValue() <= 0){
			return AjaxResult.error("请选择 下注手数");
		}
		if (BigDecimal.ZERO.compareTo(preOrderDto.getTotalFee()) >= 0){
			return AjaxResult.error("手续费不能为空");
		}
		if (BigDecimal.ZERO.compareTo(preOrderDto.getStakeAmount()) >= 0){
			return AjaxResult.error("下注金额 不能为空");
		}
		if (BigDecimal.ZERO.compareTo(preOrderDto.getTotalAmount()) >= 0){
			return AjaxResult.error("总费用不能为空");
		}
		if (BigDecimal.ZERO.compareTo(preOrderDto.getOddsScale()) >= 0){
			return AjaxResult.error("赔率不能为空");
		}
		
		CustomerDto currUser = getCurrUser();
		preOrderDto.setCustomerId(currUser.getId());
		
		try {
			if (orderService.stakeOpen(preOrderDto)){
				return AjaxResult.ok();
			}
			return AjaxResult.error("下注失败");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return AjaxResult.error(e.getMessage());
		}
	}
	
	@ResponseBody
	@GetMapping("/order/detail/{id}")
	StakeOrderDto orderDetail(@PathVariable("id") Integer id){
		StakeOrderExDto dto = orderService.getStakeOrderDtoById(id);
		if (!dto.getCustomerId().equals(getCurrUser().getId())){
			return null;
		}
		return dto;
	}
	
	@Log("兑换奖励")
	@ResponseBody
	@GetMapping("/order/draw/{id}")
	AjaxResult orderDraw(@PathVariable("id") Integer id){
		try {
			OrderCleanDto dto = orderService.getOrderCleanById(id);
			if (null == dto.getCustomerId()){
				return AjaxResult.error("兑奖记录不存在");
			}
			if (!getCurrUser().getId().equals(dto.getCustomerId())){
				return AjaxResult.error("兑奖必须为本人");
			}
			if (orderService.stakeToCash(id)){
				return AjaxResult.ok();
			}
			return AjaxResult.error();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return AjaxResult.error("兑换失败: " + e.getMessage());
		}
	}
	
	@GetMapping("/orderH")
	String orderH(){
		return "orderH/orderH";
	}
	
	@ResponseBody
	@GetMapping("/orderH/list")
	PageDto<StakeOrderHDto> orderHList(@RequestParam Map<String, Object> params){
		Query query = new Query(params);
		query.put("customerId", getCurrUser().getId());
		List<StakeOrderHDto> list = orderService.listStakeOrderHDtoByMap(query);
		Integer total = orderService.countStakeOrderHDtoByMap(params);
		
		PageDto<StakeOrderHDto> page = new PageDto<>();
		page.setRows(list);
		page.setTotal(total);
		return page;
	}
	
	@GetMapping("/orderH/detail/{id}")
	String orderHDetail(@PathVariable("id") Integer id, Model model){
		StakeOrderHDto dto = orderService.getStakeOrderHDtoById(id);
		if (!dto.getCustomerId().equals(getCurrUser().getId())){
			return "error/500";
		}
		ScheduleDto scheduleDto = scheduleService.getScheduleDtoById(dto.getScheduleId());
		OrderCleanDto orderCleanDto = orderService.getOrderCleanById(dto.getId());
		
		model.addAttribute("orderH", dto);
		model.addAttribute("scheduleDto", scheduleDto);
		if (null == orderCleanDto.getCleanAmount()){
			model.addAttribute("cleanAmount", BigDecimal.ZERO.setScale(2));
		} else {
			model.addAttribute("cleanAmount", orderCleanDto.getCleanAmount());
		}
		return "orderH/detail";
	}
}
