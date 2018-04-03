package com.tbug.ball.service.biz.system;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tbug.ball.service.TestBase;
import com.tbug.ball.service.biz.BizException;

public class SequenceBrokerLevelBizTest extends TestBase {

	@Autowired
	SequenceBrokerLevelBiz sequenceBrokerLevelBiz;
	
	@Test
	public void test1() throws BizException{
		
		System.out.println(sequenceBrokerLevelBiz.getLevelCode(""));
	}
}
