package com.tbug.ball.service.biz.system;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbug.ball.service.dao.system.SysLogMapper;
import com.tbug.ball.service.model.system.SysLog;

@Service
public class SysLogBiz {

	@Autowired
	SysLogMapper sysLogMapper;
	
	public SysLog getById(Long id){
		return sysLogMapper.selectByPrimaryKey(id);
	}
	
	public Integer save(SysLog sysLog){
		return sysLogMapper.insertSelective(sysLog);
	}
	
	public Integer update(SysLog sysLog){
		return sysLogMapper.updateByPrimaryKeySelective(sysLog);
	}
	
	public Integer remove(Long id){
		return sysLogMapper.deleteByPrimaryKey(id);
	}
	
	public Integer batchRemove(Long[] ids){
		int num = 0;
		
		if (ids != null && ids.length > 0){
			for (Long id : ids){
				num += sysLogMapper.deleteByPrimaryKey(id);
			}
		}
		return num;
	}
	
	public List<SysLog> listByMap(Map<String,Object> map){
		return sysLogMapper.list(map);
	}
	
	public Integer countByMap(Map<String,Object> map){
		return sysLogMapper.count(map);
	}
	
}
