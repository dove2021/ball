package com.tbug.ball.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.tbug.ball.service.TestBase;
import com.tbug.ball.service.Dto.schedule.ScheduleDto;

public class ScheduleServiceImplTest extends TestBase {

	@Autowired
	ScheduleServiceImpl scheduleServiceImpl;
	
	@Test
	public void test(){
		
		Map<String, Object> params = new HashMap<>();
		
		List<ScheduleDto> scheduleDtoList = scheduleServiceImpl.listScheduleDtoByMap(params);
		
		if (!CollectionUtils.isEmpty(scheduleDtoList)){
			
			
			for (ScheduleDto scheduleDto : scheduleDtoList){
				System.out.println(JSON.toJSONString(scheduleDto));
			}
		}
		
	}
	
}
