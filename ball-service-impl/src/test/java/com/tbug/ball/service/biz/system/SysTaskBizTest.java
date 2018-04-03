package com.tbug.ball.service.biz.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.tbug.ball.service.TestBase;
import com.tbug.ball.service.model.system.SysTask;

public class SysTaskBizTest extends TestBase{

	@Autowired
	SysTaskBiz sysTaskBiz;
	
	@Test
	public void test1(){
		Map<String, Object> params = new HashMap<>();
		params.put("status", "1");
		
		List<SysTask> list = sysTaskBiz.listSysTaskByMap(params);
		
		System.out.println(JSON.toJSONString(list));
	}
	
}
