package com.tbug.ball.service;

import java.util.List;
import java.util.Map;

import com.tbug.ball.service.Dto.schedule.ScheduleDto;
import com.tbug.ball.service.Dto.schedule.StakeDto;

public interface ScheduleService {

	boolean createSchedule(ScheduleDto scheduleDto) throws ServiceException;
	
	List<ScheduleDto> listScheduleDtoByMap(Map<String, Object> params);
	
	Integer countScheduleDtoByMap(Map<String, Object> params);
	
	boolean updSchedule(ScheduleDto scheduleDto) throws ServiceException;
	
	ScheduleDto getScheduleDtoById(Integer scheduleId);
	
	/**
	 * 可押注的赛程
	 * @param memberId
	 * @return
	 */
	List<ScheduleDto> listScheduleInfoDtoByNormal(Integer memberId);
	
	/** 下注类型 */
	List<StakeDto> listStake();
	
}
