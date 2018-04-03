package com.tbug.ball.manage.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tbug.ball.manage.controller.base.BaseController;
import com.tbug.ball.manage.util.FileUtil;
import com.tbug.ball.manage.util.R;
import com.tbug.ball.service.PayService;
import com.tbug.ball.service.Dto.pay.CashFileDto;
import com.tbug.ball.service.Dto.pay.PaymentChannelDto;

@Controller
@RequestMapping(CashController.prefix)
public class CashController extends BaseController {

	public static final String prefix = "sys/cash";
	
	@Autowired
	PayService payService;
	
	@Value("${pic_upload_dir}")
	String picUploadDir;
	
	@GetMapping("")
	@RequiresPermissions("sys:cash:channel")
	String cash(Model model){
		List<PaymentChannelDto> dtoList = payService.listPaymentChannelAll();
		model.addAttribute("zfb", dtoList.get(0));
		model.addAttribute("wz", dtoList.get(1));
		model.addAttribute("yl", dtoList.get(2));
		return prefix + "/cash";
	}
	
	@ResponseBody
	@PostMapping(value = "/uploadPic/{channelId}")
	@RequiresPermissions(value={"sys:cash:charge","sys:cash:withdraw"})
	R uploadFrontPic(@RequestParam("file") MultipartFile file, @PathVariable("channelId") Integer channelId){
/*		try {
			// 检查本次是否一个文件上传请求
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (!isMultipart) {
				return R.error();
			}
			
			DiskFileItemFactory factory = new DiskFileItemFactory(); 	// 创建一个工厂基于磁盘的文件项
			factory.setRepository(new File(picUploadDir)); 				// 配置储存库（确保安全的临时位置时）
			ServletFileUpload upload = new ServletFileUpload(factory); 	// 创建一个新的文件上传处理程序
			upload.setSizeMax(1024 * 1024 * 5); 						// 设置总体要求尺寸限制（建议前后台分别设置，因为前后台用到了不同的插件）
			List<FileItem> items = upload.parseRequest(request); 		// 解析请求
			Iterator<FileItem> iter = items.iterator(); 				// 处理上传的项目
			while (iter.hasNext()) { 									// 如果是一次性上传多个文件，那这里会分别去保存
				FileItem item = iter.next();
				if (!item.isFormField()) {
					if ("".equals(item.getName())) {
						continue;
					}
					String s_name = item.getName(); 				// 获得原始文件名
					int position = s_name.lastIndexOf(".");
					String s_fileType = s_name.substring(position,
							s_name.length()); 						// 获得文件后缀
					
					System.out.println(s_name + " " + s_fileType);
					
					File path = new File("");
					item.write(path);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			
		}
		return R.ok();*/
		
		String[] amountName = {"10","50","100","200","500","1000","3000","x"};
		String fileName = file.getOriginalFilename();
		
		boolean flag = false;
		String[] prefixFileName = fileName.split("\\.");
		for (int i = 0; i < amountName.length; i++){
			if (amountName[i].equals(prefixFileName[0])){
				flag = true;
				break;
			}
		}
		if (!flag){
			return R.error("文件名必须和金额相同");
		}
		
		String newFileName = FileUtil.renameToUUID(fileName);
		String basePath = picUploadDir + channelId + "/";
		try {
			FileUtil.uploadFile(file.getBytes(), basePath, newFileName);
		} catch (Exception e) {
			return R.error();
		}
		
		String reStr = "上传成功";
		CashFileDto cashFileDto = payService.getCashFileDtoByChannelAndName(channelId, fileName);
		if (null != cashFileDto){
			payService.delCashFile(cashFileDto.getId());
			reStr = "替换成功";
		}
		
		CashFileDto dto = new CashFileDto();
		dto.setChannelId(channelId);
		dto.setCreatePerson(getCurrentUser().getLoginName());
		dto.setOrgFileName(fileName);
		dto.setNewFileName(newFileName);
		dto.setNewFlieUrl(basePath);
		dto.setFileSize((int) file.getSize());
		
		payService.createCashFile(dto);
		
		return R.ok(reStr);
	}
	
	@ResponseBody
	@GetMapping("/files/{channelId}")
	@RequiresPermissions(value={"sys:cash:charge","sys:cash:withdraw"})
	List<CashFileDto> cashFiles(@PathVariable("channelId") Integer channelId){
		List<CashFileDto> dtoList = payService.listCashFileDto(channelId);
		return dtoList;
	}
	
	@GetMapping(value = "/loadPic/{id}")
	@RequiresPermissions(value={"sys:cash:charge","sys:cash:withdraw"})
	void loadCustInfoPic(@PathVariable("id") Integer id, HttpServletResponse response) {
		
		OutputStream output = null;
		
		try{
			CashFileDto cashFileDto = payService.getCashFileById(id);
			if (null == cashFileDto.getNewFileName()){
				return;
			}
			String filePath = cashFileDto.getNewFlieUrl() + File.separatorChar + cashFileDto.getNewFileName();

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
			}
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
	@GetMapping("/remove/{id}")
	R removePic(@PathVariable("id") Integer id){
		payService.delCashFile(id);
		return R.ok();
	}
	
	@ResponseBody
	@PostMapping("/save/{channelId}")
	@RequiresPermissions(value={"sys:cash:charge","sys:cash:withdraw"})
	R save(@PathVariable("channelId") Integer channelId, PaymentChannelDto dto){
		dto.setChannelId(channelId);
		if (payService.updCashFile(dto)){
			return R.ok();
		}
		return R.error();
	}
	
}
