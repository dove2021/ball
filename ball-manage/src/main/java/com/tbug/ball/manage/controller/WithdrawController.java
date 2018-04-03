package com.tbug.ball.manage.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.tbug.ball.service.Dto.PageDto;
import com.tbug.ball.service.Dto.Query;
import com.tbug.ball.service.Dto.pay.WithdrawFileDto;
import com.tbug.ball.service.Dto.pay.WithdrawRecordDto;

@Controller
@RequestMapping(WithdrawController.prefix)
public class WithdrawController extends BaseController {

	public static final String prefix = "operate/withdraw";
	
	@Autowired
	PayService payService;
	
	@GetMapping()
	String charge(){
		return prefix + "/withdraw";
	}
	
	@ResponseBody
	@GetMapping("/list")
	PageDto<WithdrawRecordDto> list(@RequestParam Map<String, Object> params){
		Query query = new Query(params);
		List<WithdrawRecordDto> dtoList = payService.listWithdrawRecordByMap(query);
		int total = payService.countWithdrawRecordByMap(params);
		
		PageDto<WithdrawRecordDto> page = new PageDto<>();
		page.setRows(dtoList);
		page.setTotal(total);
		return page;
	}
	
	@GetMapping("/detail/{id}")
	String detail(@PathVariable("id") Integer id, Model model){
		
		WithdrawRecordDto dto = payService.getWithdrawRecordById(id);
		model.addAttribute("withdrawRecordDto", dto);
		
		return prefix + "/detail";
	}
	
	@GetMapping("/check/{id}")
	String check(@PathVariable("id") Integer id, Model model){
		WithdrawRecordDto dto = payService.getWithdrawRecordById(id);
		
		WithdrawFileDto withdrawFileDto = payService.getWithdrawFile(dto.getCustomerId(), dto.getChannelId());
		withdrawFileDto.setImgContent(null);
		
		model.addAttribute("withdraw", dto);
		model.addAttribute("fileDto", withdrawFileDto);
		
		return prefix + "/check";
	}
	
	@GetMapping(value = "/loadPic/{customerId}/{channelId}")
	void loadCustInfoPic(@PathVariable("customerId") Integer customerId,
						 @PathVariable("channelId") Integer channelId, HttpServletResponse response) {
		OutputStream output = null;
		try{
			WithdrawFileDto withdrawFileDto = payService.getWithdrawFile(customerId, channelId);
			if (null == withdrawFileDto){
				return;
			}
			
			response.setContentType("image/jpeg;charset=GB2312");
			output = response.getOutputStream();
			output.write(withdrawFileDto.getImgContent());
			output.flush();
			output.close();
		} catch(Exception e){
			logger.error("显示照片异常:",e.getMessage(), e);
		} finally {
			if (output != null){
				try {
					output.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
				output = null;
			}
		}
	}
	
	@ResponseBody
	@GetMapping("/pass/{id}")
	R pass(@PathVariable("id") Integer id, HttpServletRequest req){
		
		String info  = req.getParameter("info");
		
		payService.customerWithdrawPass(id, getCurrentUser().getLoginName(), info);
		
		return R.ok();
	}
	
	@ResponseBody
	@GetMapping("/back/{id}")
	R back(@PathVariable("id") Integer id, HttpServletRequest req){
		
		String info  = req.getParameter("info");
		
		try {
			payService.customerWIthdrawBack(id, getCurrentUser().getLoginName(), info);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return R.error(e.getMessage());
		}
		
		return R.ok();
	}
	
}
