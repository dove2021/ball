package com.tbug.ball.service.biz.system;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbug.ball.service.dao.system.SysUserRoleMapper;
import com.tbug.ball.service.model.system.SysUserRole;

@Service
public class SysUserRoleBiz {

	@Autowired
	SysUserRoleMapper sysUserRoleMapper;

	public SysUserRole get(Integer id){
		return sysUserRoleMapper.selectByPrimaryKey(id);
	}

	public List<SysUserRole> list(Map<String, Object> map){
		return sysUserRoleMapper.list(map);
	}

	public int count(Map<String, Object> map){
		return sysUserRoleMapper.count(map);
	}

	public int save(SysUserRole userRole){
		return sysUserRoleMapper.insertSelective(userRole);
	}

	public int update(SysUserRole userRole){
		return sysUserRoleMapper.updateByPrimaryKeySelective(userRole);
	}

	public int remove(Integer id){
		return sysUserRoleMapper.deleteByPrimaryKey(id);
	}

	public int batchRemove(Integer[] ids){
		int num = 0;
		for (Integer id : ids){
			num += sysUserRoleMapper.deleteByPrimaryKey(id);
		}
		return num;
	}

	public List<Integer> listRoleId(Integer userId){
		return sysUserRoleMapper.listRoleId(userId);
	}

	public int removeByUserId(Integer userId){
		return sysUserRoleMapper.removeByUserId(userId);
	}

	public int batchSave(List<SysUserRole> list){
		return sysUserRoleMapper.batchSave(list);
	}

	public int batchRemoveByUserId(Integer[] ids){
		return sysUserRoleMapper.batchRemoveByUserId(ids);
	}
	
}
