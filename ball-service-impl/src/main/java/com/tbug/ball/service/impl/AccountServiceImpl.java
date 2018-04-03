package com.tbug.ball.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tbug.ball.service.AccountService;
import com.tbug.ball.service.ServiceException;
import com.tbug.ball.service.Dto.pay.CustomerWithdrawDto;
import com.tbug.ball.service.biz.account.CustomerAccountBiz;
import com.tbug.ball.service.biz.cash.AccountTransferBiz;
import com.tbug.ball.service.biz.pay.ChargeRecordBiz;
import com.tbug.ball.service.biz.pay.CustomerWithdrawBiz;
import com.tbug.ball.service.biz.pay.PaymentChannelBiz;
import com.tbug.ball.service.biz.pay.WithdrawRecordBiz;
import com.tbug.ball.service.biz.user.CustomerBiz;
import com.tbug.ball.service.biz.user.MemberBiz;
import com.tbug.ball.service.common.DBContants.ChargeRecordConst;
import com.tbug.ball.service.common.DBContants.CustomerAccountConst;
import com.tbug.ball.service.common.DBContants.CustomerConst;
import com.tbug.ball.service.common.DBContants.DB;
import com.tbug.ball.service.common.DBContants.PaymentChannelConst;
import com.tbug.ball.service.common.DBContants.WithdrawRecordConst;
import com.tbug.ball.service.common.factory.CodeFactory;
import com.tbug.ball.service.model.account.CustomerAccount;
import com.tbug.ball.service.model.pay.ChargeRecord;
import com.tbug.ball.service.model.pay.CustomerWithdraw;
import com.tbug.ball.service.model.pay.PaymentChannel;
import com.tbug.ball.service.model.pay.WithdrawRecord;
import com.tbug.ball.service.model.user.Customer;
import com.tbug.ball.service.model.user.Member;

@Service
public class AccountServiceImpl implements AccountService {
	private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
	
	@Autowired
	CustomerBiz customerBiz;
	@Autowired
	MemberBiz memberBiz;
	@Autowired
	AccountTransferBiz accountTransferBiz;
	@Autowired
	CodeFactory codeFactory;
	@Autowired
	ChargeRecordBiz chargeRecordBiz;
	@Autowired
	WithdrawRecordBiz withdrawRecordBiz;
	@Autowired
	CustomerWithdrawBiz customerWithdrawBiz;
	@Autowired
	PaymentChannelBiz paymentChannelBiz;
	@Autowired
	CustomerAccountBiz customerAccountBiz;
	
	@Override
	@Transactional(rollbackFor={Exception.class})
	public void customerCharge(Integer customerId, BigDecimal amount, String creater,String operateInfo,Integer channelId) throws ServiceException {
		try {
			Customer customer = customerBiz.getCustomerById(customerId);
			if (null == customer){
				throw new ServiceException("用户不存在");
			}
			Member member = memberBiz.getMemberById(customer.getMemberId());
			
			String transcationCode = codeFactory.getTranscationCode();
			if (!accountTransferBiz.customerPay(transcationCode, customerId, amount)){
				throw new ServiceException("客户充值失败");
			}
			
			ChargeRecord chargeRecord = new ChargeRecord();
			chargeRecord.setAmount(amount);
			chargeRecord.setConfirmAmount(amount);
			chargeRecord.setChargeCode(codeFactory.getChargeCode());
			chargeRecord.setChargeType(ChargeRecordConst.CHARGE_TYPE_1);
			chargeRecord.setCreateDate(new Date());
			chargeRecord.setCreater(creater);
			chargeRecord.setCustomerCode(customer.getCustomerCode());
			chargeRecord.setCustomerId(customer.getId());
			chargeRecord.setFinishTime(new Date());
			chargeRecord.setMemberCode(member.getMemberCode());
			chargeRecord.setStatus(ChargeRecordConst.STATUS_3);
			chargeRecord.setChannelId(channelId);
			chargeRecord.setOperateInfo(operateInfo);
			
			if (chargeRecordBiz.create(chargeRecord) != 1){
				throw new ServiceException("入金记录创建失败");
			}
		} catch (Exception e){
			logger.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public void charegeSubmit(Integer customerId, BigDecimal amount, String creater, String remark, Integer channelId)
			throws ServiceException {
		try {
			Customer customer = customerBiz.getCustomerById(customerId);
			if (null == customer){
				throw new ServiceException("用户不存在");
			}
			Member member = memberBiz.getMemberById(customer.getMemberId());
			
			ChargeRecord chargeRecord = new ChargeRecord();
			chargeRecord.setAmount(amount);
			chargeRecord.setChargeCode(codeFactory.getChargeCode());
			chargeRecord.setChargeType(ChargeRecordConst.CHARGE_TYPE_2);
			chargeRecord.setCreateDate(new Date());
			chargeRecord.setCreater(creater);
			chargeRecord.setCustomerCode(customer.getCustomerCode());
			chargeRecord.setCustomerId(customer.getId());
			chargeRecord.setFinishTime(new Date());
			chargeRecord.setMemberCode(member.getMemberCode());
			chargeRecord.setStatus(ChargeRecordConst.STATUS_1);
			chargeRecord.setChannelId(channelId);
			chargeRecord.setRemark(remark);
			
			if (chargeRecordBiz.create(chargeRecord) != 1){
				throw new ServiceException("入金记录创建失败");
			}
		} catch (Exception e){
			logger.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage());
		}
		
	}
	
	@Override
	@Transactional(rollbackFor={Exception.class})
	public void customerWithdraw(int channelId, Integer customerId, BigDecimal amount, BigDecimal fee, String creater) throws ServiceException {
		try {
			Customer customer = customerBiz.getCustomerById(customerId);
			if (null == customer){
				throw new ServiceException("用户不存在");
			}
			Member member = memberBiz.getMemberById(customer.getMemberId());
			
			String transcationCode = codeFactory.getTranscationCode();
			if (!accountTransferBiz.customerWithdraw(customerId,amount,fee,transcationCode)){
				throw new ServiceException("客户提现失败");
			}
			
			WithdrawRecord withdrawRecord = new WithdrawRecord();
			withdrawRecord.setChannelId(channelId);
			withdrawRecord.setCustomerCode(customer.getCustomerCode());
			withdrawRecord.setCustomerId(customer.getId());
			withdrawRecord.setApplyDate(new Date());
			withdrawRecord.setFinishDate(new Date());
			withdrawRecord.setMemberCode(member.getMemberCode());
			withdrawRecord.setStatus(WithdrawRecordConst.STATUS_3);
			withdrawRecord.setWithdrawAmount(amount);
			withdrawRecord.setWithdrawFee(fee);
			withdrawRecord.setWithdrawCode(codeFactory.getWithdrawCode());
			withdrawRecord.setWithdrawType(WithdrawRecordConst.WITHDRAW_TYPE_1);
			withdrawRecord.setOperater(creater);
			withdrawRecord.setChannelId(DB.NULL_INT);
			withdrawRecord.setRemark(DB.NULL_STR);
			withdrawRecord.setOperateInfo(DB.NULL_STR);
			if (withdrawRecordBiz.create(withdrawRecord) != 1){
				throw new ServiceException("出金记录创建失败");
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

	@Override
	public CustomerWithdrawDto getCustomerWithdrawDtoById(Integer id) {
		CustomerWithdrawDto dto  = new CustomerWithdrawDto();
		CustomerWithdraw customerWithdraw = customerWithdrawBiz.getById(id);
		if (null != customerWithdraw){
			BeanUtils.copyProperties(customerWithdraw, dto);
		}
		
		return dto;
	}

	@Override
	public void updCustomerWithdraw(CustomerWithdrawDto dto) throws ServiceException {
		
		CustomerWithdraw customerWithdraw = customerWithdrawBiz.getById(dto.getId());
		if (customerWithdraw == null){
			CustomerWithdraw w = new CustomerWithdraw();
			BeanUtils.copyProperties(dto, w);
			if (customerWithdrawBiz.create(w) != 1){
				throw new ServiceException("出金资料保存失败");
			}
		} else {
			BeanUtils.copyProperties(dto, customerWithdraw);
			if (customerWithdrawBiz.upd(customerWithdraw) != 1){
				throw new ServiceException("出金资料更新失败");
			}
		}
	}

	@Override
	public Integer customerWithdrawTimes(Map<String, Object> params) {
		List<WithdrawRecord> times = withdrawRecordBiz.listByMap(params);
		if (times == null){
			return 0;
		}
		return times.size();
	}

	@Override
	@Transactional(rollbackFor={ServiceException.class})
	public boolean customerWithdraw(Integer customerId,String channelId, String withdrawAmountStr) throws ServiceException {
		try{
			BigDecimal withdrawAmount = new BigDecimal(withdrawAmountStr);
			
			PaymentChannel paymentChannel = null;
			List<PaymentChannel> list = paymentChannelBiz.listByAll();
			for (PaymentChannel pay : list){
				if (pay.getChannelId().equals(Integer.valueOf(channelId))){
					paymentChannel = pay;
					if (!PaymentChannelConst.WITHDRAW_SWITH_1.equals(pay.getWithdrawSwitch())){
						throw new ServiceException("此渠道关闭，请重新选择");
					}
					if (withdrawAmount.compareTo(pay.getWithdrawMin()) < 0){
						throw new ServiceException("提现金额 过小");
					}
					if (withdrawAmount.compareTo(pay.getWithdrawMax()) > 0){
						throw new ServiceException("提现金额 过大");
					}
					break;
				}
			}
			if (paymentChannel == null){
				throw new ServiceException("提现渠道不存在");
			}
			
			CustomerAccount account = customerAccountBiz.getCustomerAccountByCustomerId(customerId);
			if (!CustomerAccountConst.STATUS_NORMAL.equals(account.getStatus())){
				throw new ServiceException("客户资金状态异常");
			}
			if (account.getBalance().compareTo(withdrawAmount.add(paymentChannel.getWithdrawFee())) < 0){
				throw new ServiceException("账户余额不够");
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Map<String, Object> params = new HashMap<>();
			params.put("applyDateStart", sdf.format(new Date()) + " 00:00:00");
			params.put("applyDateStart", sdf.format(new Date()) + " 23:59:59");
			params.put("customerId", customerId);
			params.put("channelId", Integer.valueOf(channelId));
			List<WithdrawRecord> times = withdrawRecordBiz.listByMap(params);
			if (times != null && times.size() >= paymentChannel.getWithdrawTimes()){
				throw new ServiceException("当日提现次数已用完");
			}
			
			Customer customer = customerBiz.getCustomerById(customerId);
			if (!CustomerConst.STATUS_NORMAL.equals(customer.getStatus())){
				throw new ServiceException("客户状态异常"); 
			}
			Member member = memberBiz.getMemberById(customer.getMemberId());
			
			WithdrawRecord withdrawRecord = new WithdrawRecord();
			withdrawRecord.setApplyDate(new Date());
			withdrawRecord.setChannelId(Integer.valueOf(channelId));
			withdrawRecord.setCustomerId(customerId);
			withdrawRecord.setCustomerCode(customer.getCustomerCode());
			withdrawRecord.setMemberCode(member.getMemberCode());
			withdrawRecord.setWithdrawAmount(withdrawAmount);
			withdrawRecord.setWithdrawFee(paymentChannel.getWithdrawFee());
			withdrawRecord.setWithdrawCode(codeFactory.getWithdrawCode());
			withdrawRecord.setWithdrawType(WithdrawRecordConst.WITHDRAW_TYPE_2);
			withdrawRecord.setStatus(WithdrawRecordConst.STATUS_1);
			withdrawRecord.setRemark("-");
			
			if (withdrawRecordBiz.create(withdrawRecord) != 1){
				throw new ServiceException("出金异常");
			}
			
			// 划转资金
			accountTransferBiz.customerWithdraw(customerId, withdrawRecord.getWithdrawAmount(), withdrawRecord.getWithdrawFee(), codeFactory.getTranscationCode());
			
		} catch (Exception e){
			logger.error(e.getMessage(), e);
			throw new ServiceException();
		}
		
		return true;
	}

}
