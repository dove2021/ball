package com.tbug.ball.service;

import java.util.List;
import java.util.Map;

import com.tbug.ball.service.Dto.account.CustomerAccountDto;
import com.tbug.ball.service.Dto.account.CustomerAccountExDto;
import com.tbug.ball.service.Dto.flow.CustomerAccountFlowDto;
import com.tbug.ball.service.Dto.user.CustomerDto;
import com.tbug.ball.service.Dto.user.CustomerExtDto;

public interface CustomerService {

	public Boolean createCustomer(String loginName, String password, String memberCode, String signCode, String IP) throws ServiceException;
	
	public CustomerDto cutomerLogin(String loginName) throws ServiceException;
	
	public CustomerDto getCustomerDtoById(Integer id);
	
	public CustomerDto getCustomerDtoByCode(String code);
	
	public List<CustomerExtDto> listCustomerByMap(Map<String, Object> params);
	
	public Integer countCustomerByMap(Map<String, Object> params);
	
	public boolean updCustomerStatus(Integer id, String status) throws ServiceException;
	
	boolean moveBroker(Integer newBrokerId, CustomerDto customerDto) throws ServiceException;
	
	CustomerAccountDto getCustomerAccountById(Integer id);
	
	CustomerAccountDto getCustomerAccountByCustomerId(Integer customerId);
	
	List<CustomerAccountExDto> listCustomerAccountByMap(Map<String, Object> params);
	
	int countCustomerAccountByMap(Map<String, Object> params);
	
	boolean updCustomerAccount(CustomerAccountDto customerAccountDto);
	
	boolean customerRePwd(Integer customerId, String password, String newPassword) throws ServiceException;
	
	List<CustomerAccountFlowDto> listCustomerAccountFlowByMap(Map<String, Object> params);
	
	Integer countCustomerAccountFlowByMap(Map<String, Object> params);
	
	public void updCustomerPhoneNo(Integer customerId, String phoneNo) throws ServiceException;
	
	CustomerAccountFlowDto getCustomerAccountFlowDtoById(Integer id);
	
	Map<String, Object> sumCustomerAccountFlowByMap(Map<String, Object> params);
}
