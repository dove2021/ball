package com.tbug.ball.service.biz.schedule;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.tbug.ball.service.TestBase;
import com.tbug.ball.service.model.schedule.Stake;

public class StakeBizTest extends TestBase {

	@Autowired
	StakeBiz stakeBiz;
	
	@Test
	public void test(){
		Stake stake = stakeBiz.getStakeByCode("XZ01");
		
		System.out.println(JSON.toJSONString(stake));
	}
}
