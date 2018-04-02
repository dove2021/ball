package com.tbug.ball.service.biz.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbug.ball.service.dao.order.BrokerProfitMapper;
import com.tbug.ball.service.model.order.BrokerProfit;

@Service
public class BrokerProfitBiz {

	@Autowired
	BrokerProfitMapper brokerProfitMapper;
	
	public BrokerProfit getById(Integer id){
		return brokerProfitMapper.selectByPrimaryKey(id);
	}
	
	public Integer update(BrokerProfit brokerProfit){
		return brokerProfitMapper.updateByPrimaryKeySelective(brokerProfit);
	}
	
	public List<BrokerProfit> listAll(){
		return brokerProfitMapper.listAll();
	}
	
}
