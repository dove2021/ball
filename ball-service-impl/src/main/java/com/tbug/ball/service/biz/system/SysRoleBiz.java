package com.tbug.ball.service.biz.system;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbug.ball.service.dao.system.SysRoleMapper;
import com.tbug.ball.service.model.system.SysRole;

@Service
public class SysRoleBiz {

	@Autowired
	SysRoleMapper sysRoleMapper;
	
	public SysRole get(Integer roleId){
		return sysRoleMapper.selectByPrimaryKey(roleId);
	}
	
	public List<SysRole> list(Map<String,Object> map){
		return sysRoleMapper.list(map);
	}
	
	public int count(Map<String,Object> map){
		return sysRoleMapper.count(map);
	}
	
	public int save(SysRole role){
		return sysRoleMapper.insertSelective(role);
	}
	
	public int update(SysRole role){
		return sysRoleMapper.updateByPrimaryKeySelective(role);
	}
	
	public int remove(Integer roleId){
		return sysRoleMapper.deleteByPrimaryKey(roleId);
	}
	
	public int batchRemove(Integer[] roleIds){
		int num = 0;
		for (Integer roleId : roleIds){
			num += sysRoleMapper.deleteByPrimaryKey(roleId);
		}
		return num;
	}
	
}
