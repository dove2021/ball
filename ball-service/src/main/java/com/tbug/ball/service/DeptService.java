package com.tbug.ball.service;

import java.util.List;
import java.util.Map;

import com.tbug.ball.service.Dto.TreeDto;
import com.tbug.ball.service.Dto.system.SysDeptDto;

/**
 * 部门管理
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-09-27 14:28:36
 */
public interface DeptService {
	
	SysDeptDto get(Integer deptId);
	
	List<SysDeptDto> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(SysDeptDto sysDept);
	
	int update(SysDeptDto sysDept);
	
	int remove(Integer deptId);
	
	int batchRemove(Integer[] deptIds);

	TreeDto<SysDeptDto> getTree();
}
