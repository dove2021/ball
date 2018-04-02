package com.tbug.ball.service.biz.schedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbug.ball.service.dao.schedule.StakeMapper;
import com.tbug.ball.service.model.schedule.Stake;

@Service
public class StakeBiz {

	@Autowired
	StakeMapper stakeMapper;
	
	public Stake getStakeById(Integer id){
		return stakeMapper.selectByPrimaryKey(id);
	}
	
	public Integer updStake(Stake stake){
		return stakeMapper.updateByPrimaryKeySelective(stake);
	}
	
	public Stake getStakeByCode(String code){
		return stakeMapper.selectByCode(code);
	}
	
	public List<Stake> listStakeAll(){
		return stakeMapper.listStake();
	}
}
