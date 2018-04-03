package com.tbug.ball.service.biz.pay;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.tbug.ball.service.TestBase;
import com.tbug.ball.service.model.pay.CashFile;

public class CashFileBizTest extends TestBase {

	@Autowired
	CashFileBiz cashFileBiz;
	
	@Test
	public void test(){
		List<CashFile> list = cashFileBiz.listByStatus(1, "1");
		
		for (CashFile c : list){
			System.out.println(JSON.toJSONString(c));
		}
	}
	
}
