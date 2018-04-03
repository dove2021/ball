package com.tbug.ball.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tbug.ball.common.AjaxResult;
import com.tbug.ball.controller.base.BaseController;
import com.tbug.ball.service.AccountService;
import com.tbug.ball.service.CustomerService;
import com.tbug.ball.service.PayService;
import com.tbug.ball.service.ServiceException;
import com.tbug.ball.service.Dto.account.CustomerAccountDto;
import com.tbug.ball.service.Dto.pay.CustomerWithdrawDto;
import com.tbug.ball.service.Dto.pay.PaymentChannelDto;
import com.tbug.ball.service.Dto.pay.WithdrawFileDto;
import com.tbug.ball.service.common.DBContants.PaymentChannelConst;
import com.tbug.ball.service.common.DBContants.WithdrawFileConst;
import com.tbug.ball.utils.FileUtil;

@Controller
public class WithdrawController extends BaseController {

	@Autowired
	PayService payService;
	@Autowired
	CustomerService customerService;
	@Autowired
	AccountService accountService;
	
	@Value("${pic_upload_dir}")
	String picUploadDir;
	
	@GetMapping("/withdraw")
	String withdraw(Model model){
		CustomerAccountDto dto = customerService.getCustomerAccountByCustomerId(getCurrUser().getId());
		model.addAttribute("customerAccount", dto);
		
		List<PaymentChannelDto> list = payService.listPaymentChannelAll();
		model.addAttribute("zfb", list.get(0));
		model.addAttribute("wx", list.get(1));
		model.addAttribute("yl", list.get(2));
		
		CustomerWithdrawDto withdrawDto = accountService.getCustomerWithdrawDtoById(getCurrUser().getId());
		model.addAttribute("withdrawDto", withdrawDto);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> params = new HashMap<>();
		params.put("applyDateStart", sdf.format(new Date()) + " 00:00:00");
		params.put("applyDateEnd", sdf.format(new Date()) + " 23:59:59");
		params.put("customerId", getCurrUser().getId());
		params.put("channelId", PaymentChannelConst.CHANNEL_ID_1);
		Integer wfb_times = accountService.customerWithdrawTimes(params);
		model.addAttribute("wfb_times", wfb_times);
		
		params.put("channelId", PaymentChannelConst.CHANNEL_ID_2);
		Integer wx_times = accountService.customerWithdrawTimes(params);
		model.addAttribute("wx_times", wx_times);
		
		params.put("channelId", PaymentChannelConst.CHANNEL_ID_3);
		Integer yl_times = accountService.customerWithdrawTimes(params);
		model.addAttribute("yl_times", yl_times);
		
		return "/withdraw/withdraw";
	}

	@GetMapping("/w/edit")
	String wEdit(Model model){
		CustomerWithdrawDto withdrawDto = accountService.getCustomerWithdrawDtoById(getCurrUser().getId());
		model.addAttribute("withdrawDto", withdrawDto);
		
		return "/withdraw/w";
	}
	
	@ResponseBody
	@PostMapping("/w/save")
	AjaxResult wSave(HttpServletRequest request){
		
		String linkMan = request.getParameter("linkMan");
		String linkPhoneNo = request.getParameter("linkPhoneNo");
		
		if (StringUtils.isEmpty(linkMan)){
			return AjaxResult.error("联系人不能为空");
		}
		if (linkMan.length() > 32){
			return AjaxResult.error("联系人长度32位以内");
		}
		if (StringUtils.isEmpty(linkPhoneNo)){
			return AjaxResult.error("联系电话不能为空");
		}
		if (linkPhoneNo.length() > 64){
			return AjaxResult.error("联系电话长度64位以内");
		}
		
		CustomerWithdrawDto dto = new CustomerWithdrawDto();
		dto.setId(getCurrUser().getId());
		dto.setLinkMan(linkMan);
		dto.setLinkPhoneNo(linkPhoneNo);
		
		try {
			accountService.updCustomerWithdraw(dto);
		} catch (ServiceException e) {
			logger.error(e.getMessage());
			AjaxResult.error(e.getMessage());
		}
		
		return AjaxResult.ok();
	}
	
	@ResponseBody
	@PostMapping("/w/submit")
	AjaxResult wSubmit(HttpServletRequest request){
		String channelId = request.getParameter("channelId");
		String withdrawAmountStr = request.getParameter("withdrawAmount");
		
		if (StringUtils.isEmpty(channelId)){
			return AjaxResult.error("提现渠道不能为空");
		}
		if (StringUtils.isEmpty(withdrawAmountStr)){
			return AjaxResult.error("提现金额不能为空");
		}
		
		try {
			accountService.customerWithdraw(getCurrUser().getId(), channelId, withdrawAmountStr);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return AjaxResult.error(e.getMessage());
		}
		
		return AjaxResult.ok();
	}
	
	@ResponseBody
	@PostMapping(value = "/uploadPic/{channelId}")
	AjaxResult uploadFrontPic(@RequestParam("file") MultipartFile file, @PathVariable("channelId") Integer channelId){
		
		String fileName = file.getOriginalFilename();
		
		String newFileName = FileUtil.renameToUUID(fileName);
		String basePath = picUploadDir + getCurrUser().getId() + "/";
		
		if (file.getSize() > 1000 * 1000){
			return AjaxResult.error("图片不能超过1M");
		}
		if (!FileUtil.isPic(newFileName)){
			return AjaxResult.error("图片格式错误");
		}
		
		try {
			// FileUtil.uploadFile(file.getBytes(), basePath, newFileName);
		
			String reStr = "上传成功";
			WithdrawFileDto Dto = payService.getWithdrawFile(getCurrUser().getId(), channelId);
			if (null != Dto){
				payService.updWithdrawFile(getCurrUser().getId(), channelId);
				reStr = "替换成功";
			}
			
			WithdrawFileDto withdrawFileDto = new WithdrawFileDto();
			withdrawFileDto.setCustomerId(getCurrUser().getId());
			withdrawFileDto.setChannelId(channelId);
			withdrawFileDto.setCreaterDate(new Date());
			withdrawFileDto.setFilePath(basePath);
			withdrawFileDto.setFileSize((int) file.getSize());
			withdrawFileDto.setModifyDate(new Date());
			withdrawFileDto.setNewFileName(newFileName);
			withdrawFileDto.setOrgFileName(fileName);
			withdrawFileDto.setStatus(WithdrawFileConst.STATUS_1);
			withdrawFileDto.setImgContent(file.getBytes());
		
			payService.createWithdrawFile(withdrawFileDto);
			
			return AjaxResult.ok(reStr);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return AjaxResult.error();
		}
	}
	
	@GetMapping(value = "/loadPic/{channelId}")
	void loadCustInfoPic(@PathVariable("channelId") Integer channelId, HttpServletResponse response) {
		
		OutputStream output = null;
		
		try{
			WithdrawFileDto withdrawFileDto = payService.getWithdrawFile(getCurrUser().getId(), channelId);
			if (null == withdrawFileDto){
				return;
			}
			
			response.setContentType("image/jpeg;charset=GB2312");
			output = response.getOutputStream();
			output.write(withdrawFileDto.getImgContent());
			output.flush();
			output.close();
			
/*			String filePath = withdrawFileDto.getFilePath() + File.separatorChar + withdrawFileDto.getNewFileName();

			output = response.getOutputStream();
			
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
			}*/
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
	
	@GetMapping("/pic/{channelId}")
	public String picChannel(@PathVariable("channelId")Integer channelId, Model model){
		model.addAttribute("channelId", channelId);
		
		return "/withdraw/pic";
	}
	
}
