package com.tbug.ball.service.biz.pay;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbug.ball.service.dao.pay.WithdrawFileMapper;
import com.tbug.ball.service.model.pay.WithdrawFile;

@Service
public class WithdrawFileBiz {

	@Autowired
	WithdrawFileMapper withdrawFileMapper;
	
	public int create(WithdrawFile withdrawFile){
		return withdrawFileMapper.insertSelective(withdrawFile);
	}
	
	public List<WithdrawFile> listWithdrawFileById(Integer customerId){
		return withdrawFileMapper.ListByCustomerId(customerId);
	}
	
	public int updWithdrawFile(WithdrawFile withdrawFile){
		return withdrawFileMapper.updateByPrimaryKey(withdrawFile);
	}
	
	public WithdrawFile getByCustomer(@Param("customerId")Integer customerId, @Param("channelId")Integer channelId){
		return withdrawFileMapper.getWithdrawFile(customerId, channelId);
	}
	
}
