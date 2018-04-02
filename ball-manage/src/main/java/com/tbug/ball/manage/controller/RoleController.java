package com.tbug.ball.manage.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.tbug.ball.manage.controller.base.BaseController;
import com.tbug.ball.manage.util.R;
import com.tbug.ball.service.RoleService;
import com.tbug.ball.service.ServiceException;
import com.tbug.ball.service.Dto.system.SysRoleDto;

import java.util.List;

@RequestMapping(RoleController.prefix)
@Controller
public class RoleController extends BaseController {
	public static final String prefix = "/sys/role";
	
	@Autowired
	RoleService roleService;

	@GetMapping()
	String role() {
		return prefix + "/role";
	}

	@RequiresPermissions("sys:role:role")
	@GetMapping("/list")
	@ResponseBody()
	List<SysRoleDto> list() {
		List<SysRoleDto> roles = roleService.list();
		return roles;
	}

	@RequiresPermissions("sys:role:add")
	@GetMapping("/add")
	String add() {
		return prefix + "/add";
	}

	@GetMapping("/edit/{id}")
	String edit(@PathVariable("id") Integer id, Model model) {
		SysRoleDto roleDO = roleService.get(id);
		model.addAttribute("role", roleDO);
		return prefix + "/edit";
	}

	@PostMapping("/save")
	@ResponseBody()
	R save(SysRoleDto role) {
		try {
			role.setUserIdCreate(getCurrentUser().getId().longValue());
			if (roleService.save(role) > 0) {
				return R.ok();
			} else {
				return R.error(1, "保存失败");
			}
		} catch (ServiceException e) {
			logger.error(e.getMessage(), e);
			return R.error(e.getMessage());
		}
	}

	@PostMapping("/update")
	@ResponseBody()
	R update(SysRoleDto role) {
		if (roleService.update(role) > 0) {
			return R.ok();
		} else {
			return R.error(1, "保存失败");
		}
	}

	@RequiresPermissions("sys:role:remove")
	@PostMapping("/remove")
	@ResponseBody()
	R save(Integer id) {
		if (roleService.remove(id) > 0) {
			return R.ok();
		} else {
			return R.error(1, "删除失败");
		}
	}
	
	@RequiresPermissions("sys:role:batchRemove")
	@PostMapping("/batchRemove")
	@ResponseBody
	R batchRemove(@RequestParam("ids[]") Integer[] ids) {
		int r = roleService.batchremove(ids);
		if (r > 0) {
			return R.ok();
		}
		return R.error();
	}
}
