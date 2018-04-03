package com.tbug.ball.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tbug.ball.service.Dto.pay.CashFileDto;
import com.tbug.ball.service.Dto.pay.ChargeRecordDto;
import com.tbug.ball.service.Dto.pay.PaymentChannelDto;
import com.tbug.ball.service.Dto.pay.WithdrawFileDto;
import com.tbug.ball.service.Dto.pay.WithdrawRecordDto;

public interface PayService {

	CashFileDto getCashFileById(Integer id);
	
	CashFileDto getCashFileDtoByChannelAndName(Integer channelId, String fileName);
	
	void createCashFile(CashFileDto dto);
	
	List<CashFileDto> listCashFileDto(Integer channelId);
	
	void delCashFile(Integer id);
	
	List<PaymentChannelDto> listPaymentChannelAll();
	
	boolean updCashFile(PaymentChannelDto dto);
	
	WithdrawRecordDto getWithdrawRecordById(Integer id);
	
	List<WithdrawRecordDto> listWithdrawRecordByMap(Map<String, Object> params);
	
	int countWithdrawRecordByMap(Map<String, Object> params);
	
	ChargeRecordDto getChargeRecordById(Integer id);
	
	List<ChargeRecordDto> listChargeRecordByMap(Map<String, Object> params);
	
	int countChargeRecordByMap(Map<String, Object> params);
	
	/**
	 * 提现已打款
	 * @param id
	 * @param operater
	 * @param operateInfo
	 * @return
	 */
	boolean customerWithdrawPass(Integer id,String operater, String operateInfo);
	
	/**
	 * 提现遭驳回
	 * @param id
	 * @param operater
	 * @param operateInfo
	 * @throws ServiceException
	 */
	void customerWIthdrawBack(Integer id,String operater, String operateInfo) throws ServiceException;
	
	/**
	 * 入金审核通过
	 * @param id
	 * @param BigDecimal amount
	 * @param operater
	 * @param operateInfo
	 * @return
	 */
	boolean customerChargePass(Integer id, BigDecimal confirmAmount,String operater, String operateInfo) throws ServiceException ;
	
	/**
	 * 入金审核驳回
	 * @param id
	 * @param operater
	 * @param operateInfo
	 * @throws ServiceException
	 */
	void customerChargeBack(Integer id,String operater, String operateInfo) throws ServiceException;
	
	void createWithdrawFile(WithdrawFileDto dto);
	
	List<WithdrawFileDto> listWithdrawFileDto(Integer customerId);
	
	void updWithdrawFile(Integer customerId, Integer channelId);
	
	WithdrawFileDto getWithdrawFile(Integer customerId, Integer channelId);
}
