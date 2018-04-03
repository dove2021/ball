package com.tbug.ball.service;

import java.math.BigDecimal;
import java.util.Map;

import com.tbug.ball.service.Dto.pay.CustomerWithdrawDto;

public interface AccountService {

	/**
	 * 客户充值: 后台
	 */
	public void customerCharge(Integer customerId, BigDecimal amount, String creater, String operatorInfo,Integer channelId) throws ServiceException;
	
	/**
	 * 客户充值: 客户
	 */
	public void charegeSubmit(Integer customerId, BigDecimal amount, String creater, String remark,Integer channelId) throws ServiceException;
	
	/**
	 * 客户充值
	 */
	public void customerWithdraw(int channelId, Integer customerId, BigDecimal amount, BigDecimal fee, String creater) throws ServiceException;
	
	/**
	 * 会员充值
	 */
	public void memberCharge(Integer memberId, BigDecimal amount) throws ServiceException;
	
	/**
	 * 会员充值
	 */
	public void memberWithdraw(Integer memberId, BigDecimal amount) throws ServiceException;
	
	public CustomerWithdrawDto getCustomerWithdrawDtoById(Integer id);
	
	public void updCustomerWithdraw(CustomerWithdrawDto dto) throws ServiceException;
	
	public Integer customerWithdrawTimes(Map<String, Object> params);
	
	/**
	 * 
	 * @param dto
	 * @return
	 */
	public boolean customerWithdraw(Integer customerId,String channelId, String withdrawAmountStr) throws ServiceException;
}
