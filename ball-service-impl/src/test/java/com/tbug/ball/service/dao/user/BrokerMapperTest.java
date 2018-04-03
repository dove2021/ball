package com.tbug.ball.service.dao.user;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tbug.ball.service.TestBase;

public class BrokerMapperTest extends TestBase {

	@Autowired
	BrokerMapper brokerMapper;
	
	@Test
	public void test(){
		
		brokerMapper.selectByPrimaryKey(1);
		
	}
}
