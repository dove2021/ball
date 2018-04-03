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
import com.tbug.ball.manage.util.R;
import com.tbug.ball.service.OrderService;
import com.tbug.ball.service.Dto.PageDto;
import com.tbug.ball.service.Dto.Query;
import com.tbug.ball.service.Dto.order.StakeOrderExDto;

@Controller
@RequestMapping(OrderController.prefix)
public class OrderController extends BaseController {

	public static final String prefix = "operate/order";
	
	@Autowired
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
	
	@ResponseBody
	@GetMapping("/clean/{id}")
	@RequiresPermissions("operate:order:clean")
	R orderClean(@PathVariable("id") Integer id){
		try {
			if (orderService.stakeCleanBySingle(getCurrentUser().getLoginName(), id)){
				return R.ok();
			}
			return R.error();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return R.error(e.getMessage());
		}
	}
	
	@GetMapping("/detail/{id}")
	@RequiresPermissions("operate:order:detail")
	String orderDetail(@PathVariable("id") Integer id, Model model){
		StakeOrderExDto orderDto = orderService.getStakeOrderDtoById(id);
		model.addAttribute("order", orderDto);
		return prefix + "/detail";
	}
}
