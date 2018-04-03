package com.tbug.ball.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.tbug.ball.service.TestBase;
import com.tbug.ball.service.Dto.pay.ChargeRecordDto;
import com.tbug.ball.service.Dto.pay.PaymentChannelDto;

public class PayServiceImplTest extends TestBase {

	@Autowired
	PayServiceImpl payServiceImpl;
	
	//@Test
	public void test(){
		
		List<PaymentChannelDto> dtoList = payServiceImpl.listPaymentChannelAll();
		for (PaymentChannelDto dto : dtoList){
			
			System.out.println(JSON.toJSONString(dto));
		}
	}
	
	//@Test
	public void test1(){
		
		Map<String, Object> params = new HashMap<>();
		params.put("field", "customer_code");
		params.put("sort", "desc");
		params.put("offset", 1);
		params.put("limit", 10);
		
		List<ChargeRecordDto> list = payServiceImpl.listChargeRecordByMap(params);
		if (list != null && list.size() > 0){
			for (ChargeRecordDto dto : list){
				System.out.println(JSON.toJSONString(dto));
			}
		}
	}
	
	@Test
	public void test2(){
		
		payServiceImpl.getWithdrawFile(1, 2);
	}
	
}
