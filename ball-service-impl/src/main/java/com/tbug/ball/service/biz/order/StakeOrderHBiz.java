package com.tbug.ball.service.biz.order;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbug.ball.service.dao.order.StakeOrderHMapper;
import com.tbug.ball.service.model.order.StakeOrderH;

@Service
public class StakeOrderHBiz {

	@Autowired
	StakeOrderHMapper stakeOrderHMapper;
	
	public StakeOrderH getStakeOrderHById(Integer id){
		return stakeOrderHMapper.selectByPrimaryKey(id);
	}
	
	public Integer createStakeOrderH(StakeOrderH stakeOrderH){
		return stakeOrderHMapper.insert(stakeOrderH);
	}
	
	public List<StakeOrderH> listByMap(Map<String, Object> params){
		return stakeOrderHMapper.listByMap(params);
	}
	
	public Integer countByMap(Map<String, Object> params){
		return stakeOrderHMapper.countByMap(params);
	}
	
}
