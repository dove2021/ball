package com.tbug.ball.service.dao.schedule;

import java.util.List;

import com.tbug.ball.service.model.schedule.Stake;

public interface StakeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Stake record);

    int insertSelective(Stake record);

    Stake selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Stake record);

    int updateByPrimaryKey(Stake record);
    
    Stake selectByCode(String stakeCode);
    
    List<Stake> listStake();
}