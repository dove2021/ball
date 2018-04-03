package com.tbug.ball.service.biz.schedule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.tbug.ball.service.TestBase;
import com.tbug.ball.service.model.schedule.Schedule;

public class ScheduleBizTest extends TestBase{

	@Autowired
	ScheduleBiz scheduleBiz;
	
	@Test
	public void test1(){
		
		Map<String, Object> params = new HashMap<>();
		
		List<Schedule> scheduleList = scheduleBiz.getScheduleByMap(params);
		
		if (!CollectionUtils.isEmpty(scheduleList)){
			
			for (Schedule schedule : scheduleList){
				
				System.out.println(JSON.toJSONString(schedule));
			}
		}
		
	}
}
