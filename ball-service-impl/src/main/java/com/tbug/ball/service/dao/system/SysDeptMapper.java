package com.tbug.ball.service.dao.system;

import java.util.List;
import java.util.Map;

import com.tbug.ball.service.model.system.SysDept;

public interface SysDeptMapper {
    int deleteByPrimaryKey(Integer deptId);

    int insert(SysDept record);

    int insertSelective(SysDept record);

    SysDept selectByPrimaryKey(Integer deptId);

    int updateByPrimaryKeySelective(SysDept record);

    int updateByPrimaryKey(SysDept record);
    
	List<SysDept> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	Integer[] listParentDept();    
}