package com.tbug.ball.service;

import java.util.List;

import com.tbug.ball.service.Dto.system.SysRoleDto;

public interface RoleService {

	SysRoleDto get(Integer id);

	List<SysRoleDto> list();

	int save(SysRoleDto role) throws ServiceException;

	int update(SysRoleDto role);

	int remove(Integer id);

	List<SysRoleDto> list(Integer userId);

	int batchremove(Integer[] ids);
	
	boolean reRole(Integer id, Integer[] roles) throws ServiceException;
}
