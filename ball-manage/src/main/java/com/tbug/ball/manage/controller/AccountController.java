package com.tbug.ball.manage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.reger.dubbo.annotation.Inject;
import com.tbug.ball.manage.controller.base.BaseController;
import com.tbug.ball.manage.util.R;
import com.tbug.ball.service.BrokerService;
import com.tbug.ball.service.CustomerService;
import com.tbug.ball.service.MemberService;
import com.tbug.ball.service.TradeService;
import com.tbug.ball.service.Dto.PageDto;
import com.tbug.ball.service.Dto.Query;
import com.tbug.ball.service.Dto.account.BrokerAccountDto;
import com.tbug.ball.service.Dto.account.BrokerAccountExDto;
import com.tbug.ball.service.Dto.account.CustomerAccountDto;
import com.tbug.ball.service.Dto.account.CustomerAccountExDto;
import com.tbug.ball.service.Dto.account.MemberAccountDto;
import com.tbug.ball.service.Dto.account.TradeAccountDto;
import com.tbug.ball.service.Dto.flow.BrokerAccountFlowDto;
import com.tbug.ball.service.Dto.flow.CustomerAccountFlowDto;
import com.tbug.ball.service.Dto.flow.MemberAccountFlowDto;
import com.tbug.ball.service.Dto.flow.TradeAccountFlowDto;
import com.tbug.ball.service.common.DBContants.BrokerAccountConst;
import com.tbug.ball.service.common.DBContants.CustomerAccountConst;
import com.tbug.ball.service.common.DBContants.MemberAccountConst;

@Controller
@RequestMapping(AccountController.prefix)
public class AccountController extends BaseController {

	public static final String prefix = "/account";
	
	@Inject
	TradeService tradeService;
	@Inject
	MemberService memberService;
	@Inject
	CustomerService customerService;
	@Inject
	BrokerService brokerService;
	
	@GetMapping("/trade")
	String tradeAccount(){
		return prefix + "/trade/trade";
	}
	
	@ResponseBody
	@GetMapping("/trade/list")
	List<TradeAccountDto> listTradeAccount(){
		List<TradeAccountDto> list = tradeService.listTradeAccountByMap();
		return list;
	}
	
	@GetMapping("/trade/fund/detail/{id}")
	String tradeFundDetail(@PathVariable("id") Integer id, Model model){
		model.addAttribute("accountId", id);
		return prefix + "/trade/detail";
	}
	
	@ResponseBody
	@GetMapping("/trade/fund/detail/list")
	PageDto<TradeAccountFlowDto> tradeFlowList(@RequestParam Map<String, Object> params){
		Query query = new Query(params);
		List<TradeAccountFlowDto> list = tradeService.listTradeAccountFlowByMap(query);
		Integer total = tradeService.countTradeAccountFlowByMap(query);
		
		PageDto<TradeAccountFlowDto> page = new PageDto<>();
		page.setRows(list);
		page.setTotal(total);
		return page;
	}
	
	@GetMapping("/member")
	String memberAccount(){
		return prefix + "/member/member";
	}
	
	@ResponseBody
	@GetMapping("/member/list")
	List<MemberAccountDto> listMemberAccount(){
		Map<String, Object> params = new HashMap<>();
		List<MemberAccountDto> dtoList = memberService.listMemberAccountByMap(params);
		return dtoList;
	}
	
	@ResponseBody
	@GetMapping("/member/freeze/{ids}")
	R memberFreeze(@PathVariable("ids") Integer[] ids){
		int num = 0;
		for (Integer id : ids){
			MemberAccountDto dto = new MemberAccountDto();
			dto.setId(id);
			dto.setStatus(MemberAccountConst.STATUS_FREEZE);
			if (memberService.updMemberAccount(dto)){
				num ++;
			}
		}
		return R.ok("已成功冻结 " + num + " 个会员");
	}
	
	@ResponseBody
	@GetMapping("/member/unfreeze/{ids}")
	R memberUnFreeze(@PathVariable("ids") Integer[] ids){
		int num = 0;
		for (Integer id : ids){
			MemberAccountDto dto = new MemberAccountDto();
			dto.setId(id);
			dto.setStatus(MemberAccountConst.STATUS_NORMAL);
			if (memberService.updMemberAccount(dto)){
				num ++;
			}
		}
		return R.ok("已成功恢复 " + num + " 个会员");
	}
	
	@GetMapping("/member/detail/{id}")
	String memberDetail(@PathVariable("id") Integer id, Model model){
		model.addAttribute("accountId", id);
		return prefix + "/member/detail";
	}
	
	@ResponseBody
	@GetMapping("/member/detail/list")
	PageDto<MemberAccountFlowDto> memberDetailList(@RequestParam Map<String, Object> params){
		Query query = new Query(params);
		List<MemberAccountFlowDto> list = memberService.listMemberAccountFlowByMap(query);
		int total = memberService.countMemberAccountFlowByMap(query);
		
		PageDto<MemberAccountFlowDto> page = new PageDto<>();
		page.setRows(list);
		page.setTotal(total);
		return page;
	}
	
	@GetMapping("/customer")
	String accountCustomer(){
		return prefix + "/customer/customer";
	}
	
	@ResponseBody
	@GetMapping("/customer/list")
	PageDto<CustomerAccountExDto> listCustomer(@RequestParam Map<String, Object> params){
		Query query = new Query(params);
		List<CustomerAccountExDto> dtoList = customerService.listCustomerAccountByMap(query);
		int total = customerService.countCustomerAccountByMap(query);
		
		PageDto<CustomerAccountExDto> page = new PageDto<>();
		page.setRows(dtoList);
		page.setTotal(total);
		return page;
	}
	
	@ResponseBody
	@GetMapping("/customer/freeze/{ids}")
	R customerFreeze(@PathVariable("ids") Integer[] ids){
		int num = 0;
		for (Integer id : ids){
			CustomerAccountDto dto = new CustomerAccountDto();
			dto.setId(id);
			dto.setStatus(CustomerAccountConst.STATUS_FREEZE);
			if (customerService.updCustomerAccount(dto)){
				num++;
			}
		}
		return R.ok("成功冻结 " + num + " 个客户");
	}
	
	@ResponseBody
	@GetMapping("/customer/unfreeze/{ids}")
	R customerUnFreeze(@PathVariable("ids") Integer[] ids){
		int num = 0;
		for (Integer id : ids){
			CustomerAccountDto dto = new CustomerAccountDto();
			dto.setId(id);
			dto.setStatus(CustomerAccountConst.STATUS_NORMAL);
			if (customerService.updCustomerAccount(dto)){
				num++;
			}
		}
		return R.ok("成功恢复 " + num + " 个客户");
	}
	
	@GetMapping("/customer/detail/{id}")
	String customerDetail(@PathVariable("id") Integer id, Model model){
		model.addAttribute("accountId", id);
		return prefix + "/customer/detail";
	}
	
	@ResponseBody
	@GetMapping("/customer/detail/list")
	PageDto<CustomerAccountFlowDto> customerDetailList(@RequestParam Map<String, Object> params){
		Query query = new Query(params);
		List<CustomerAccountFlowDto> dtoList = customerService.listCustomerAccountFlowByMap(query);
		int total = customerService.countCustomerAccountFlowByMap(query);
		
		PageDto<CustomerAccountFlowDto> page = new PageDto<>();
		page.setRows(dtoList);
		page.setTotal(total);
		return page;
	}
	
	@GetMapping("/broker")
	String broker(){
		return prefix + "/broker/broker";
	}
	
	@ResponseBody
	@GetMapping("/broker/list")
	PageDto<BrokerAccountExDto> brokerList(@RequestParam Map<String, Object> params){
		Query query = new Query(params);
		List<BrokerAccountExDto> dtoList = brokerService.listBrokerAccount(query);
		int total = brokerService.countBrokerAccount(query);
		
		PageDto<BrokerAccountExDto> page = new PageDto<>();
		page.setRows(dtoList);
		page.setTotal(total);
		return page;
	}
	
	@ResponseBody
	@GetMapping("broker/freeze/{ids}")
	R brokerFreeze(@PathVariable("ids") Integer[] ids){
		
		int num = 0;
		for (Integer id : ids){
			BrokerAccountDto dto = new BrokerAccountDto();
			dto.setId(id);
			dto.setStatus(BrokerAccountConst.STATUS_FREEZE);
			if (brokerService.updBrokerAccount(dto)){
				num++;
			}
		}
		
		return R.ok("已冻结 " + num + " 个客户");
	}
	
	@ResponseBody
	@GetMapping("broker/unfreeze/{ids}")
	R brokerUnFreeze(@PathVariable("ids") Integer[] ids){
		int num = 0;
		for (Integer id : ids){
			BrokerAccountDto dto = new BrokerAccountDto();
			dto.setId(id);
			dto.setStatus(BrokerAccountConst.STATUS_NORMAL);
			if (brokerService.updBrokerAccount(dto)){
				num++;
			}
		}
		return R.ok("已恢复 " + num + " 个客户");
	}
	
	@GetMapping("/broker/detail/{id}")
	String brokerDetail(@PathVariable("id") Integer id, Model model){
		model.addAttribute("accountId", id);
		return prefix + "/broker/detail";
	}
	
	@ResponseBody
	@GetMapping("/broker/detail/list")
	PageDto<BrokerAccountFlowDto> brokerDetailList(@RequestParam Map<String, Object> params){
		Query query = new Query(params);
		List<BrokerAccountFlowDto> dtoList = brokerService.listBrokerAccountFlowByMap(query);
		int total = brokerService.countBrokerAccountFlowByMap(query);
		
		PageDto<BrokerAccountFlowDto> page = new PageDto<>();
		page.setRows(dtoList);
		page.setTotal(total);
		return page;
	}
}
