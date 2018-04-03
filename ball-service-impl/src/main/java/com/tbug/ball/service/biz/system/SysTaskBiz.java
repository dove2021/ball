package com.tbug.ball.service.biz.system;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbug.ball.service.dao.system.SysTaskMapper;
import com.tbug.ball.service.model.system.SysTask;

@Service
public class SysTaskBiz {

	@Autowired
	SysTaskMapper sysTaskMapper;
	
	public SysTask getSysTaskById(Long id){
		return sysTaskMapper.selectByPrimaryKey(id);
	}
	
	public List<SysTask> listSysTaskByMap(Map<String, Object> params){
		return sysTaskMapper.listByMap(params);
	}
	
}
