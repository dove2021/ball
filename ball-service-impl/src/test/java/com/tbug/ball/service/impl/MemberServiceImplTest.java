package com.tbug.ball.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.tbug.ball.service.ServiceException;
import com.tbug.ball.service.TestBase;
import com.tbug.ball.service.Dto.user.MemberDto;

public class MemberServiceImplTest extends TestBase {

	@Autowired
	MemberServiceImpl memberServiceImpl;
	
	@Test
	public void test(){
		
		try {
			
			memberServiceImpl.createMember("admin", "M1005", "会员单位M1005", "联系人", "15998639625", "15998639625","123456", "");
			
			Map<String, Object> params = new HashMap<>();
			// params.put("phoneNo", "15998639623");
			
			List<MemberDto> memberDtoList = memberServiceImpl.listMemberDtoByMap(params);
			for (MemberDto memberDto : memberDtoList){
				System.out.println(JSON.toJSONString(memberDto));
			}
			
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
