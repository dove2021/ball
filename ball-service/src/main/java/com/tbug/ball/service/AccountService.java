package com.tbug.ball.service;

import java.math.BigDecimal;

public interface AccountService {

	/**
	 * 客户充值
	 */
	public void customerCharge(Integer customerId, BigDecimal amount) throws ServiceException;
	
	/**
	 * 客户充值
	 */
	public void customerWithdraw(Integer customerId, BigDecimal amount) throws ServiceException;
	
	/**
	 * 会员充值
	 */
	public void memberCharge(Integer memberId, BigDecimal amount) throws ServiceException;
	
	/**
	 * 会员充值
	 */
	public void memberWithdraw(Integer memberId, BigDecimal amount) throws ServiceException;
	
}
