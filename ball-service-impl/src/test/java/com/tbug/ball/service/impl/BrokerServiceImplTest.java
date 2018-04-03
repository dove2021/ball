package com.tbug.ball.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.tbug.ball.service.ServiceException;
import com.tbug.ball.service.TestBase;
import com.tbug.ball.service.Dto.user.BrokerDto;
import com.tbug.ball.service.common.DBContants.BrokerConst;

public class BrokerServiceImplTest extends TestBase {

	@Autowired
	BrokerServiceImpl brokerServiceImpl;
	
	@Test
	public void test1() throws ServiceException{
		
		brokerServiceImpl.createBroker("admin", "M1003", "15102179999", "15102179999", "123456", 4);
		
		Map<String, Object> params = new HashMap<>();
		// params.put("phoneNo", "15998639623");
		
		List<BrokerDto> brokerDtoList = brokerServiceImpl.listBrokerDtoByMap(params);
		for (BrokerDto brokerDto : brokerDtoList){
			System.out.println(JSON.toJSONString(brokerDto));
		}
		
		System.out.println(brokerServiceImpl.countBrokerDtoByMap(params));
		
		BrokerDto brokerDto = brokerServiceImpl.getBrokerDtoById(4);
		System.out.println(JSON.toJSONString(brokerDto));
		
		brokerDto.setStatus(BrokerConst.STATUS_NORMAL);
		brokerServiceImpl.updBroker(brokerDto);
		
		BrokerDto brokerDto1 = brokerServiceImpl.getBrokerDtoById(4);
		System.out.println(JSON.toJSONString(brokerDto1));
	}
	
	//@Test
	public void test2() throws ServiceException{
		
		BrokerDto brokerDto = brokerServiceImpl.getBrokerDtoById(3);
		
		brokerServiceImpl.moveBroker(-1, brokerDto);
	}
}
