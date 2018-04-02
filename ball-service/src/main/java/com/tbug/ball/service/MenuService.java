package com.tbug.ball.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tbug.ball.service.Dto.TreeDto;
import com.tbug.ball.service.Dto.system.SysMenuDto;

public interface MenuService {
	TreeDto<SysMenuDto> getSysMenuTree(Integer id);

	List<TreeDto<SysMenuDto>> listMenuTree(Integer id);

	TreeDto<SysMenuDto> getTree();

	TreeDto<SysMenuDto> getTree(Integer id);

	List<SysMenuDto> list(Map<String, Object> params);

	int remove(Integer id);

	int save(SysMenuDto menu);

	int update(SysMenuDto menu);

	SysMenuDto get(Integer id);

	Set<String> listPerms(Integer userId);
}
