package com.tbug.ball.manage.controller;

import java.util.Collection;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tbug.ball.manage.common.annotation.Log;
import com.tbug.ball.manage.controller.base.BaseController;
import com.tbug.ball.manage.service.SessionService;
import com.tbug.ball.manage.util.R;
import com.tbug.ball.manage.vo.UserOnline;

@Controller
@RequestMapping(SessionController.prefix)
public class SessionController extends BaseController{

	public static final String prefix = "sys/online";
	
	@Autowired
	SessionService sessionService;

	@GetMapping()
	public String online() {
		return prefix + "/online";
	}

	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("sys:online:online")
	public List<UserOnline> list() {
		return sessionService.list();
	}

	@Log("强制下线")
	@ResponseBody
	@RequestMapping("/forceLogout/{sessionId}")
	@RequiresPermissions("sys:online:off")
	public R forceLogout(@PathVariable("sessionId") String sessionId, RedirectAttributes redirectAttributes) {
		try {
			sessionService.forceLogout(sessionId);
			return R.ok("已强制用户下线");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return R.error("操作失败");
		}
	}

	@ResponseBody
	@RequestMapping("/sessionList")
	@RequiresPermissions("sys:online:online")
	public Collection<Session> sessionList() {
		return sessionService.sessionList();
	}
}
