package com.tbug.ball.service.biz.user;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tbug.ball.service.biz.BizException;
import com.tbug.ball.service.common.DBContants.BrokerConst;
import com.tbug.ball.service.common.DBContants.CustomerConst;
import com.tbug.ball.service.common.DBContants.MemberConst;
import com.tbug.ball.service.common.factory.CodeFactory;
import com.tbug.ball.service.common.security.PasswordHelper;
import com.tbug.ball.service.dao.user.BrokerMapper;
import com.tbug.ball.service.dao.user.CustomerMapper;
import com.tbug.ball.service.dao.user.MemberMapper;
import com.tbug.ball.service.model.user.Broker;
import com.tbug.ball.service.model.user.Customer;
import com.tbug.ball.service.model.user.Member;

@Service
public class CustomerBiz {
	private static final Logger logger = LoggerFactory.getLogger(CustomerBiz.class);
	
	@Autowired
	CustomerMapper customerMapper;
	@Autowired
	CodeFactory codeFactory;
	@Autowired
	MemberMapper memberMapper;
	@Autowired
	BrokerMapper brokerMapper;
	
	public Customer createCustomer(String loginName,String password,String memberCode, String brokerSignCode, String IP) throws BizException{
		
		Member member = memberMapper.selectByCode(memberCode);
		if (null == member){
			throw new BizException("会员单位不能为空");
		}
		if (!MemberConst.STATUS_NORMAL.equals(member.getStatus())){
			throw new BizException("会员状态异常");
		}
		Customer dbCustomer = customerMapper.selectByLoginName(loginName);
		if (dbCustomer != null){
			throw new BizException("登录名已经存");
		}
		
		Customer customer = new Customer();
		customer.setMemberId(member.getId());
		customer.setPhoneNo("");
		customer.setLoginName(loginName);
		customer.setCreateDate(new Date());
		customer.setCustomerCode(codeFactory.getCustomerCode(member.getMemberCode()));
		customer.setStatus(CustomerConst.STATUS_NORMAL);
		customer.setRegisterIp(IP);
		
		if (StringUtils.isEmpty(brokerSignCode)){
			customer.setBrokerId(-1);
			customer.setLevelCode("");
			customer.setLevelNum(Byte.valueOf("-1"));
		}else {
			Broker broker = brokerMapper.selectBySignCode(brokerSignCode);
			if (null == broker){
				logger.warn("代理商不存在");
			}
			if (!BrokerConst.STATUS_NORMAL.equals(broker.getStatus())){
				logger.warn("代理商状态异常");
			}
			customer.setMemberId(broker.getMemberId());
			customer.setBrokerId(broker.getBrokerId());
			customer.setLevelCode(broker.getLevelCode());
			customer.setLevelNum(broker.getLevelNum());
		}
		
		String salt = PasswordHelper.generateSalt();
		String newPassword = PasswordHelper.encryptPassword(salt, password);
		
		customer.setPassword(newPassword);
		customer.setSalt(salt);		
		if (customerMapper.insert(customer) != 1){
			throw new BizException("客户创建失败");
		}
		
		return customer;
	}
	
	public Customer getCustomerById(Integer id){
		return customerMapper.selectByPrimaryKey(id);
	}
	
	public Customer getCustomerByCode(String code){
		return customerMapper.selectByCode(code);
	}
	
	public Customer getCustomerByloginName(String loginName){
		return customerMapper.selectByLoginName(loginName);
	}
	
	public Customer getCustomerByPhoneNo(String phoneNo){
		return customerMapper.selectByPhoneNo(phoneNo);
	}
	
	public List<Customer> listCustomerByMap(Map<String, Object> params){
		return customerMapper.listCustomerByMap(params);
	}
	
	public Integer countCustomerByMap(Map<String, Object> params){
		return customerMapper.countCustomerByMap(params);
	}
	
	public Integer updCustomer(Customer customer){
		return  customerMapper.updateByPrimaryKeySelective(customer);
	}
	
	public List<Customer> listCustomerByBrokerId(Integer brokerId){
		Map<String, Object> params = new HashMap<>();
		params.put("brokerId", brokerId);
		return customerMapper.listCustomerByMap(params);
	}
	
	/**
	 * 模糊匹配
	 * @param levelCode
	 * @return
	 */
	public List<Customer> listCustomerByLevelCode(String levelCode){
		Map<String, Object> params = new HashMap<>();
		params.put("levelCode", levelCode);
		return customerMapper.listCustomerByMap(params);
	}
	
	/**
	 * 非模糊匹配
	 * @param levelCode
	 * @return
	 */
	public List<Customer> getCustomerByLevelCode(String levelCode){
		return customerMapper.selectByLevelCode(levelCode);
	}
}
