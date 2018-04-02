package com.tbug.ball.service;

import java.util.List;
import java.util.Map;

import com.tbug.ball.service.Dto.system.SysLogDto;

public interface LogService {

	List<SysLogDto> list(Map<String, Object> params);
	
	Integer count(Map<String, Object> params);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
	
	void save(SysLogDto sysLogDto);
}
