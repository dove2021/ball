package com.tbug.ball.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.tbug.ball.service.PayService;
import com.tbug.ball.service.ServiceException;
import com.tbug.ball.service.Dto.pay.CashFileDto;
import com.tbug.ball.service.Dto.pay.ChargeRecordDto;
import com.tbug.ball.service.Dto.pay.PaymentChannelDto;
import com.tbug.ball.service.Dto.pay.WithdrawFileDto;
import com.tbug.ball.service.Dto.pay.WithdrawRecordDto;
import com.tbug.ball.service.biz.BizException;
import com.tbug.ball.service.biz.cash.AccountTransferBiz;
import com.tbug.ball.service.biz.pay.CashFileBiz;
import com.tbug.ball.service.biz.pay.ChargeRecordBiz;
import com.tbug.ball.service.biz.pay.PaymentChannelBiz;
import com.tbug.ball.service.biz.pay.WithdrawFileBiz;
import com.tbug.ball.service.biz.pay.WithdrawRecordBiz;
import com.tbug.ball.service.common.DBContants.CashFileConst;
import com.tbug.ball.service.common.DBContants.ChargeRecordConst;
import com.tbug.ball.service.common.DBContants.WithdrawFileConst;
import com.tbug.ball.service.common.DBContants.WithdrawRecordConst;
import com.tbug.ball.service.common.factory.CodeFactory;
import com.tbug.ball.service.model.pay.CashFile;
import com.tbug.ball.service.model.pay.ChargeRecord;
import com.tbug.ball.service.model.pay.PaymentChannel;
import com.tbug.ball.service.model.pay.WithdrawFile;
import com.tbug.ball.service.model.pay.WithdrawRecord;

@Service
public class PayServiceImpl implements PayService {

	private static final Logger logger = LoggerFactory.getLogger(PayServiceImpl.class);
	
	@Autowired
	CashFileBiz cashFileBiz;
	@Autowired
	PaymentChannelBiz paymentChannelBiz;
	@Autowired
	ChargeRecordBiz chargeRecordBiz;
	@Autowired
	WithdrawRecordBiz withdrawRecordBiz;
	@Autowired
	AccountTransferBiz accountTransferBiz;
	@Autowired
	CodeFactory codeFactory;
	@Autowired
	WithdrawFileBiz withdrawFileBiz;
	
	@Override
	public void createCashFile(CashFileDto dto) {
		CashFile cashFile = new CashFile();
		BeanUtils.copyProperties(dto, cashFile);
		cashFileBiz.createCashFile(cashFile);
	}

	@Override
	public CashFileDto getCashFileDtoByChannelAndName(Integer channelId, String fileName) {
		CashFile cashFile = cashFileBiz.getByChanenelAndFileName(channelId, fileName);
		if (null == cashFile){
			return null;
		}
		CashFileDto dto = new CashFileDto();
		BeanUtils.copyProperties(cashFile, dto);
		return dto;
	}
	
	@Override
	public List<CashFileDto> listCashFileDto(Integer channelId) {
		List<CashFileDto> dtoList = new ArrayList<>();
		List<CashFile> list = cashFileBiz.listByStatus(channelId, CashFileConst.STATUS_1);
		if (!CollectionUtils.isEmpty(list)){
			for (CashFile cashFile : list){
				CashFileDto dto = new CashFileDto();
				BeanUtils.copyProperties(cashFile, dto);
				dtoList.add(dto);
			}
		}
		return dtoList;
	}

	@Override
	public void delCashFile(Integer id) {
		cashFileBiz.delCashFile(id);
	}

	@Override
	public CashFileDto getCashFileById(Integer id) {
		CashFileDto dto = new CashFileDto();
		CashFile cashFile = cashFileBiz.getCashFileById(id);
		if (null != cashFile){
			BeanUtils.copyProperties(cashFile, dto);
		}
		return dto;
	}

	@Override
	public List<PaymentChannelDto> listPaymentChannelAll() {
		List<PaymentChannelDto> dtoList = new ArrayList<>();
		
		List<PaymentChannel> list = paymentChannelBiz.listByAll();
		if (!CollectionUtils.isEmpty(list)){
			for (PaymentChannel paymentChannel : list){
				PaymentChannelDto dto = new PaymentChannelDto();
				BeanUtils.copyProperties(paymentChannel, dto);
				
				dtoList.add(dto);
			}
		}
		return dtoList;
	}

	@Override
	public boolean updCashFile(PaymentChannelDto dto) {
		PaymentChannel paymentChannel = new PaymentChannel();
		BeanUtils.copyProperties(dto, paymentChannel);
		
		if (paymentChannelBiz.updPaymentChannel(paymentChannel) == 1){
			return true;
		}
		return false;
	}

	@Override
	public WithdrawRecordDto getWithdrawRecordById(Integer id) {
		WithdrawRecord withdrawRecord = withdrawRecordBiz.getById(id);
		if (null == withdrawRecord ){
			return null;
		}
		WithdrawRecordDto dto = new WithdrawRecordDto();
		BeanUtils.copyProperties(withdrawRecord, dto);
		return dto;
	}

	@Override
	public List<WithdrawRecordDto> listWithdrawRecordByMap(Map<String, Object> params) {
		List<WithdrawRecordDto> dtoList = new ArrayList<>();
		List<WithdrawRecord> list = withdrawRecordBiz.listByMap(params);
		if (!CollectionUtils.isEmpty(list)){
			for (WithdrawRecord withdrawRecord : list){
				WithdrawRecordDto dto = new WithdrawRecordDto();
				BeanUtils.copyProperties(withdrawRecord, dto);
				dtoList.add(dto);
			}
		}
		return dtoList;
	}

	@Override
	public int countWithdrawRecordByMap(Map<String, Object> params) {
		return withdrawRecordBiz.countByMap(params);
	}

	@Override
	public ChargeRecordDto getChargeRecordById(Integer id) {
		ChargeRecord chargeRecord = chargeRecordBiz.getById(id);
		if (null == chargeRecord){
			return null;
		}
		ChargeRecordDto dto = new ChargeRecordDto();
		BeanUtils.copyProperties(chargeRecord, dto);
		
		return dto;
	}

	@Override
	public List<ChargeRecordDto> listChargeRecordByMap(Map<String, Object> params) {
		List<ChargeRecordDto> dtoList = new ArrayList<>();
		List<ChargeRecord> list = chargeRecordBiz.listByMap(params);
		if (!CollectionUtils.isEmpty(list)){
			for (ChargeRecord chargeRecord : list){
				ChargeRecordDto dto  = new ChargeRecordDto();
				BeanUtils.copyProperties(chargeRecord, dto);
				dtoList.add(dto);
			}
		}
		return dtoList;
	}

	@Override
	public int countChargeRecordByMap(Map<String, Object> params) {
		return chargeRecordBiz.countByMap(params);
	}

	@Override
	public boolean customerWithdrawPass(Integer id, String operater, String operateInfo) {
		
		WithdrawRecord withdrawRecord = new WithdrawRecord();
		withdrawRecord.setId(id);
		withdrawRecord.setOperater(operater);
		withdrawRecord.setOperateInfo(operateInfo);
		withdrawRecord.setStatus(WithdrawRecordConst.STATUS_3);
		withdrawRecord.setFinishDate(new Date());
		
		if (withdrawRecordBiz.updWithdrawRecord(withdrawRecord) != 1){
			return false;
		}
		return true;
	}

	@Override
	@Transactional(rollbackFor={ServiceException.class})
	public void customerWIthdrawBack(Integer id, String operater, String operateInfo) throws ServiceException {
		try {
			WithdrawRecord withdrawRecord = new WithdrawRecord();
			withdrawRecord.setId(id);
			withdrawRecord.setOperater(operater);
			withdrawRecord.setOperateInfo(operateInfo);
			withdrawRecord.setStatus(WithdrawRecordConst.STATUS_2);
			withdrawRecord.setFinishDate(new Date());
			
			if (withdrawRecordBiz.updWithdrawRecord(withdrawRecord) != 1){
				throw new ServiceException("出金记录更新失败");
			}
			
			WithdrawRecord dbWithdrawRecord = withdrawRecordBiz.getById(id);
			
			// 回退客户资金
			accountTransferBiz.customerWithdrawBack(dbWithdrawRecord.getCustomerId(), dbWithdrawRecord.getWithdrawAmount(), dbWithdrawRecord.getWithdrawFee());
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	@Transactional(rollbackFor={ServiceException.class})
	public boolean customerChargePass(Integer id, BigDecimal confirmAmount, String operater, String operateInfo) throws ServiceException {
		
		ChargeRecord chargeRecord = chargeRecordBiz.getById(id);
		chargeRecord.setStatus(ChargeRecordConst.STATUS_3);
		chargeRecord.setFinishTime(new Date());
		chargeRecord.setOperater(operater);
		chargeRecord.setOperateInfo(operateInfo);
		chargeRecord.setConfirmAmount(confirmAmount);
		
		if (chargeRecordBiz.updChargeRecord(chargeRecord) != 1){
			throw new ServiceException("入金更新失败");
		}
		
		try {
			accountTransferBiz.customerPay(codeFactory.getTranscationCode(), chargeRecord.getCustomerId(), confirmAmount);
		} catch (BizException e) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage());
		}
		
		return true;
	}

	@Override
	public void customerChargeBack(Integer id, String operater, String operateInfo) throws ServiceException {
		ChargeRecord chargeRecord = chargeRecordBiz.getById(id);
		chargeRecord.setStatus(ChargeRecordConst.STATUS_2);
		chargeRecord.setFinishTime(new Date());
		chargeRecord.setOperater(operater);
		chargeRecord.setOperateInfo(operateInfo);
		
		if (chargeRecordBiz.updChargeRecord(chargeRecord) != 1){
			throw new ServiceException("入金更新失败");
		}
	}

	@Override
	public void createWithdrawFile(WithdrawFileDto dto) {
		WithdrawFile withdrawFile = new WithdrawFile();
		BeanUtils.copyProperties(dto, withdrawFile);
		withdrawFileBiz.create(withdrawFile);
	}

	@Override
	public List<WithdrawFileDto> listWithdrawFileDto(Integer customerId) {
		
		List<WithdrawFile> list = withdrawFileBiz.listWithdrawFileById(customerId);
		if (CollectionUtils.isEmpty(list)){
			return new ArrayList<>(0);
		}
		List<WithdrawFileDto> dtoList = new ArrayList<>();
		for (WithdrawFile withdrawFile : list){
			WithdrawFileDto dto = new WithdrawFileDto();
			BeanUtils.copyProperties(withdrawFile, dto);
			dtoList.add(dto);
		}
		return dtoList;
	}

	@Override
	public void updWithdrawFile(Integer customerId, Integer channelId) {
		WithdrawFile file = withdrawFileBiz.getByCustomer(customerId, channelId);
		file.setModifyDate(new Date());
		file.setStatus(WithdrawFileConst.STATUS_2);
		withdrawFileBiz.updWithdrawFile(file);
	}

	@Override
	public WithdrawFileDto getWithdrawFile(Integer customerId, Integer channelId) {
		
		WithdrawFile file = withdrawFileBiz.getByCustomer(customerId, channelId);
		if (file == null){
			return null;
		}
		WithdrawFileDto dto = new WithdrawFileDto();
		BeanUtils.copyProperties(file, dto);
		
		return dto;
	}
	
}
