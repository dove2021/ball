package com.tbug.ball.manage.common.quartz;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.tbug.ball.manage.common.quartz.utils.QuartzManager;
import com.tbug.ball.service.TaskService;
import com.tbug.ball.service.Dto.system.SysTaskDto;
import com.tbug.ball.service.common.DBContants.SysTaskConst;

@Component
public class InitQuartz {

	@Autowired
	TaskService taskService;
	
	@Autowired
	QuartzManager quartzManager;
	
	public void initJob(){
		
		List<SysTaskDto> dtoList = taskService.listSysTask(new HashMap<>());
		if (!CollectionUtils.isEmpty(dtoList)){
			for (SysTaskDto dto : dtoList){
				if (!SysTaskConst.STATUS_RUNNING.equals(dto.getJobStatus())){
					quartzManager.addJob(dto);
				}
			}
		}
	}
	
}
