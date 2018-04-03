package com.tbug.ball.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tbug.ball.common.AjaxResult;
import com.tbug.ball.common.annotation.Log;
import com.tbug.ball.controller.base.BaseController;
import com.tbug.ball.service.AccountService;
import com.tbug.ball.service.PayService;
import com.tbug.ball.service.Dto.pay.CashFileDto;

@Controller
public class ChargeController extends BaseController {

	@Autowired
	PayService payService;
	@Autowired
	AccountService accountService;
	
	
	@GetMapping("/charge")
	String charge(){
		return "/charge/charge";
	}
	
	@GetMapping("/charge/{channelId}/{amount}")
	String chargeAmount(@PathVariable("channelId") Integer channelId, @PathVariable("amount") String amount, Model model){
		
		String orgFileName = amount;
		
		String[] amtArr =  amount.split(",");
		if ("x".equals(amtArr[0])){
			orgFileName = amtArr[0];
			amount = amtArr[1];
		}
		
		List<CashFileDto> dtoList = payService.listCashFileDto(channelId);
		if (!CollectionUtils.isEmpty(dtoList)){
			for (CashFileDto cashFileDto : dtoList){
				if (cashFileDto.getOrgFileName().split("\\.")[0].equals(orgFileName)){
					model.addAttribute("url", cashFileDto.getId());
					break;
				}
			}
		}
		model.addAttribute("amount", amount);
		model.addAttribute("channelId", channelId);
		return "charge/chargePic";
	}
	
	@GetMapping(value = "/charge/loadPic/{id}")
	void loadCustInfoPic(@PathVariable("id") Integer id, HttpServletResponse response) {
		try{
			
			CashFileDto cashFileDto = payService.getCashFileById(id);
			if (null == cashFileDto.getNewFileName()){
				return;
			}
			String filePath = cashFileDto.getNewFlieUrl() + File.separatorChar + cashFileDto.getNewFileName();

			OutputStream output = response.getOutputStream();
			
			if (filePath.toLowerCase().endsWith(".jpg")) {
				response.setContentType("image/jpeg;charset=GB2312");
				InputStream imageIn = new FileInputStream(new File(filePath));
				BufferedImage bufferedImage =  ImageIO.read(imageIn);
				ImageIO.write(bufferedImage, "JPEG", output);
				imageIn.close();
				
			}else{ //非jpg格式都以FileInputStream读取(普通文件)
				response.setContentType("image/jpeg;charset=GB2312");
				//ServletContext context = request.getServletContext();
				//InputStream imageIn = context.getResourceAsStream(imagePath);
				InputStream imageIn = new FileInputStream(new File(filePath));
				BufferedInputStream bis = new BufferedInputStream(imageIn);
				BufferedOutputStream bos = new BufferedOutputStream(output);
				byte data[] = new byte[4096];
				int size = 0;
				size = bis.read(data);
				while (size != -1) {
					bos.write(data, 0, size);
					size = bis.read(data);
				}
				bis.close();
				bos.flush();
				// 清空输出缓冲流
				bos.close();
			}
			output.close();
			
		}catch(Exception e){
			logger.error("显示照片异常:",e.getMessage(), e);
		}
	}
	
	@Log("客户充值")
	@ResponseBody
	@GetMapping("/charge/submit/{amount}/{channelId}/{remark}")
	public AjaxResult chargeSubmit(@PathVariable("amount") BigDecimal amount,
								   @PathVariable("channelId") Integer channelId,
								   @PathVariable("remark") String remark){
		
		try {
			accountService.charegeSubmit(getCurrUser().getId(), amount, "客户", remark, channelId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return AjaxResult.error(e.getMessage());
		}
		
		return AjaxResult.ok();
	}
	
}
