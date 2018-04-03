package com.tbug.ball.service.dao.system;

import java.util.List;
import java.util.Map;

import com.tbug.ball.service.model.system.SysTask;

public interface SysTaskMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysTask record);

    int insertSelective(SysTask record);

    SysTask selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysTask record);

    int updateByPrimaryKey(SysTask record);
    
    List<SysTask> listByMap(Map<String, Object> params);
}