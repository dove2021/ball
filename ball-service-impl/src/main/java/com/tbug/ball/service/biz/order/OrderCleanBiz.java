package com.tbug.ball.service.biz.order;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbug.ball.service.dao.order.OrderCleanMapper;
import com.tbug.ball.service.model.order.OrderClean;
import com.tbug.ball.service.model.order.OrderCleanEx;

@Service
public class OrderCleanBiz {

	@Autowired
	OrderCleanMapper orderCleanMapper;
	
	public Integer createOrderClean(OrderClean orderClean){
		return orderCleanMapper.insertSelective(orderClean);
	}
	
	public OrderClean getOrderCleanById(Integer id){
		return orderCleanMapper.selectByPrimaryKey(id);
	}
	
	public OrderClean getOrderCleanByIdForUpdate(Integer id){
		return orderCleanMapper.selectByPrimaryKeyForUpdate(id);
	}
	
	public Integer updOrderClean(OrderClean orderClean){
		return orderCleanMapper.updateByPrimaryKeySelective(orderClean);
	}
	
	public List<OrderCleanEx> listByMap(Map<String, Object> params){
		return orderCleanMapper.listByMap(params);
	}
}
