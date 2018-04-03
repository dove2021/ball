package com.tbug.ball.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.tbug.ball.service.CustomerService;
import com.tbug.ball.service.ServiceException;
import com.tbug.ball.service.Dto.account.CustomerAccountDto;
import com.tbug.ball.service.Dto.account.CustomerAccountExDto;
import com.tbug.ball.service.Dto.flow.CustomerAccountFlowDto;
import com.tbug.ball.service.Dto.user.CustomerDto;
import com.tbug.ball.service.Dto.user.CustomerExtDto;
import com.tbug.ball.service.biz.account.CustomerAccountBiz;
import com.tbug.ball.service.biz.flow.CustomerAccountFlowBiz;
import com.tbug.ball.service.biz.user.BrokerBiz;
import com.tbug.ball.service.biz.user.CustomerBiz;
import com.tbug.ball.service.common.DBContants.BrokerConst;
import com.tbug.ball.service.common.DBContants.CustomerConst;
import com.tbug.ball.service.common.security.PasswordHelper;
import com.tbug.ball.service.model.account.CustomerAccount;
import com.tbug.ball.service.model.account.CustomerAccountEx;
import com.tbug.ball.service.model.flow.CustomerAccountFlow;
import com.tbug.ball.service.model.flow.CustomerAccountFlowSum;
import com.tbug.ball.service.model.user.Broker;
import com.tbug.ball.service.model.user.Customer;
import com.tbug.ball.service.model.user.CustomerExt;

@Service
public class CustomerServiceImpl implements CustomerService {
	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
	
	@Autowired CustomerBiz customerBiz;
	
	@Autowired CustomerAccountBiz customerAccountBiz;
	
	@Autowired BrokerBiz brokerBiz;
	
	@Autowired CustomerAccountFlowBiz customerAccountFlowBiz;
	
	@Override
	@Transactional(rollbackFor={ServiceException.class})
	public Boolean createCustomer(String loginName, String password, String memberCode, String signCode, String IP)
			throws ServiceException {
		try {
			Customer customer = customerBiz.createCustomer(loginName, password, memberCode, signCode,IP);
			
			// 资金账户
			CustomerAccount customerAccount = new CustomerAccount();
			customerAccount.setCustomerId(customer.getId());
			if (customerAccountBiz.createCustomerAccount(customerAccount) != 1){
				throw new ServiceException("生成资金账户失败");
			}
		} catch (Exception e){
			logger.error("创建客户失败:{}", e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
		return true;
	}

	@Override
	public CustomerDto cutomerLogin(String loginName) throws ServiceException {
		try {
			Customer customer = customerBiz.getCustomerByloginName(loginName);
			if (customer == null){
				customer = customerBiz.getCustomerByPhoneNo(loginName);
			}
			if (customer == null){
				customer = customerBiz.getCustomerByCode(loginName);
			}
			if (customer == null){
				throw new ServiceException("用户不存在");
			}
			CustomerDto customerDto = new CustomerDto();
			BeanUtils.copyProperties(customer, customerDto);
			
			return customerDto;
		} catch (Exception e){
			logger.error("登陆异常, 参数:用户名: {},异常信息: {}",loginName, e.getMessage(), e);
			throw new ServiceException(e.getMessage());
		}
		
	}

	@Override
	public CustomerDto getCustomerDtoById(Integer id) {
		CustomerDto customerDto = null;
		
		Customer customer = customerBiz.getCustomerById(id);
		if (customer != null){
			customerDto = new CustomerDto();
			
			BeanUtils.copyProperties(customer, customerDto);
		}
		return customerDto;
	}

	@Override
	public CustomerDto getCustomerDtoByCode(String code) {
		CustomerDto customerDto = null;
		
		Customer customer = customerBiz.getCustomerByCode(code);
		if (customer != null){
			customerDto = new CustomerDto();
			
			BeanUtils.copyProperties(customerDto, customer);
		}
		return customerDto;
	}

	@Override
	public List<CustomerExtDto> listCustomerByMap(Map<String, Object> params) {
		List<CustomerExtDto> customerDtoList = new ArrayList<>();
		
		List<CustomerExt> customerList = customerBiz.listCustomerByMap(params);
		if (!CollectionUtils.isEmpty(customerList)){
			
			for (CustomerExt customerExt : customerList){
				CustomerExtDto dto = new CustomerExtDto();
				BeanUtils.copyProperties(customerExt, dto);
				
				customerDtoList.add(dto);
			}
		}
		return customerDtoList;
	}

	@Override
	public Integer countCustomerByMap(Map<String, Object> params) {
		return customerBiz.countCustomerByMap(params);
	}

	@Override
	public boolean updCustomerStatus(Integer id, String status) throws ServiceException {
		Customer customer = new Customer();
		customer.setId(id);
		customer.setStatus(status);
		
		if (customerBiz.updCustomer(customer) != 1){
			throw new ServiceException("更新用户状态失败");
		}
		
		return true;
	}

	@Override
	public boolean moveBroker(Integer newBrokerId, CustomerDto customerDto) throws ServiceException{
		
		if (newBrokerId.equals(customerDto.getBrokerId())){
			throw new ServiceException("代理商还是原代理商");
		}
		
		Broker broker = brokerBiz.getBrokerById(newBrokerId);
		if (broker == null){
			throw new ServiceException("代理商不存在");
		}
		if (!BrokerConst.STATUS_NORMAL.equals(broker.getStatus())){
			throw new ServiceException("代理商状态异常");
		}
		
		Customer customer = customerBiz.getCustomerById(customerDto.getId());
		if (customer == null){
			throw new ServiceException("客户信息不存在");
		}
		if (!CustomerConst.STATUS_NORMAL.equals(customer.getStatus())){
			throw new ServiceException("客户状态异常");
		}
		
		// 开始转移
		customer.setBrokerId(broker.getBrokerId());
		customer.setLevelCode(broker.getLevelCode());
		customer.setLevelNum(broker.getLevelNum());
		if (customerBiz.updCustomer(customer) != 1){
			throw new ServiceException("客户");
		}
		
		return true;
	}

	@Override
	public CustomerAccountDto getCustomerAccountById(Integer id) {
		CustomerAccountDto dto = new CustomerAccountDto();
		CustomerAccount customerAccount = customerAccountBiz.getCustomerAccountById(id);
		if (null != customerAccount){
			BeanUtils.copyProperties(customerAccount, dto);
		}
		return dto;
	}

	@Override
	public List<CustomerAccountExDto> listCustomerAccountByMap(Map<String, Object> params) {
		List<CustomerAccountExDto> dtoList = new ArrayList<>();
		
		List<CustomerAccountEx> list = customerAccountBiz.listCustomerAccountByMap(params);
		if (!CollectionUtils.isEmpty(list)){
			for (CustomerAccountEx customerAccountEx : list){
				CustomerAccountExDto dto = new CustomerAccountExDto();
				BeanUtils.copyProperties(customerAccountEx, dto);
				
				dtoList.add(dto);
			}
		}
		return dtoList;
	}

	@Override
	public int countCustomerAccountByMap(Map<String, Object> params) {
		return customerAccountBiz.CountCustomerAccountByMap(params);
	}

	@Override
	public boolean updCustomerAccount(CustomerAccountDto dto) {
		CustomerAccount customerAccount = new CustomerAccount();
		BeanUtils.copyProperties(dto, customerAccount);
		
		if (customerAccountBiz.updCustomerAccount(customerAccount) != 1){
			return false;
		}
		return true;
	}

	@Override
	public CustomerAccountDto getCustomerAccountByCustomerId(Integer customerId) {
		CustomerAccountDto dto = new CustomerAccountDto();
		CustomerAccount customerAccount = customerAccountBiz.getCustomerAccountByCustomerId(customerId);
		if (null != customerAccount){
			BeanUtils.copyProperties(customerAccount, dto);
		}
		return dto;
	}

	@Override
	public boolean customerRePwd(Integer customerId, String password, String newPassword) throws ServiceException {
		Customer customer = customerBiz.getCustomerById(customerId);
		if (null == customer){
			throw new ServiceException("账户不存在");
		}
		if (!StringUtils.isEmpty(password)){
			String salt = customer.getSalt();
			String enPassword = PasswordHelper.encryptPassword(salt, password);
			if (!enPassword.equals(customer.getPassword())){
				throw new ServiceException("原密码错误");
			}
		}
		
		String newSalt = PasswordHelper.generateSalt();
		String newEnpassword = PasswordHelper.encryptPassword(newSalt, newPassword);
		
		customer.setSalt(newSalt);
		customer.setPassword(newEnpassword);
		
		if (customerBiz.updCustomer(customer) != 1){
			throw new ServiceException("更新密码失败");
		}
		return true;
	}

	@Override
	public List<CustomerAccountFlowDto> listCustomerAccountFlowByMap(Map<String, Object> params) {
		List<CustomerAccountFlowDto> dtoList = new ArrayList<>();
		
		List<CustomerAccountFlow> list = customerAccountFlowBiz.listCustomerAccountFlowByMap(params);
		if (!CollectionUtils.isEmpty(list)){
			for (CustomerAccountFlow customerAccountFlow : list){
				CustomerAccountFlowDto dto = new CustomerAccountFlowDto();
				BeanUtils.copyProperties(customerAccountFlow, dto);
				
				dtoList.add(dto);
			}
		}
		
		return dtoList;
	}

	@Override
	public Integer countCustomerAccountFlowByMap(Map<String, Object> params) {
		return customerAccountFlowBiz.countCustomerAccountFlowByMap(params);
	}

	@Override
	public void updCustomerPhoneNo(Integer customerId, String phoneNo) throws ServiceException {
		Customer customer = customerBiz.getCustomerById(customerId);
		if (customer == null){
			throw new ServiceException("客户不存在");
		}
		
		Customer c = new Customer();
		c.setId(customerId);
		c.setPhoneNo(phoneNo);
		if (customerBiz.updCustomer(c) != 1){
			throw new ServiceException("客户更新手机号失败");
		}
	}

	@Override
	public CustomerAccountFlowDto getCustomerAccountFlowDtoById(Integer id) {
		CustomerAccountFlowDto dto = new CustomerAccountFlowDto();
		
		CustomerAccountFlow customerAccountFlow = customerAccountFlowBiz.getCustomerAccountFlowById(id);
		if (null != customerAccountFlow){
			BeanUtils.copyProperties(customerAccountFlow, dto);
		}
		return dto;
	}

	@Override
	public Map<String, Object> sumCustomerAccountFlowByMap(Map<String, Object> params) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("inCome", BigDecimal.ZERO);
		map.put("outGo", BigDecimal.ZERO);
		
		CustomerAccountFlowSum sum = customerAccountFlowBiz.sumFlow(params);
		if (sum != null){
			map.put("inCome", sum.getInCome());
			map.put("outGo", sum.getOutGo());
		}
		return map;
	}

}
