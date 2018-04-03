package com.tbug.ball.service.biz.pay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbug.ball.service.dao.pay.CustomerWithdrawMapper;
import com.tbug.ball.service.model.pay.CustomerWithdraw;

@Service
public class CustomerWithdrawBiz {

	@Autowired
	CustomerWithdrawMapper customerWithdrawMapper;
	
	public int create(CustomerWithdraw customerWithdraw){
		return customerWithdrawMapper.insert(customerWithdraw);
	}
	
	public CustomerWithdraw getById(Integer id){
		return customerWithdrawMapper.selectByPrimaryKey(id);
	}
	
	public int upd(CustomerWithdraw customerWithdraw){
		return customerWithdrawMapper.updateByPrimaryKeySelective(customerWithdraw);
	}
	
}
