package com.tbug.ball.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.tbug.ball.service.DeptService;
import com.tbug.ball.service.Dto.TreeDto;
import com.tbug.ball.service.Dto.system.SysDeptDto;
import com.tbug.ball.service.biz.system.SysDeptBiz;
import com.tbug.ball.service.model.system.SysDept;
import com.tbug.ball.service.utils.BuildTree;

@Service
public class DeptServiceImpl implements DeptService {

	@Autowired
	SysDeptBiz sysDeptBiz;
	
	@Override
	public SysDeptDto get(Integer deptId) {
		SysDeptDto sysDeptDto = new SysDeptDto();
		
		SysDept sysDept = sysDeptBiz.get(deptId);
		if (null != sysDept){
			BeanUtils.copyProperties(sysDept, sysDeptDto);
		}
		return sysDeptDto;
	}

	@Override
	public List<SysDeptDto> list(Map<String, Object> map) {
		List<SysDeptDto> sysDeptDtoList = new ArrayList<>();
		
		List<SysDept> sysDeptList = sysDeptBiz.list(map);
		if (!CollectionUtils.isEmpty(sysDeptList)){
			for (SysDept sysDept : sysDeptList){
				SysDeptDto sysDeptDto = new SysDeptDto();
				BeanUtils.copyProperties(sysDept, sysDeptDto);
				
				sysDeptDtoList.add(sysDeptDto);
			}
		}
		return sysDeptDtoList;
	}

	@Override
	public int count(Map<String, Object> map) {
		return sysDeptBiz.count(map);
	}

	@Override
	public int save(SysDeptDto sysDeptDto) {
		SysDept sysDept = new SysDept();
		BeanUtils.copyProperties(sysDeptDto, sysDept);
		
		return sysDeptBiz.save(sysDept);
	}

	@Override
	public int update(SysDeptDto sysDeptDto) {
		SysDept sysDept = new SysDept();
		BeanUtils.copyProperties(sysDeptDto, sysDept);
		
		return sysDeptBiz.update(sysDept);
	}

	@Override
	public int remove(Integer deptId) {
		return sysDeptBiz.remove(deptId);
	}

	@Override
	public int batchRemove(Integer[] deptIds) {
		return sysDeptBiz.batchRemove(deptIds);
	}

	@Override
	public TreeDto<SysDeptDto> getTree() {
		List<TreeDto<SysDeptDto>> trees = new ArrayList<>();
		List<SysDept> sysDepts = sysDeptBiz.list(new HashMap<String,Object>(16));
		for (SysDept sysDept : sysDepts) {
			TreeDto<SysDeptDto> tree = new TreeDto<SysDeptDto>();
			tree.setId(sysDept.getDeptId().toString());
			tree.setParentId(sysDept.getParentId().toString());
			tree.setText(sysDept.getName());
			Map<String, Object> state = new HashMap<>(16);
			state.put("opened", true);
			tree.setState(state);
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		TreeDto<SysDeptDto> t = BuildTree.build(trees);
		return t;
	}

}
