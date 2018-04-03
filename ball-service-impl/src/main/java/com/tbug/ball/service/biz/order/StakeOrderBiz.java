package com.tbug.ball.service.biz.order;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbug.ball.service.biz.BizException;
import com.tbug.ball.service.common.factory.CodeFactory;
import com.tbug.ball.service.dao.order.StakeOrderMapper;
import com.tbug.ball.service.model.order.StakeOrder;
import com.tbug.ball.service.model.order.StakeOrderEx;

@Service
public class StakeOrderBiz {
	
	@Autowired
	StakeOrderMapper stakeOrderMapper;
	@Autowired
	CodeFactory codeFactory;
	
	public Integer createStakeOrder(StakeOrder stakeOrder) throws BizException{
		stakeOrder.setOrderNo(codeFactory.getOrderNo());
		return stakeOrderMapper.insertSelective(stakeOrder);
	}
	
	public StakeOrder getStakeOrderById(Integer id){
		return stakeOrderMapper.selectByPrimaryKey(id);
	}
	
	public StakeOrder getStakeOrderByIdForUpdate(Integer id){
		return stakeOrderMapper.selectByPrimaryKeyForUpdate(id);
	}
	
	public Integer updStakeOrder(StakeOrder stakeOrder){
		return stakeOrderMapper.updateByPrimaryKeySelective(stakeOrder);
	}
	
	public Integer delStakeOrder(Integer id){
		return stakeOrderMapper.deleteByPrimaryKey(id);
	}
	
	public List<StakeOrderEx> listStakeOrderByMap(Map<String, Object> params){
		return stakeOrderMapper.listByMap(params);
	}
	
	public int countStakeOrderByMap(Map<String, Object> params){
		return stakeOrderMapper.countByMap(params);
	}
}
