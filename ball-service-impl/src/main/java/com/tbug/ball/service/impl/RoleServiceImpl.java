package com.tbug.ball.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.tbug.ball.service.RoleService;
import com.tbug.ball.service.ServiceException;
import com.tbug.ball.service.Dto.system.SysRoleDto;
import com.tbug.ball.service.biz.system.SysRoleBiz;
import com.tbug.ball.service.biz.system.SysRoleMenuBiz;
import com.tbug.ball.service.biz.system.SysUserRoleBiz;
import com.tbug.ball.service.model.system.SysRole;
import com.tbug.ball.service.model.system.SysRoleMenu;
import com.tbug.ball.service.model.system.SysUserRole;

@Service
public class RoleServiceImpl implements RoleService {

	private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
	
	@Autowired
	SysRoleBiz sysRoleBiz;
	@Autowired
	SysRoleMenuBiz sysRoleMenuBiz;
	@Autowired
	SysUserRoleBiz sysUserRoleBiz;
	
	@Override
	public SysRoleDto get(Integer id) {
		SysRoleDto sysRoleDto = new SysRoleDto();
		SysRole sysRole = sysRoleBiz.get(id);
		if (null != sysRole){
			BeanUtils.copyProperties(sysRole, sysRoleDto);
		}
		return sysRoleDto;
	}

	@Override
	public List<SysRoleDto> list() {
		List<SysRoleDto> sysRoleDtoList = new ArrayList<>();
		List<SysRole> sysRoleList = sysRoleBiz.list(new HashMap<>(16));
		if (!CollectionUtils.isEmpty(sysRoleList)){
			for (SysRole sysRole : sysRoleList){
				SysRoleDto sysRoleDto = new SysRoleDto();
				BeanUtils.copyProperties(sysRole, sysRoleDto);
				
				sysRoleDtoList.add(sysRoleDto);
			}
		}
		return sysRoleDtoList;
	}

	@Override
	@Transactional(rollbackFor={ServiceException.class})
	public int save(SysRoleDto sysRoleDto) throws ServiceException {
		try {
			SysRole role = new SysRole();
			BeanUtils.copyProperties(sysRoleDto, role);
			role.setGmtCreate(new Date());
			role.setGmtModified(new Date());
			
	        int count = sysRoleBiz.save(role);
	        List<Integer> menuIds = role.getMenuIds();
	        Integer roleId = role.getRoleId();
	        List<SysRoleMenu> rms = new ArrayList<>();
	        for (Integer menuId : menuIds) {
	        	SysRoleMenu rmDo = new SysRoleMenu();
	            rmDo.setRoleId(roleId);
	            rmDo.setMenuId(menuId);
	            rms.add(rmDo);
	        }
	        sysRoleMenuBiz.removeByRoleId(roleId);
	        if (rms.size() > 0) {
	            sysRoleMenuBiz.batchSave(rms);
	        }
	        return count;			
			
		} catch (Exception e){
			logger.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public int update(SysRoleDto sysRoleDto) {
		SysRole role = new SysRole();
		BeanUtils.copyProperties(sysRoleDto, role);
		
        int r = sysRoleBiz.update(role);
        List<Integer> menuIds = role.getMenuIds();
        Integer roleId = role.getRoleId();
        sysRoleMenuBiz.removeByRoleId(roleId);
        List<SysRoleMenu> rms = new ArrayList<>();
        for (Integer menuId : menuIds) {
        	SysRoleMenu rmDo = new SysRoleMenu();
            rmDo.setRoleId(roleId);
            rmDo.setMenuId(menuId);
            rms.add(rmDo);
        }
        if (rms.size() > 0) {
            sysRoleMenuBiz.batchSave(rms);
        }
        return r;
	}

	@Override
	@Transactional
	public int remove(Integer id) {
        int count = sysRoleBiz.remove(id);
        sysRoleMenuBiz.removeByRoleId(id);
        return count;
	}

	@Override
	public List<SysRoleDto> list(Integer userId) {
		List<SysRoleDto> sysRoleDtoList = new ArrayList<>();
        List<SysRole> roles = sysRoleBiz.list(new HashMap<>(16));
        List<Integer> roleIdList = sysUserRoleBiz.listRoleId(userId);
        if (!CollectionUtils.isEmpty(roles)){
        	for (SysRole sysRole : roles){
        		SysRoleDto sysRoleDto = new SysRoleDto();
        		BeanUtils.copyProperties(sysRole, sysRoleDto);
        		
        		for (Integer roleId : roleIdList){
                    if (Objects.equals(sysRole.getRoleId(), roleId)) {
                    	sysRoleDto.setRoleSign("true");
                        break;
                    }       			
        		}
        		sysRoleDtoList.add(sysRoleDto);
        	}
        }
        return sysRoleDtoList;
	}

	@Override
	public int batchremove(Integer[] ids) {
        int r = sysRoleBiz.batchRemove(ids);
        return r;
	}

	@Override
	@Transactional(rollbackFor={ServiceException.class})
	public boolean reRole(Integer id, Integer[] roles) throws ServiceException {
		try {
			sysUserRoleBiz.removeByUserId(id);
			
			for (Integer role : roles){
				SysUserRole userRole = new SysUserRole();
				userRole.setUserId(id);
				userRole.setRoleId(role);
				sysUserRoleBiz.save(userRole);
			}
		} catch (Exception e){
			logger.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage());
		}
		return true;
	}

}
