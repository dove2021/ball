package com.tbug.ball.service.dao.system;

import java.util.List;
import java.util.Map;

import com.tbug.ball.service.model.system.SysRoleMenu;

public interface SysRoleMenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRoleMenu record);

    int insertSelective(SysRoleMenu record);

    SysRoleMenu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRoleMenu record);

    int updateByPrimaryKey(SysRoleMenu record);
    
	List<SysRoleMenu> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	int remove(Integer id);
	
	int batchRemove(Integer[] ids);
	
	List<Integer> listMenuIdByRoleId(Integer roleId);
	
	int removeByRoleId(Integer roleId);
	
	int batchSave(List<SysRoleMenu> list);
}