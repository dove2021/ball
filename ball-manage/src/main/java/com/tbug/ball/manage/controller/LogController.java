package com.tbug.ball.manage.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tbug.ball.manage.controller.base.BaseController;
import com.tbug.ball.manage.util.R;
import com.tbug.ball.service.LogService;
import com.tbug.ball.service.Dto.PageDto;
import com.tbug.ball.service.Dto.Query;
import com.tbug.ball.service.Dto.system.SysLogDto;

@RequestMapping(LogController.prefix)
@Controller
public class LogController extends BaseController{
	
	public static final String prefix = "/common/log";
	
	@Autowired
	LogService logService;
	
	@GetMapping()
	String log() {
		return prefix + "/log";
	}

	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("common:log:log")
	PageDto<SysLogDto> list(@RequestParam Map<String, Object> params) {
		Query query = new Query(params);
		List<SysLogDto> sysLogDtoList = logService.list(query);
		int totalCount = logService.count(query);
		
		PageDto<SysLogDto> page = new PageDto<SysLogDto>();
		page.setRows(sysLogDtoList);
		page.setTotal(totalCount);
		
		return page;
	}
	
	@ResponseBody
	@PostMapping("/remove")
	@RequiresPermissions("common:log:remove")
	R remove(Long id) {
		if (logService.remove(id)>0) {
			return R.ok();
		}
		return R.error();
	}

	@ResponseBody
	@PostMapping("/batchRemove")
	@RequiresPermissions("common:log:clear")
	R batchRemove(@RequestParam("ids[]") Long[] ids) {
		int r = logService.batchRemove(ids);
		if (r > 0) {
			return R.ok();
		}
		return R.error();
	}
}
