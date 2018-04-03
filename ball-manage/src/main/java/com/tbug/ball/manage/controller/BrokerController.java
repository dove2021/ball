package com.tbug.ball.manage.controller;

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

import com.tbug.ball.manage.common.annotation.Log;
import com.tbug.ball.manage.controller.base.BaseController;
import com.tbug.ball.manage.util.R;
import com.tbug.ball.service.BrokerService;
import com.tbug.ball.service.MemberService;
import com.tbug.ball.service.Dto.user.BrokerDto;
import com.tbug.ball.service.Dto.user.MemberDto;
import com.tbug.ball.service.common.DBContants.BrokerConst;
import com.tbug.ball.service.common.DBContants.DB;
import com.tbug.ball.service.common.DBContants.MemberConst;

@Controller
@RequestMapping(BrokerController.prefix)
public class BrokerController extends BaseController {

	public static final String prefix = "user/broker";
	
	@Autowired
	BrokerService brokerService;
	@Autowired
	MemberService memberService;
	
	@GetMapping("")
	String broker(){
		return prefix + "/broker";
	}
	
	@ResponseBody
	@GetMapping("/list")
	List<BrokerDto> list(HttpServletRequest request){
		String phoneNo = request.getParameter("phoneNo");
		Map<String, Object> params = new HashMap<>();
		if (!StringUtils.isEmpty(phoneNo)){
			params.put("signCode", phoneNo);
		}
		List<BrokerDto> brokerDtoList = brokerService.listBrokerDtoByMap(params);
		
		return brokerDtoList;
	}

	@GetMapping("/add/{pId}")
	@RequiresPermissions("user:broker:add")
	String add(@PathVariable("pId") Integer pId ,Model model){
		
		if (pId.equals(0)){
			BrokerDto parentBrokerDto = new BrokerDto();
			parentBrokerDto.setParentId(DB.NULL_INT);
			parentBrokerDto.setSignCode("单独为第一层");
			model.addAttribute("parent", parentBrokerDto);
			
			Map<String, Object> params = new HashMap<>();
			params.put("Status", MemberConst.STATUS_NORMAL);
			List<MemberDto> memberDtoList = memberService.listMemberDtoByMap(params);
			model.addAttribute("memberList", memberDtoList);
			
			return prefix + "/add";
		} else {
			BrokerDto parentBrokerDto = brokerService.getBrokerDtoById(pId);
			model.addAttribute("parent", parentBrokerDto);
			
			return prefix + "/addSon";
		}
	}
	
	@ResponseBody
	@PostMapping("/save")
	R save(HttpServletRequest request){
		String parentId = request.getParameter("parentId");
		String memberCode = request.getParameter("memberCode");
		String phoneNo = request.getParameter("phoneNo");
		String nickName  = request.getParameter("nickName");
		String password = request.getParameter("password");
		
		try {
			brokerService.createBroker(getCurrentUser().getLoginName(), memberCode, phoneNo, nickName, password, Integer.valueOf(parentId));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return R.error(e.getMessage());
		}
		return R.ok();
	}
	
	@GetMapping("/edit/{id}")
	String edit(@PathVariable("id") Integer id, Model model){
		
		BrokerDto brokerDto = brokerService.getBrokerDtoById(id);
		BrokerDto parentBrokerDto = brokerService.getBrokerDtoById(brokerDto.getParentId());
		MemberDto memberDto = memberService.getMemberDtoById(brokerDto.getMemberId());
		
		if (null == parentBrokerDto){
			parentBrokerDto = new BrokerDto();
			parentBrokerDto.setSignCode("首层代理商");
		}
		model.addAttribute("parent", parentBrokerDto);
		model.addAttribute("brokerDto", brokerDto);
		model.addAttribute("memberDto", memberDto);
		return prefix + "/edit";
	}
	
	@ResponseBody
	@PostMapping("/update")
	R update(HttpServletRequest request){
		BrokerDto brokerDto = new BrokerDto();
		String brokerId = request.getParameter("brokerId");
		String phoneNo = request.getParameter("phoneNo");
		String signCode = request.getParameter("signCode");
		brokerDto.setBrokerId(Integer.valueOf(brokerId));
		brokerDto.setSignCode(phoneNo);
		brokerDto.setLoginName(phoneNo);
		brokerDto.setSignCode(signCode);
		
		brokerService.updBroker(brokerDto);
		return R.ok();
	}

	@Log("冻结代理商")
	@ResponseBody
	@PostMapping("/freeze")
	R freeze(@RequestParam("id") Integer id){
		
		BrokerDto brokerDto = new BrokerDto();
		brokerDto.setBrokerId(id);
		brokerDto.setStatus(BrokerConst.STATUS_FREEZE);
		
		if (!brokerService.updBroker(brokerDto)){
			return R.error("冻结失败");
		}
		return R.ok();
	}
	
	@ResponseBody
	@GetMapping("/exist/loginName")
	boolean existloginName(HttpServletRequest request){
		String loginName = request.getParameter("loginName");
		Map<String, Object> params = new HashMap<>();
		params.put("loginName", loginName);
		List<BrokerDto> brokerDtoList = brokerService.listBrokerDtoByMap(params);
		
		if (brokerDtoList == null || brokerDtoList.size() <= 0){
			return true;
		}
		
		return false;
	}
	
}
