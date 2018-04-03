package com.tbug.ball.service.biz.account;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbug.ball.service.biz.BizException;
import com.tbug.ball.service.common.DBContants.CustomerAccountConst;
import com.tbug.ball.service.common.security.PasswordHelper;
import com.tbug.ball.service.dao.account.CustomerAccountMapper;
import com.tbug.ball.service.model.account.CustomerAccount;
import com.tbug.ball.service.model.account.CustomerAccountEx;

@Service
public class CustomerAccountBiz {

	@Autowired
	CustomerAccountMapper customerAccountMapper;
	
	public Integer createCustomerAccount(CustomerAccount customerAccount) throws BizException{
		
		if (null == customerAccount){
			throw new BizException("客户账户不存在");
		}
		
		customerAccount.setBalance(BigDecimal.ZERO);
		customerAccount.setCreateDate(new Date());
		customerAccount.setStatus(CustomerAccountConst.STATUS_NORMAL);
		
		String password = customerAccount.getAccountPassword();
		String salt = PasswordHelper.generateSalt();
		String newPassword = PasswordHelper.encryptPassword(salt, password);
		
		customerAccount.setAccountPassword(newPassword);
		customerAccount.setSalt(salt);
		
		return customerAccountMapper.insert(customerAccount);
	}
	
	public CustomerAccount getCustomerAccountById(Integer id){
		return customerAccountMapper.selectByPrimaryKey(id);
	}
	
	public CustomerAccount getCustomerAccountByCustomerId(Integer customerId){
		return customerAccountMapper.selectByCustomerId(customerId);
	}
	
	public CustomerAccount getCustomerAccountByIdForUpdate(Integer id){
		return customerAccountMapper.selectByPrimaryKeyForUpdate(id);
	}

	public List<CustomerAccountEx> listCustomerAccountByMap(Map<String, Object> params){
		return customerAccountMapper.selectByMap(params);
	}
	
	public Integer CountCustomerAccountByMap(Map<String, Object> params){
		return customerAccountMapper.countByMap(params);
	}
	
	public Integer updCustomerAccount(CustomerAccount customerAccount){
		return customerAccountMapper.updateByPrimaryKeySelective(customerAccount);
	}
	
}
