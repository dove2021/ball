package com.tbug.ball.service.dao.system;

import com.tbug.ball.service.model.system.SequenceBrokerLevel;

public interface SequenceBrokerLevelMapper {
    int deleteByPrimaryKey(String name);

    int insert(SequenceBrokerLevel record);

    int insertSelective(SequenceBrokerLevel record);

    SequenceBrokerLevel selectByPrimaryKey(String name);

    int updateByPrimaryKeySelective(SequenceBrokerLevel record);

    int updateByPrimaryKey(SequenceBrokerLevel record);
    
    int cycleSequence(String name);
    
    int incrementSequence(String name);
}