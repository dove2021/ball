package com.tbug.ball.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.tbug.ball.service.TaskService;
import com.tbug.ball.service.Dto.system.SysTaskDto;
import com.tbug.ball.service.biz.system.SysTaskBiz;
import com.tbug.ball.service.model.system.SysTask;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	SysTaskBiz sysTaskBiz;
	
	@Override
	public List<SysTaskDto> listSysTask(Map<String, Object> params) {
		
		List<SysTask> list = sysTaskBiz.listSysTaskByMap(params);
		if (CollectionUtils.isEmpty(list)){
			return new ArrayList<>(0);
		}
		
		List<SysTaskDto> dtoList = new ArrayList<>();
		for (SysTask sysTask : list){
			SysTaskDto dto  = new SysTaskDto();
			BeanUtils.copyProperties(sysTask, dto);
			dtoList.add(dto);
		}
		return dtoList;
	}

}
