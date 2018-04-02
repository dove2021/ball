package com.tbug.ball.manage.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.reger.dubbo.annotation.Inject;
import com.tbug.ball.manage.common.annotation.Log;
import com.tbug.ball.manage.controller.base.BaseController;
import com.tbug.ball.manage.util.R;
import com.tbug.ball.service.AccountService;
import com.tbug.ball.service.MemberService;
import com.tbug.ball.service.Dto.user.MemberDto;
import com.tbug.ball.service.common.DBContants.MemberConst;

@Controller
@RequestMapping(MemberController.prefix)
public class MemberController extends BaseController{

	public static final String prefix = "/user/member";
	
	@Autowired
	MemberService memberService;
	@Inject
	AccountService accountService;
	
	@GetMapping("")
	String member(){
		return prefix + "/member";
	}
	
	@ResponseBody
	@GetMapping("/list")
	List<MemberDto> list(HttpServletRequest request){
		String memberCode = request.getParameter("memberCode");
		Map<String, Object> params = new HashMap<>();
		if (!StringUtils.isEmpty(memberCode)){
			params.put("memberCode", memberCode);
		}
		List<MemberDto> memberDtoList = memberService.listMemberDtoByMap(params);
	
		return memberDtoList;
	}
	
	@GetMapping("/add")
	String add(){
		return prefix + "/add";
	}
	
	@ResponseBody
	@PostMapping("save")
	R save(MemberDto memberDto){
		try {
			memberService.createMember(getCurrentUser().getLoginName(), 
					memberDto.getMemberCode(), 
					memberDto.getName(), 
					memberDto.getLinkman(), 
					memberDto.getPhoneNo(), 
					memberDto.getLoginName(), memberDto.getPassword(), memberDto.getContent());
		} catch (Exception e) {
			logger.error(e.getMessage());
			return R.error(e.getMessage());
		}
		
		return R.ok();
	}
	
	@GetMapping("/edit/{id}")
	String edit(@PathVariable("id") Integer id, Model model){
		MemberDto memberDto = memberService.getMemberDtoById(id);
		model.addAttribute("memberDto", memberDto);
		return prefix + "/repwd";
	}
	
	@GetMapping("/repwd/{id}")
	String repwd(@PathVariable("id") Integer id, Model model){
		MemberDto memberDto = memberService.getMemberDtoById(id);
		model.addAttribute("memberDto", memberDto);
		return prefix + "/repwd";
	}
	
	@ResponseBody
	@PostMapping("/repwd")
	@RequiresPermissions("user:member:repwd")
	R repwdPost(HttpServletRequest request){
		String id = request.getParameter("id");
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");
		if (StringUtils.isEmpty(id)){
			return R.error("会员id不存在");
		}
		if (StringUtils.isEmpty(password1)){
			return R.error("先密码不能为空");
		}
		if (StringUtils.isEmpty(id)){
			return R.error("确认密码不能为空");
		}
		if (!password1.equals(password2)){
			return R.error("新密码与确认密码不一致");
		}
		try {
			if (memberService.memberRePwd(Integer.valueOf(id), "", password1)){
				return R.ok();
			}
			return R.error("更新失败");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return R.error(e.getMessage());
		}
	}
	
	@ResponseBody
	@PostMapping("/update")
	R update(MemberDto memberDto){
		try {
			if (memberService.updMemberDto(memberDto)){
				return R.ok();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return R.error(e.getMessage());
		}
		
		return R.ok();
	}
	
	@ResponseBody
	@GetMapping("/freeze/{id}")
	R freeze(@PathVariable("id") Integer id){
		
		MemberDto memberDto = new MemberDto();
		memberDto.setId(Integer.valueOf(id));
		memberDto.setStatus(MemberConst.STATUS_FREEZE);
		try {
			if (memberService.updMemberDto(memberDto));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return R.error(e.getMessage());
		}
		
		return R.ok();
	}
	
	@ResponseBody
	@PostMapping("/batch/freeze")
	R batchFreeze(@RequestParam("ids[]") Integer[] ids){
		if (null == ids || ids.length < 1){
			return R.error("选择数据为空");
		}
		
		int num = 0;
		for (Integer id : ids){
			MemberDto memberDto = new MemberDto();
			memberDto.setId(id);
			memberDto.setStatus(MemberConst.STATUS_FREEZE);
			try {
				if (memberService.updMemberDto(memberDto)) num++;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return R.ok("已冻结 " + num + " 个用户");
	}
	
	@ResponseBody
	@PostMapping("/batch/normal")
	R batchNormal(@RequestParam("ids[]") Integer[] ids){
		if (null == ids || ids.length < 1){
			return R.error("选择数据为空");
		}
		
		int num = 0;
		for (Integer id : ids){
			MemberDto memberDto = new MemberDto();
			memberDto.setId(id);
			memberDto.setStatus(MemberConst.STATUS_NORMAL);
			try {
				if (memberService.updMemberDto(memberDto)) num++;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return R.ok("已恢复 " + num + " 个用户");
	}
	
	@ResponseBody
	@GetMapping("/exist")
	boolean exist(HttpServletRequest request){
		String memberCode = request.getParameter("memberCode");
		Map<String, Object> params = new HashMap<>();
		params.put("memberCode", memberCode);
		List<MemberDto> memberDtoList = memberService.listMemberDtoByMap(params);
		
		if (memberDtoList == null || memberDtoList.size() <= 0){
			return true;
		}
		
		return false;
	}
	
	@ResponseBody
	@GetMapping("/exist/loginName")
	boolean existloginName(HttpServletRequest request){
		String loginName = request.getParameter("loginName");
		Map<String, Object> params = new HashMap<>();
		params.put("loginName", loginName);
		List<MemberDto> memberDtoList = memberService.listMemberDtoByMap(params);
		
		if (memberDtoList == null || memberDtoList.size() <= 0){
			return true;
		}
		
		return false;
	}
	
	@Log("会员入金")
	@ResponseBody
	@GetMapping("/charge/{id}/{amount}")
	@RequiresPermissions("user:member:charge")
	R charge(@PathVariable("id") Integer id,@PathVariable("amount") BigDecimal amount){
		
		try {
			accountService.memberCharge(id, amount);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return R.error(e.getMessage());
		}
		return R.ok();
	}
	
	@Log("会员出金")
	@ResponseBody
	@GetMapping("/withdraw/{id}/{amount}")
	@RequiresPermissions("user:member:withdraw")
	R withdraw(@PathVariable("id") Integer id,@PathVariable("amount") BigDecimal amount){
		try {
			accountService.memberWithdraw(id, amount);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return R.error(e.getMessage());
		}
		return R.ok();
	}	
}
