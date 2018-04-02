package com.tbug.ball.service.common.factory;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tbug.ball.service.TestBase;
import com.tbug.ball.service.biz.BizException;

public class CodeFactoryTest extends TestBase {

	@Autowired
	CodeFactory codeFactory;
	
	@Test
	public void brokerCode() throws BizException{
		
		System.out.println(codeFactory.getBrokerCode("M0001"));
	}
}
