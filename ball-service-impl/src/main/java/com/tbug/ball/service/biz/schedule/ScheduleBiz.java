package com.tbug.ball.service.biz.schedule;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbug.ball.service.biz.BizException;
import com.tbug.ball.service.common.DBContants.ScheduleConst;
import com.tbug.ball.service.dao.schedule.ScheduleMapper;
import com.tbug.ball.service.dao.user.MemberMapper;
import com.tbug.ball.service.model.schedule.Schedule;
import com.tbug.ball.service.model.user.Member;

@Service
public class ScheduleBiz {

	@Autowired
	ScheduleMapper scheduleMapper;
	@Autowired
	MemberMapper memberMapper;
	
	public Integer createSchedule(Schedule schedule) throws BizException{
		
		Member member = memberMapper.selectByCode(schedule.getMemberCode());
		if (null == member){
			throw new BizException("会员单位不存在");
		}
		schedule.setMemberId(member.getId());
		schedule.setMemberName(member.getName());;
		schedule.setCreateDate(new Date());
		schedule.setGameResult(ScheduleConst.GAME_RESULT_0);
		schedule.setStatus(ScheduleConst.STATUS_NEW);
		
		return scheduleMapper.insertSelective(schedule);
	}
	
	public List<Schedule> getScheduleByMap(Map<String, Object> params){
		return scheduleMapper.listByMap(params);
	}
	
	public Integer countScheduleByMap(Map<String, Object> params){
		return scheduleMapper.countByMap(params);
	}
	
	public Schedule getScheduleById(Integer id){
		return scheduleMapper.selectByPrimaryKey(id);
	}
	
	public Integer updSchedule(Schedule schedule){
		return scheduleMapper.updateByPrimaryKeySelective(schedule);
	}
	
	public List<Schedule> getScheduleByNormal(String memberCode){
		Map<String, Object> params = new HashMap<>();
		params.put("memberCode", memberCode);
		params.put("status", "02");
		return scheduleMapper.listByMap(params);
	}
}
