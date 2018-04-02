package com.tbug.ball.service.biz.system;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbug.ball.service.dao.system.SysMenuMapper;
import com.tbug.ball.service.model.system.SysMenu;

@Service
public class SysMenuBiz {

	@Autowired
	SysMenuMapper sysMenuMapper;
	
	public int createSysMenu(SysMenu sysMenu){
		return sysMenuMapper.insertSelective(sysMenu);
	}
	
	public SysMenu getSysMenuById(Integer menuId){
		return sysMenuMapper.selectByPrimaryKey(menuId);
	}
	
	public Integer updateSysMenu(SysMenu sysMenu){
		return sysMenuMapper.updateByPrimaryKeySelective(sysMenu);
	}
	
	public List<SysMenu> listSysMenuByMap(Map<String, Object> params){
		return sysMenuMapper.list(params);
	}
	
	public Integer countSysMenuByMap(Map<String, Object> params){
		return sysMenuMapper.count(params);
	}
	
	public List<String> listPerms(Integer userId){
		return sysMenuMapper.listUserPerms(userId);
	}
	
	public List<SysMenu> listMenuByUserId(Integer userId){
		return sysMenuMapper.listMenuByUserId(userId);
	}
	
	public int remove(Integer id){
		return sysMenuMapper.deleteByPrimaryKey(id);
	}
	
}
