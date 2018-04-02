package com.tbug.ball.service.dao.schedule;

import com.tbug.ball.service.model.schedule.ScheduleOdds;

public interface ScheduleOddsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ScheduleOdds record);

    int insertSelective(ScheduleOdds record);

    ScheduleOdds selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ScheduleOdds record);

    int updateByPrimaryKey(ScheduleOdds record);
}