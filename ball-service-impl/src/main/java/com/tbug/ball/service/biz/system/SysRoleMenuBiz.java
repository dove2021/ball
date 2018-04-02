package com.tbug.ball.service.biz.system;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbug.ball.service.dao.system.SysRoleMenuMapper;
import com.tbug.ball.service.model.system.SysRoleMenu;

@Service
public class SysRoleMenuBiz {

	@Autowired
	SysRoleMenuMapper sysRoleMenuMapper;
	
	public SysRoleMenu get(Integer id){
		return sysRoleMenuMapper.selectByPrimaryKey(id);
	}
	
	public List<SysRoleMenu> list(Map<String,Object> map){
		return sysRoleMenuMapper.list(map);
	}
	
	public int count(Map<String,Object> map){
		return sysRoleMenuMapper.count(map);
	}
	
	public int save(SysRoleMenu roleMenu){
		return sysRoleMenuMapper.insertSelective(roleMenu);
	}
	
	public int update(SysRoleMenu roleMenu){
		return sysRoleMenuMapper.updateByPrimaryKeySelective(roleMenu);
	}
	
	public int remove(Integer id){
		return sysRoleMenuMapper.remove(id);
	}
	
	public int batchRemove(Integer[] ids){
		return sysRoleMenuMapper.batchRemove(ids);
	}
	
	public List<Integer> listMenuIdByRoleId(Integer roleId){
		return sysRoleMenuMapper.listMenuIdByRoleId(roleId);
	}
	
	public int removeByRoleId(Integer roleId){
		return sysRoleMenuMapper.removeByRoleId(roleId);
	}
	
	public int batchSave(List<SysRoleMenu> list){
		return sysRoleMenuMapper.batchSave(list);
	}
	
}
