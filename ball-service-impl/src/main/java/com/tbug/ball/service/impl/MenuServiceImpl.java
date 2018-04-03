package com.tbug.ball.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.tbug.ball.service.MenuService;
import com.tbug.ball.service.Dto.TreeDto;
import com.tbug.ball.service.Dto.system.SysMenuDto;
import com.tbug.ball.service.biz.system.SysMenuBiz;
import com.tbug.ball.service.biz.system.SysRoleMenuBiz;
import com.tbug.ball.service.model.system.SysMenu;
import com.tbug.ball.service.utils.BuildTree;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	SysMenuBiz sysMenuBiz;
	@Autowired
	SysRoleMenuBiz sysRoleMenuBiz;
	
	@Override
	public TreeDto<SysMenuDto> getSysMenuTree(Integer id) {
		List<TreeDto<SysMenuDto>> trees = new ArrayList<>();
		List<SysMenu> menuList = sysMenuBiz.listMenuByUserId(id);
		if (!CollectionUtils.isEmpty(menuList)){
			for (SysMenu sysMenu : menuList){
				TreeDto<SysMenuDto> tree = new TreeDto<SysMenuDto>();
				tree.setId(sysMenu.getMenuId().toString());
				tree.setParentId(sysMenu.getParentId().toString());
				tree.setText(sysMenu.getName());
				Map<String, Object> attributes = new HashMap<>(16);
				attributes.put("url", sysMenu.getUrl());
				attributes.put("icon", sysMenu.getIcon());
				tree.setAttributes(attributes);
				trees.add(tree);
			}
		}
		
		// 默认顶级菜单为０，根据数据库实际情况调整
		TreeDto<SysMenuDto> t = BuildTree.build(trees);
		
		return t;
	}

	@Override
	public List<TreeDto<SysMenuDto>> listMenuTree(Integer id) {
		List<TreeDto<SysMenuDto>> trees = new ArrayList<>();
		List<SysMenu> menuDOs = sysMenuBiz.listMenuByUserId(id);
		if (!CollectionUtils.isEmpty(menuDOs)){
			for (SysMenu sysMenuDO : menuDOs) {
				TreeDto<SysMenuDto> tree = new TreeDto<SysMenuDto>();
				tree.setId(sysMenuDO.getMenuId().toString());
				tree.setParentId(sysMenuDO.getParentId().toString());
				tree.setText(sysMenuDO.getName());
				Map<String, Object> attributes = new HashMap<>(16);
				attributes.put("url", sysMenuDO.getUrl());
				attributes.put("icon", sysMenuDO.getIcon());
				tree.setAttributes(attributes);
				trees.add(tree);
			}
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		List<TreeDto<SysMenuDto>> list = BuildTree.buildList(trees, "0");
		return list;
	}

	@Override
	public TreeDto<SysMenuDto> getTree() {
		List<TreeDto<SysMenuDto>> trees = new ArrayList<>();
		List<SysMenu> menuDOs = sysMenuBiz.listSysMenuByMap(new HashMap<>(16));
		if (!CollectionUtils.isEmpty(menuDOs)){
			for (SysMenu sysMenuDO : menuDOs) {
				TreeDto<SysMenuDto> tree = new TreeDto<>();
				tree.setId(sysMenuDO.getMenuId().toString());
				tree.setParentId(sysMenuDO.getParentId().toString());
				tree.setText(sysMenuDO.getName());
				trees.add(tree);
			}
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		TreeDto<SysMenuDto> t = BuildTree.build(trees);
		return t;
	}

	@Override
	public TreeDto<SysMenuDto> getTree(Integer id) {
		// 根据roleId查询权限
		List<SysMenu> menus = sysMenuBiz.listSysMenuByMap(new HashMap<String, Object>(16));
		List<Integer> menuIds = sysRoleMenuBiz.listMenuIdByRoleId(id);
		List<Integer> temp = menuIds;
		if (!CollectionUtils.isEmpty(menus)){
			for (SysMenu menu : menus) {
				if (temp.contains(menu.getParentId())) {
					menuIds.remove(menu.getParentId());
				}
			}
		}
		List<TreeDto<SysMenuDto>> trees = new ArrayList<>();
		List<SysMenu> menuDOs = sysMenuBiz.listSysMenuByMap(new HashMap<String, Object>(16));
		for (SysMenu sysMenuDO : menuDOs) {
			TreeDto<SysMenuDto> tree = new TreeDto<>();
			tree.setId(sysMenuDO.getMenuId().toString());
			tree.setParentId(sysMenuDO.getParentId().toString());
			tree.setText(sysMenuDO.getName());
			Map<String, Object> state = new HashMap<>(16);
			Integer menuId = sysMenuDO.getMenuId();
			if (menuIds.contains(menuId)) {
				state.put("selected", true);
			} else {
				state.put("selected", false);
			}
			tree.setState(state);
			trees.add(tree);
		}
		// 默认顶级菜单为０，根据数据库实际情况调整
		TreeDto<SysMenuDto> t = BuildTree.build(trees);
		return t;
	}

	@Override
	public List<SysMenuDto> list(Map<String, Object> params) {
		
		List<SysMenuDto> sysMenuDtoList = new ArrayList<>();
		
		List<SysMenu> sysMenuList = sysMenuBiz.listSysMenuByMap(params);
		if (!CollectionUtils.isEmpty(sysMenuList)){
			for (SysMenu sysMenu : sysMenuList){
				SysMenuDto sysMenuDto = new SysMenuDto();
				BeanUtils.copyProperties(sysMenu, sysMenuDto);
				
				sysMenuDtoList.add(sysMenuDto);
			}
		}
		
		return sysMenuDtoList;
	}

	@Override
	public int remove(Integer id) {
		return sysMenuBiz.remove(id);
	}

	@Override
	public int save(SysMenuDto menu) {
		SysMenu sysMenu = new SysMenu();
		BeanUtils.copyProperties(menu, sysMenu);
		return sysMenuBiz.createSysMenu(sysMenu);
	}

	@Override
	public int update(SysMenuDto menu) {
		SysMenu sysMenu = new SysMenu();
		BeanUtils.copyProperties(menu, sysMenu);
		return sysMenuBiz.updateSysMenu(sysMenu);
	}

	@Override
	public SysMenuDto get(Integer id) {
		SysMenuDto sysMenuDto = new SysMenuDto();
		SysMenu sysMenu = sysMenuBiz.getSysMenuById(id);
		BeanUtils.copyProperties(sysMenu, sysMenuDto);
		
		return sysMenuDto;
	}

	@Override
	public Set<String> listPerms(Integer userId) {
		Set<String> permsSet = new HashSet<>();
		
		List<String> permList = sysMenuBiz.listPerms(userId);
		if (!CollectionUtils.isEmpty(permList)){
			for (String perm : permList){
				if (StringUtils.isNotBlank(perm)){
					permsSet.addAll(Arrays.asList(perm.trim().split(",")));
				}
			}
		}
		return permsSet;
	}

}
