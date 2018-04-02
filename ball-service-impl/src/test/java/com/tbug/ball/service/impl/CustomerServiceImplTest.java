package com.tbug.ball.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.tbug.ball.service.ServiceException;
import com.tbug.ball.service.TestBase;

public class CustomerServiceImplTest extends TestBase {

	@Autowired
	CustomerServiceImpl customerServiceImpl;
	
	@Test
	public void test() throws ServiceException {
		//customerServiceImpl.createCustomer("151024591", "123456", "M1001", "xiaox");
		
/*		CustomerDto customerDto = customerServiceImpl.getCustomerDtoById(4);
		customerServiceImpl.moveBroker(3, customerDto);*/
		
		
		Map<String, Object> params = new HashMap<>();
		params.put("offset", 0);
		params.put("limit", 3);
		params.put("accountId", 2);
		
		Map<String, Object> map = customerServiceImpl.sumCustomerAccountFlowByMap(params);
		System.out.println(JSON.toJSONString(map));
	}
	
}
