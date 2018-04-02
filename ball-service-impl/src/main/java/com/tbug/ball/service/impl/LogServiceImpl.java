package com.tbug.ball.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.tbug.ball.service.LogService;
import com.tbug.ball.service.Dto.system.SysLogDto;
import com.tbug.ball.service.biz.system.SysLogBiz;
import com.tbug.ball.service.model.system.SysLog;

@Service
public class LogServiceImpl implements LogService {

	@Autowired
	SysLogBiz sysLogBiz;

	@Override
	public int remove(Long id) {
		int count = sysLogBiz.remove(id);
		return count;
	}

	@Override
	public int batchRemove(Long[] ids) {
		return sysLogBiz.batchRemove(ids);
	}

	@Override
	public List<SysLogDto> list(Map<String, Object> params) {
		List<SysLogDto> sysLogDtoList = new ArrayList<>();
		
		List<SysLog> logs = sysLogBiz.listByMap(params);
		if (!CollectionUtils.isEmpty(logs)){
			for (SysLog sysLog : logs){
				SysLogDto sysLogDto = new SysLogDto();
				BeanUtils.copyProperties(sysLog, sysLogDto);
				
				sysLogDtoList.add(sysLogDto);
			}
		}
		return sysLogDtoList;
	}

	@Override
	public Integer count(Map<String, Object> params) {
		return sysLogBiz.countByMap(params);
	}

	@Override
	public void save(SysLogDto sysLogDto) {
		
		SysLog sysLog = new SysLog();
		BeanUtils.copyProperties(sysLogDto, sysLog);
		
		sysLogBiz.save(sysLog);
	}

}
