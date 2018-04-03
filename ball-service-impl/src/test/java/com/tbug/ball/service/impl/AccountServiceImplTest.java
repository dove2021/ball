package com.tbug.ball.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;

import com.tbug.ball.service.ServiceException;
import com.tbug.ball.service.TestBase;

public class AccountServiceImplTest extends TestBase {

	@Autowired
	AccountServiceImpl accountServiceImpl;
	
	//@Test
	public void test() throws ServiceException{
//		accountServiceImpl.customerCharge(3, new BigDecimal("100"), "");
	}
	
}
