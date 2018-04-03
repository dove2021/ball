package com.tbug.ball.manage.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.tbug.ball.service.PayService;
import com.tbug.ball.service.ServiceException;
import com.tbug.ball.service.Dto.PageDto;
import com.tbug.ball.service.Dto.Query;
import com.tbug.ball.service.Dto.pay.ChargeRecordDto;

@Controller
@RequestMapping(ChargeController.prefix)
public class ChargeController extends BaseController {

	public static final String prefix = "operate/charge";
	
	@Autowired
	PayService payService;
	
	@GetMapping()
	String charge(){
		return prefix + "/charge";
	}
	
	@ResponseBody
	@GetMapping("/list")
	PageDto<ChargeRecordDto> list(@RequestParam Map<String, Object> params){
		Query query = new Query(params);
		List<ChargeRecordDto> dtoList = payService.listChargeRecordByMap(query);
		int total = payService.countChargeRecordByMap(params);
		
		PageDto<ChargeRecordDto> page = new PageDto<>();
		page.setRows(dtoList);
		page.setTotal(total);
		return page;
	}

	@GetMapping("/detail/{id}")
	String detail(@PathVariable("id") Integer id, Model model){
		
		ChargeRecordDto dto = payService.getChargeRecordById(id);
		model.addAttribute("charge", dto);
		
		return prefix + "/detail";
	}
	
	@GetMapping("/check/{id}")
	String check(@PathVariable("id") Integer id, Model model){
		ChargeRecordDto dto = payService.getChargeRecordById(id);
		model.addAttribute("charge", dto);
		return prefix + "/check";
	}
	
	@ResponseBody
	@GetMapping("/pass/{id}/{confirmAmount}")
	R pass(@PathVariable("id") Integer id, @PathVariable("confirmAmount") BigDecimal confirmAmount,HttpServletRequest req){
		
		String info  = req.getParameter("info");
		
		try {
			payService.customerChargePass(id, confirmAmount, getCurrentUser().getLoginName(), info);
		} catch (ServiceException e) {
			logger.error(e.getMessage(), e);
			return R.error(e.getMessage());
		}
		
		return R.ok();
	}
	
	@ResponseBody
	@GetMapping("/back/{id}")
	R back(@PathVariable("id") Integer id, HttpServletRequest req){
		
		String info  = req.getParameter("info");
		
		try {
			payService.customerChargeBack(id, getCurrentUser().getLoginName(), info);;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return R.error(e.getMessage());
		}
		
		return R.ok();
	}
	
}
