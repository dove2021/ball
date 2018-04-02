package com.tbug.ball.service.biz.system;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbug.ball.service.dao.system.SysDeptMapper;
import com.tbug.ball.service.model.system.SysDept;

@Service
public class SysDeptBiz {

	@Autowired
	SysDeptMapper sysDeptMapper;
	
	public SysDept get(Integer deptId){
		return sysDeptMapper.selectByPrimaryKey(deptId);
	}
	
	public List<SysDept> list(Map<String,Object> map){
		return sysDeptMapper.list(map);
	}
	
	public int count(Map<String,Object> map){
		return sysDeptMapper.count(map);
	}
	
	public int save(SysDept dept){
		return sysDeptMapper.insertSelective(dept);
	}
	
	public int update(SysDept dept){
		return sysDeptMapper.updateByPrimaryKeySelective(dept);
	}
	
	public int remove(Integer deptId){
		return sysDeptMapper.deleteByPrimaryKey(deptId);
	}
	
	public int batchRemove(Integer[] deptIds){
		int num = 0;
		
		for (Integer id : deptIds){
			num += sysDeptMapper.deleteByPrimaryKey(id);
		}
		return num;
	}
	
	public Integer[] listParentDept(){
		return sysDeptMapper.listParentDept();
	}
}

