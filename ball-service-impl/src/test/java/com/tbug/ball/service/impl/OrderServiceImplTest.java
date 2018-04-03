package com.tbug.ball.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.tbug.ball.service.TestBase;
import com.tbug.ball.service.Dto.order.StakeOrderHDto;

public class OrderServiceImplTest extends TestBase {

	@Autowired
	OrderServiceImpl orderServiceImpl;
	
	@Test
	public void test(){
		// orderServiceImpl.listOrderCleanByCustomerId(1, OrderCleanConst.IS_DRAW_N);
		
		Map<String, Object> params = new HashMap<>();
		params.put("offset", 0);
		params.put("limit", 10);
		List<StakeOrderHDto> list = orderServiceImpl.listStakeOrderHDtoByMap(params);
		
		for (StakeOrderHDto dto : list){
			System.out.println(JSON.toJSONString(dto));
		}
	}
	
}
