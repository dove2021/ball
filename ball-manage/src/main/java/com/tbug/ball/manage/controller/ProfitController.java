package com.tbug.ball.manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tbug.ball.manage.controller.base.BaseController;
import com.tbug.ball.manage.util.R;
import com.tbug.ball.service.BrokerService;
import com.tbug.ball.service.Dto.order.BrokerProfitDto;

@Controller
@RequestMapping(ProfitController.prefix)
public class ProfitController extends BaseController {

	public static final String prefix = "/operate/profit";
	
	@Autowired
	BrokerService brokerService;
	
	@GetMapping()
	String profit(){
		return prefix + "/profit";
	}
	
	@ResponseBody
	@GetMapping("/list")
	List<BrokerProfitDto> list(){
		List<BrokerProfitDto> profitList = brokerService.listProfitAll();
		return profitList;
	}
	
	@GetMapping("/edit/{id}")
	String edit(@PathVariable("id") Integer id, Model model){
		BrokerProfitDto brokerProfitDto = brokerService.getBrokerProfitDtoById(id);
		model.addAttribute("profitDto", brokerProfitDto);
		return prefix + "/edit";
	}
	
	@ResponseBody
	@PostMapping("/save")
	R saveAjax(BrokerProfitDto brokerProfitDto){
		if (brokerService.updProfit(brokerProfitDto)){
			return R.ok();
		}
		return R.ok();
	}
	
}
