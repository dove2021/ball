package com.tbug.ball.service.dao.system;

import com.tbug.ball.service.model.system.SysTask;

public interface SysTaskMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysTask record);

    int insertSelective(SysTask record);

    SysTask selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysTask record);

    int updateByPrimaryKey(SysTask record);
}