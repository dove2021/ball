package com.tbug.ball.service.dao.system;

import java.util.List;
import java.util.Map;

import com.tbug.ball.service.model.system.SysUserRole;

public interface SysUserRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);

    SysUserRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUserRole record);

    int updateByPrimaryKey(SysUserRole record);
    
    List<SysUserRole> list(Map<String, Object> prams);
    
    Integer count(Map<String, Object> prams);
    
	List<Integer> listRoleId(Integer userId);

	int removeByUserId(Integer userId);

	int batchSave(List<SysUserRole> list);

	int batchRemoveByUserId(Integer[] ids);
}