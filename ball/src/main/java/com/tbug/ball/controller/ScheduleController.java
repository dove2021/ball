package com.tbug.ball.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tbug.ball.service.ScheduleService;
import com.tbug.ball.service.Dto.schedule.ScheduleDto;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {

	@Autowired
	private ScheduleService scheduleService;
	
	@ResponseBody
	@GetMapping("/list")
	List<ScheduleDto> scheduleList(){
		
		Map<String, Object> params = new HashMap<>();
		return scheduleService.listScheduleDtoByMap(params);
	}
}
