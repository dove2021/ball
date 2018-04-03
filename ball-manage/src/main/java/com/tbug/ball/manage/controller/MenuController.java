package com.tbug.ball.manage.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tbug.ball.manage.controller.base.BaseController;
import com.tbug.ball.manage.util.R;
import com.tbug.ball.service.MenuService;
import com.tbug.ball.service.Dto.TreeDto;
import com.tbug.ball.service.Dto.system.SysMenuDto;

@Controller
@RequestMapping(MenuController.prefix)
public class MenuController extends BaseController {
	public static final String prefix = "sys/menu";
	
	@Autowired
	MenuService menuService;

	@GetMapping()
	String menu(Model model) {
		return prefix + "/menu";
	}

	@ResponseBody
	@RequestMapping("/list")
	List<SysMenuDto> list() {
		List<SysMenuDto> menus = menuService.list(new HashMap<>());
		return menus;
	}

	@GetMapping("/add/{pId}")
	String add(Model model, @PathVariable("pId") Integer pId) {
		model.addAttribute("pId", pId);
		if (pId == 0) {
			model.addAttribute("pName", "根目录");
		} else {
			model.addAttribute("pName", menuService.get(pId).getName());
		}
		return prefix + "/add";
	}

	@GetMapping("/edit/{id}")
	String edit(Model model, @PathVariable("id") Integer id) {
		model.addAttribute("menu", menuService.get(id));
		return prefix+"/edit";
	}

	@ResponseBody
	@PostMapping("/save")
	R save(SysMenuDto menu) {
		if (menuService.save(menu) > 0) {
			return R.ok();
		} else {
			return R.error(1, "保存失败");
		}
	}

	@PostMapping("/update")
	@ResponseBody
	R update(SysMenuDto menu) {
		if (menuService.update(menu) > 0) {
			return R.ok();
		} else {
			return R.error(1, "更新失败");
		}
	}

	@PostMapping("/remove")
	@ResponseBody
	R remove(Integer id) {
		if (menuService.remove(id) > 0) {
			return R.ok();
		} else {
			return R.error(1, "删除失败");
		}
	}

	@GetMapping("/tree")
	@ResponseBody
	TreeDto<SysMenuDto> tree() {
		TreeDto<SysMenuDto> tree = new TreeDto<>();
		tree = menuService.getTree();
		return tree;
	}

	@GetMapping("/tree/{roleId}")
	@ResponseBody
	TreeDto<SysMenuDto> tree(@PathVariable("roleId") Integer roleId) {
		TreeDto<SysMenuDto> tree = new TreeDto<>();
		tree = menuService.getTree(roleId);
		return tree;
	}
}
