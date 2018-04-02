package com.tbug.ball.service.impl;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbug.ball.service.AccountService;
import com.tbug.ball.service.ServiceException;
import com.tbug.ball.service.biz.cash.AccountTransferBiz;
import com.tbug.ball.service.common.factory.CodeFactory;

@Service
public class AccountServiceImpl implements AccountService {
	private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
	
	@Autowired
	AccountTransferBiz accountTransferBiz;
	@Autowired
	CodeFactory codeFactory;
	
	@Override
	public void customerCharge(Integer customerId, BigDecimal amount) throws ServiceException {
		try {
			String transcationCode = codeFactory.getTranscationCode();
			if (!accountTransferBiz.customerPay(transcationCode, customerId, amount)){
				throw new ServiceException("客户充值失败");
			}
		} catch (Exception e){
			logger.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public void customerWithdraw(Integer customerId, BigDecimal amount) throws ServiceException {
		try {
			String transcationCode = codeFactory.getTranscationCode();
			if (!accountTransferBiz.customerWithdraw(customerId,amount, transcationCode)){
				throw new ServiceException("客户提现失败");
			}
		} catch (Exception e){
			logger.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public void memberCharge(Integer memberId, BigDecimal amount) throws ServiceException {
		try {
			String transcationCode = codeFactory.getTranscationCode();
			accountTransferBiz.memberCharge(memberId,amount,transcationCode);
		} catch (Exception e){
			logger.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public void memberWithdraw(Integer memberId, BigDecimal amount) throws ServiceException {
		try {
			String transcationCode = codeFactory.getTranscationCode();
			accountTransferBiz.memberWithdraw(memberId, amount, transcationCode);
		} catch (Exception e){
			logger.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage());
		}
	}

}
