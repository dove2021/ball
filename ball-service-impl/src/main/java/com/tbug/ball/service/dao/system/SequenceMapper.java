package com.tbug.ball.service.dao.system;

import com.tbug.ball.service.model.system.Sequence;

public interface SequenceMapper {
    int deleteByPrimaryKey(String name);

    int insert(Sequence record);

    int insertSelective(Sequence record);

    Sequence selectByPrimaryKey(String name);

    int updateByPrimaryKeySelective(Sequence record);

    int updateByPrimaryKey(Sequence record);
    
    int cycleSequence(String name);
    
    int incrementSequence(String name);
}