package com.tbug.ball.service.biz.cash;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.tbug.ball.service.biz.BizException;
import com.tbug.ball.service.biz.flow.BrokerAccountFlowBiz;
import com.tbug.ball.service.biz.flow.CustomerAccountFlowBiz;
import com.tbug.ball.service.biz.flow.MemberAccountFlowBiz;
import com.tbug.ball.service.biz.flow.TradeAccountFlowBiz;
import com.tbug.ball.service.common.DBContants.BrokerAccountFlowConst;
import com.tbug.ball.service.common.DBContants.CustomerAccountFlowConst;
import com.tbug.ball.service.common.DBContants.MemberAccountFlowConst;
import com.tbug.ball.service.common.DBContants.TradeAccountFlowConst;
import com.tbug.ball.service.dao.account.BrokerAccountMapper;
import com.tbug.ball.service.dao.account.CustomerAccountMapper;
import com.tbug.ball.service.dao.account.MemberAccountMapper;
import com.tbug.ball.service.dao.account.TradeAccountMapper;
import com.tbug.ball.service.model.account.BrokerAccount;
import com.tbug.ball.service.model.account.CustomerAccount;
import com.tbug.ball.service.model.account.MemberAccount;
import com.tbug.ball.service.model.account.TradeAccount;
import com.tbug.ball.service.model.flow.BrokerAccountFlow;
import com.tbug.ball.service.model.flow.CustomerAccountFlow;
import com.tbug.ball.service.model.flow.MemberAccountFlow;
import com.tbug.ball.service.model.flow.TradeAccountFlow;

@Service
public class BaseAccountTransferBiz {
	private static final Logger logger = LoggerFactory.getLogger(BaseAccountTransferBiz.class);
	
	@Resource
	private CustomerAccountMapper customerAccountMapper;
	@Resource
	private CustomerAccountFlowBiz customerAccountFlowBiz;
	@Resource
	private BrokerAccountMapper brokerAccountMapper;
	@Resource
	private BrokerAccountFlowBiz brokerAccountFlowBiz;
	@Resource
	private MemberAccountMapper memberAccountMapper;
	@Resource
	private MemberAccountFlowBiz memberAccountFlowBiz;
	@Resource
	private TradeAccountMapper tradeAccountMapper;
	@Resource
	private TradeAccountFlowBiz tradeAccountFlowBiz;
	
	/**
	 * 客户资金账户变更
	 * @param customerFundFlowList
	 * @return
	 * @throws BizException 
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={BizException.class})
	boolean customerCashAccountChange(CustomerAccountFlow customerAccountFlow) throws BizException{
		try {
			if (null == customerAccountFlow){
				throw new BizException("fundflow.customer.empty");
			}
			
			if (BigDecimal.ZERO.compareTo(customerAccountFlow.getAmount()) >= 0 ){
				throw new BizException("fundflow.customer.amount.zero");
			}
			
			// 锁资金账户表
			Integer accountId = customerAccountFlow.getAccountId();
			CustomerAccount customerAccount = customerAccountMapper.selectByPrimaryKeyForUpdate(accountId);
			if (null == customerAccount){
				throw new BizException("fundflow.customer.account.not.exist");
			}
			
			// 计算余额
			BigDecimal oldBalance = customerAccount.getBalance();
			BigDecimal amount = customerAccountFlow.getAmount();
			BigDecimal newBalance = null;
			if (CustomerAccountFlowConst.TRADE_TYPE_CHARGE_IN.equals(customerAccountFlow.getTradeType())
					|| CustomerAccountFlowConst.TRADE_TYPE_CLEAN_IN.equals(customerAccountFlow.getTradeType())
					|| CustomerAccountFlowConst.TRADE_TYPE_WITHDRAW_IN.equals(customerAccountFlow.getTradeType())
					|| CustomerAccountFlowConst.TRADE_TYPE_WITHDRAW_FEE_IN.equals(customerAccountFlow.getTradeType())){
				
				newBalance = oldBalance.add(amount);
			}
			else if (CustomerAccountFlowConst.TRADE_TYPE_STAKE_OUT.equals(customerAccountFlow.getTradeType())
				   || CustomerAccountFlowConst.TRADE_TYPE_WITHDRAW_OUT.equals(customerAccountFlow.getTradeType())
				   || CustomerAccountFlowConst.TRADE_TYPE_FEE_OUT.equals(customerAccountFlow.getTradeType())
				   || CustomerAccountFlowConst.TRADE_TYPE_WITHDRAW_FEE_OUT.equals(customerAccountFlow.getTradeType())){
				   
				newBalance = oldBalance.subtract(amount);
				amount = amount.negate();
			}
			else {
				throw new BizException("fundflow.customer.tradetype.not.exist");
			}
			
			// 余额不能为负数
			if (newBalance.compareTo(new BigDecimal(0)) < 0){
				throw new BizException("fundflow.customer.account.balance.less");
			}
			
			// 新增资金流水
			customerAccountFlow.setAmount(amount);
			customerAccountFlow.setBalance(newBalance);
			customerAccountFlow.setStatus("1");
			
			customerAccountFlowBiz.createCustomerAccountFlow(customerAccountFlow);
			
			// 更新资金账户余额
			customerAccount.setBalance(newBalance);
			customerAccountMapper.updateByPrimaryKeySelective(customerAccount);
			
		} catch (Exception e){
			logger.error(e.getMessage(), e);
			throw new BizException(e.getMessage());
		}
		
		return true;
	}

	/**
	 * 代理商资金账户变更
	 * 
	 * @param brokerFundFlowList
	 * @return
	 * @throws BizException 
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={BizException.class})
	boolean brokerCashAccountChange(BrokerAccountFlow brokerAccountFlow) throws BizException{
		try {
			if (null == brokerAccountFlow){
				throw new BizException("fundflow.broker.empty");
			}
			
			if (BigDecimal.ZERO.compareTo(brokerAccountFlow.getAmount()) >= 0 ){
				throw new BizException("fundflow.broker.amount.zero");
			}
			
			// 锁资金账户表
			BrokerAccount brokerAccount  = brokerAccountMapper.selectByPrimaryKeyForUpdate(brokerAccountFlow.getAccountId());
			if (null == brokerAccount){
				throw new BizException("fundflow.broker.account.not.exist");
			}
			
			// 计算余额
			BigDecimal oldBalance = brokerAccount.getBalance();
			BigDecimal amount = brokerAccountFlow.getAmount();
			BigDecimal newBalance = null;
			if (BrokerAccountFlowConst.TRADE_TYPE_CLEAN_IN.equals(brokerAccountFlow.getTradeType())){
				
				newBalance = oldBalance.add(amount);
			}
			else if (BrokerAccountFlowConst.TRADE_TYPE_WITHDRAW_OUT .equals(brokerAccountFlow.getTradeType())){
				
				newBalance = oldBalance.subtract(newBalance);
				amount = amount.negate();
			}
			else {
				throw new BizException("fundflow.broker.tradetype.not.exist");
			}
	
			// 余额不能为负数
			if (newBalance.compareTo(new BigDecimal(0)) < 0){
				throw new BizException("fundflow.customer.account.balance.less");
			}
			
			// 新增资金流水
			brokerAccountFlow.setAmount(amount);
			brokerAccountFlow.setBalance(newBalance);
			brokerAccountFlow.setStatus("1");
			brokerAccountFlowBiz.createBrokerAccountFlow(brokerAccountFlow);
			
			// 更新资金账户余额
			brokerAccount.setBalance(newBalance);
			brokerAccountMapper.updateByPrimaryKeySelective(brokerAccount);
			
		} catch (Exception e){
			logger.error(e.getMessage(), e);
			throw new BizException(e.getMessage());
		}
		
		return true;
	}
	
	/**
	 * 会员资金账户变更
	 * @param sellerFundFlowList
	 * @return
	 * @throws BizException 
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={BizException.class})
	boolean memberCashAccountChange(MemberAccountFlow memberAccountFlow) throws BizException{
		try{
			if (null == memberAccountFlow){
				throw new BizException("fundflow.member.empty");
			}
			
			if (BigDecimal.ZERO.compareTo(memberAccountFlow.getAmount()) >= 0 ){
				throw new BizException("fundflow.member.amount.zero");
			}
			
			// 锁资金账户表
			MemberAccount memberAccount  = memberAccountMapper.selectByPrimaryKeyForUpdate(memberAccountFlow.getAccountId());
			if (null == memberAccount){
				throw new BizException("fundflow.member.account.not.exist");
			}
			
			// 计算余额
			BigDecimal oldBalance = memberAccount.getBalance();
			BigDecimal amount = memberAccountFlow.getAmount();
			BigDecimal newBalance = null;
			if (MemberAccountFlowConst.TRADE_TYPE_FEE_IN.equals(memberAccountFlow.getTradeType())
					|| MemberAccountFlowConst.TRADE_TYPE_CLEAN_IN.equals(memberAccountFlow.getTradeType())
					|| MemberAccountFlowConst.TRADE_TYPE_CHARGE_IN.equals(memberAccountFlow.getTradeType())){
				
				newBalance = oldBalance.add(amount);
			}
			else if (MemberAccountFlowConst.TRADE_TYPE_STAKE_OUT.equals(memberAccountFlow.getTradeType())
				   || MemberAccountFlowConst.TRADE_TYPE_WITHDRAW_OUT.equals(memberAccountFlow.getTradeType())){
				
				newBalance = oldBalance.subtract(amount);
				amount = amount.negate();
			}
			else {
				throw new BizException("fundflow.member.tradetype.not.exist");
			}
	
			// 余额不能为负数
			if (newBalance.compareTo(new BigDecimal(0)) < 0){
				throw new BizException("fundflow.member.account.balance.less");
			}
			
			// 新增资金流水
			memberAccountFlow.setAmount(amount);
			memberAccountFlow.setBalance(newBalance);
			memberAccountFlow.setStatus("1");
			memberAccountFlowBiz.createMemberAccountFlow(memberAccountFlow);
			
			// 更新资金账户余额
			memberAccount.setBalance(newBalance);
			memberAccountMapper.updateByPrimaryKey(memberAccount);
			
		} catch (Exception e){
			logger.error(e.getMessage(), e);
			throw new BizException(e.getMessage());
		}
		
		return true;
	}
	
	/**
	 * 交易所资金账户变更
	 * @param tradeFundFlow
	 * @return
	 * @throws BizException 
	 */
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor={BizException.class})
	boolean tradeCashAccountChange(TradeAccountFlow tradeAccountFlow) throws BizException{
		try{
			if (null == tradeAccountFlow){
				throw new BizException("fundflow.trade.empty");
			}
			
			if (BigDecimal.ZERO.compareTo(tradeAccountFlow.getAmount()) >= 0 ){
				throw new BizException("fundflow.trade.amount.zero");
			}
			
			// 锁资金账户表
			TradeAccount tradeAccount = tradeAccountMapper.selectByPrimaryKeyForUpdate(tradeAccountFlow.getAccountId());
			if (null == tradeAccount){
				throw new BizException("fundflow.trade.account.not.exist");
			}
			
			// 计算余额
			BigDecimal oldBalance = tradeAccount.getBalance();
			BigDecimal amount = tradeAccountFlow.getAmount();
			BigDecimal newBalance = null;
			if (TradeAccountFlowConst.TRADE_TYPE_FEE_IN.equals(tradeAccountFlow.getTradeType())
			  || TradeAccountFlowConst.TRADE_TYPE_STAKE_CUSTOMER_IN.equals(tradeAccountFlow.getTradeType())
			  || TradeAccountFlowConst.TRADE_TYPE_STAKE_MEMBER_IN.equals(tradeAccountFlow.getTradeType())
			  || TradeAccountFlowConst.TRADE_TYPE_CUSTOMER_WITHDRAW_FEE_IN.equals(tradeAccountFlow.getTradeType())){
				
				newBalance = oldBalance.add(amount);
			}
			else if (TradeAccountFlowConst.TRADE_TYPE_CLEAN_BROKER_OUT.equals(tradeAccountFlow.getTradeType())
				   || TradeAccountFlowConst.TRADE_TYPE_CLEAN_CUSTOMER_OUT.equals(tradeAccountFlow.getTradeType())
				   || TradeAccountFlowConst.TRADE_TYPE_CLEAN_MEMBER_OUT.equals(tradeAccountFlow.getTradeType())
				   || TradeAccountFlowConst.TRADE_TYPE_WITHDRAW_OUT.equals(tradeAccountFlow.getTradeType())
				   || TradeAccountFlowConst.TRADE_TYPE_CUSTOMER_WITHDRAW_FEE_OUT.equals(tradeAccountFlow.getTradeType())){
				
				newBalance = oldBalance.subtract(amount);
				amount = amount.negate();
			}
			else {
				throw new BizException("fundflow.trade.tradetype.not.exist");
			}
			
			// 余额为负数,警告
			if (newBalance.compareTo(new BigDecimal(0)) < 0){
				logger.warn("交易所资金账户资金为负数,资金流水: {}", JSON.toJSONString(tradeAccountFlow));
			}
			
			// 新增资金流水
			tradeAccountFlow.setAmount(amount);
			tradeAccountFlow.setBalance(newBalance);
			tradeAccountFlow.setStatus("1");
			tradeAccountFlowBiz.createTradeAccountFlow(tradeAccountFlow);
			
			// 更新资金账户余额
			tradeAccount.setBalance(newBalance);
			tradeAccountMapper.updateByPrimaryKey(tradeAccount);
			
		} catch (Exception e){
			logger.error(e.getMessage(), e);
			throw new BizException(e.getMessage());
		}
		
		return true;
	}
}
