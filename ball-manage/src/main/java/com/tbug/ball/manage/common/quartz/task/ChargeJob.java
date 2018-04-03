package com.tbug.ball.manage.common.quartz.task;

import java.util.HashMap;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.tbug.ball.service.PayService;
import com.tbug.ball.service.common.DBContants.ChargeRecordConst;

@Component
public class ChargeJob implements Job {

	@Autowired
	SimpMessagingTemplate template;
	
	@Autowired
	PayService payService;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		Map<String, Object> params = new HashMap<>();
		params.put("status", ChargeRecordConst.STATUS_1);
		int total = payService.countChargeRecordByMap(params);
		
		if (total > 0){
			template.convertAndSend("/topic/getResponse", "待处理入金数量：" + total);		
		}
	}

}
