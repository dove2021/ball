package com.tbug.ball.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.tbug.ball.service.ScheduleService;
import com.tbug.ball.service.ServiceException;
import com.tbug.ball.service.Dto.schedule.ScheduleDto;
import com.tbug.ball.service.Dto.schedule.StakeDto;
import com.tbug.ball.service.biz.schedule.ScheduleBiz;
import com.tbug.ball.service.biz.schedule.StakeBiz;
import com.tbug.ball.service.biz.user.MemberBiz;
import com.tbug.ball.service.common.Constants.CacheConst;
import com.tbug.ball.service.model.schedule.Schedule;
import com.tbug.ball.service.model.schedule.Stake;
import com.tbug.ball.service.model.user.Member;

@Service
public class ScheduleServiceImpl implements ScheduleService {
	private static final Logger logger = LoggerFactory.getLogger(ScheduleServiceImpl.class);
	
	@Autowired
	ScheduleBiz scheduleBiz;
	@Autowired
	MemberBiz memberBiz;
	@Autowired
	StakeBiz stakeBiz;

	@Override
	public boolean createSchedule(ScheduleDto scheduleDto) throws ServiceException {
		try{
			
			if (scheduleDto == null){
				throw new ServiceException("赛程信息不能为空");
			}
			if (StringUtils.isEmpty(scheduleDto.getaName())){
				throw new ServiceException("甲方名字不能为空");
			}
			if (StringUtils.isEmpty(scheduleDto.getbName())){
				throw new ServiceException("乙方名字不能为空");
			}
			if (StringUtils.isEmpty(scheduleDto.getCreatePerson())){
				throw new ServiceException("创建人不能为空");
			}
			if (StringUtils.isEmpty(scheduleDto.getMemberCode())){
				throw new ServiceException("会员单位不能为空");
			}
			if (null == scheduleDto.getOddsWin()){
				throw new ServiceException("赢赔率不能为空");
			}
			if (null == scheduleDto.getOddsDraw()){
				throw new ServiceException("平赔率不能为空");
			}
			if (null == scheduleDto.getOddsLose()){
				throw new ServiceException("输赔率不能为空");
			}
			
			Schedule schedule = new Schedule();
			BeanUtils.copyProperties(scheduleDto, schedule);
			
			if (scheduleBiz.createSchedule(schedule) != 1){
				throw new ServiceException("创建赛程失败");
			}
			
			return true;
		} catch (Exception e){
			logger.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}
	
	@Override
	public List<ScheduleDto> listScheduleDtoByMap(Map<String, Object> params) {
		
		List<ScheduleDto> scheduleDtoList = new ArrayList<>();
		
		List<Schedule> scheduleList = scheduleBiz.getScheduleByMap(params);
		if (!CollectionUtils.isEmpty(scheduleList)){
			for (Schedule schedule : scheduleList){
				
				ScheduleDto scheduleDto = new ScheduleDto();
				BeanUtils.copyProperties(schedule, scheduleDto);
				
				scheduleDtoList.add(scheduleDto);
			}
		}
		return scheduleDtoList;
	}

	@Override
	public Integer countScheduleDtoByMap(Map<String, Object> params) {
		return scheduleBiz.countScheduleByMap(params);
	}

	@Override
	public boolean updSchedule(ScheduleDto scheduleDto) throws ServiceException {
		
		if (scheduleDto == null){
			throw new ServiceException("赛程信息不能为空");
		}
		Schedule schedule = new Schedule();
		BeanUtils.copyProperties(scheduleDto, schedule);
		
		if (scheduleBiz.updSchedule(schedule) != 1){
			throw new ServiceException("赛程更新失败");
		}
		
		return true;
	}

	@Override
	public ScheduleDto getScheduleDtoById(Integer scheduleId) {
		
		ScheduleDto scheduleDto = null;
		
		Schedule schedule = scheduleBiz.getScheduleById(scheduleId);
		if (null != schedule){
			scheduleDto = new ScheduleDto();
			
			BeanUtils.copyProperties(schedule, scheduleDto);
		}
		
		return scheduleDto;
	}

	@Override
	public List<ScheduleDto> listScheduleInfoDtoByNormal(Integer memberId) {
		List<ScheduleDto> scheduleDtoList = new ArrayList<>();
		Member member = memberBiz.getMemberById(memberId);
		if  (member == null){
			return scheduleDtoList;
		}
		List<Schedule> scheduleList = scheduleBiz.getScheduleByNormal(member.getMemberCode());
		if (!CollectionUtils.isEmpty(scheduleList)){
			for (Schedule schedule : scheduleList){
				ScheduleDto scheduleDto = new ScheduleDto();
				BeanUtils.copyProperties(schedule, scheduleDto);
				
				scheduleDtoList.add(scheduleDto);
			}
		}
		return scheduleDtoList;
	}

	@Cacheable(value=CacheConst.CACHE_01)
	@Override
	public List<StakeDto> listStake() {
		List<StakeDto> dtoList = new ArrayList<>();
		List<Stake> list = stakeBiz.listStakeAll();
		
		if (!CollectionUtils.isEmpty(list)){
			for (Stake stake : list){
				StakeDto stakeDto = new StakeDto();
				BeanUtils.copyProperties(stake, stakeDto);
				
				dtoList.add(stakeDto);
			}
		}
		return dtoList;
	}

}
