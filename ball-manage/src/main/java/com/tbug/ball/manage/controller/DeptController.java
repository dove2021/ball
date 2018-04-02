package com.tbug.ball.manage.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.tbug.ball.manage.controller.base.BaseController;
import com.tbug.ball.manage.util.R;
import com.tbug.ball.service.DeptService;
import com.tbug.ball.service.Dto.TreeDto;
import com.tbug.ball.service.Dto.system.SysDeptDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门管理
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-09-27 14:40:36
 */

@Controller
@RequestMapping(DeptController.prefix)
public class DeptController extends BaseController {
	public static final String prefix = "/sys/dept";
	
	@Autowired
	DeptService sysDeptService;

	@GetMapping()
	@RequiresPermissions("sys:dept")
	String dept() {
		return prefix + "/dept";
	}

	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("sys:dept:dept")
	public List<SysDeptDto> list() {
		Map<String, Object> query = new HashMap<>(16);
		List<SysDeptDto> sysDeptList = sysDeptService.list(query);
		return sysDeptList;
	}

	@GetMapping("/add/{pId}")
	@RequiresPermissions("sys:dept:add")
	String add(@PathVariable("pId") Integer pId, Model model) {
		model.addAttribute("pId", pId);
		if (pId == 0) {
			model.addAttribute("pName", "总部门");
		} else {
			model.addAttribute("pName", sysDeptService.get(pId).getName());
		}
		return  prefix + "/add";
	}

	@GetMapping("/edit/{deptId}")
	@RequiresPermissions("sys:dept:edit")
	String edit(@PathVariable("deptId") Integer deptId, Model model) {
		SysDeptDto sysDept = sysDeptService.get(deptId);
		model.addAttribute("sysDept", sysDept);
		return  prefix + "/edit";
	}

	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("sys:dept:add")
	public R save(SysDeptDto sysDept) {
		if (sysDeptService.save(sysDept) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("sys:dept:edit")
	public R update(SysDeptDto sysDept) {
		if (sysDeptService.update(sysDept) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ResponseBody
	@RequiresPermissions("sys:dept:remove")
	public R remove(Integer deptId) {
		if (sysDeptService.remove(deptId) > 0) {
			return R.ok();
		}
		return R.error();
	}

	/**
	 * 删除
	 */
	@PostMapping("/batchRemove")
	@ResponseBody
	@RequiresPermissions("sys:dept:remove")
	public R remove(@RequestParam("ids[]") Integer[] deptIds) {
		sysDeptService.batchRemove(deptIds);
		return R.ok();
	}

	@GetMapping("/tree")
	@ResponseBody
	public TreeDto<SysDeptDto> tree() {
		TreeDto<SysDeptDto> tree = new TreeDto<SysDeptDto>();
		tree = sysDeptService.getTree();
		return tree;
	}

	@GetMapping("/treeView")
	String treeView() {
		return  prefix + "/deptTree";
	}

}
