package com.tbug.ball.service.dao.system;

import java.util.List;
import java.util.Map;

import com.tbug.ball.service.model.system.SysMenu;

public interface SysMenuMapper {
    int deleteByPrimaryKey(Integer menuId);

    int insert(SysMenu record);

    int insertSelective(SysMenu record);

    SysMenu selectByPrimaryKey(Integer menuId);

    int updateByPrimaryKeySelective(SysMenu record);

    int updateByPrimaryKey(SysMenu record);
    
	List<SysMenu> list(Map<String,Object> map);
	
	int count(Map<String,Object> map);
	
	List<SysMenu> listMenuByUserId(Integer userId);
	
	List<String> listUserPerms(Integer userId);
}