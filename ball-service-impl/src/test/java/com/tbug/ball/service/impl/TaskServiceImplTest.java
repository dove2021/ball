package com.tbug.ball.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.tbug.ball.service.TestBase;
import com.tbug.ball.service.Dto.system.SysTaskDto;

public class TaskServiceImplTest extends TestBase {

	@Autowired
	TaskServiceImpl taskServiceImpl;
	
	
	@Test
	public void test1(){
		
		Map<String, Object> params = new HashMap<>();
		
		List<SysTaskDto> dtoList = taskServiceImpl.listSysTask(params);
		
		System.out.println(JSON.toJSONString(dtoList));
	}
	
}
