package com.tbug.ball.service.dao.schedule;

import java.util.List;
import java.util.Map;

import com.tbug.ball.service.model.schedule.Schedule;

public interface ScheduleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Schedule record);

    int insertSelective(Schedule record);

    Schedule selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Schedule record);

    int updateByPrimaryKey(Schedule record);
    
    List<Schedule> listByMap(Map<String, Object> params);
    
    Integer countByMap(Map<String, Object> params);
}