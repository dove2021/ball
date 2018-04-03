package com.tbug.ball.service;

import java.util.List;
import java.util.Map;

import com.tbug.ball.service.Dto.system.SysTaskDto;

public interface TaskService {

	List<SysTaskDto> listSysTask(Map<String, Object> params);
	
}
