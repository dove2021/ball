package com.tbug.ball.service.biz.cash;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tbug.ball.service.biz.BizException;
import com.tbug.ball.service.biz.account.CustomerAccountBiz;
import com.tbug.ball.service.biz.account.MemberAccountBiz;
import com.tbug.ball.service.common.DBContants.BrokerAccountFlowConst;
import com.tbug.ball.service.common.DBContants.CustomerAccountFlowConst;
import com.tbug.ball.service.common.DBContants.MemberAccountFlowConst;
import com.tbug.ball.service.common.DBContants.TradeAccountFlowConst;
import com.tbug.ball.service.common.factory.CodeFactory;
import com.tbug.ball.service.dao.user.CustomerMapper;
import com.tbug.ball.service.model.account.BrokerAccount;
import com.tbug.ball.service.model.account.CustomerAccount;
import com.tbug.ball.service.model.account.MemberAccount;
import com.tbug.ball.service.model.flow.BrokerAccountFlow;
import com.tbug.ball.service.model.flow.CustomerAccountFlow;
import com.tbug.ball.service.model.flow.MemberAccountFlow;
import com.tbug.ball.service.model.flow.TradeAccountFlow;
import com.tbug.ball.service.model.order.OrderClean;

@Service
public class AccountTransferBiz {
	private static final Logger logger = LoggerFactory.getLogger(AccountTransferBiz.class);
	
	@Resource
	BaseAccountTransferBiz baseAccountTransferBiz;
	@Resource
	CodeFactory codeFactory;
	@Resource
	CustomerMapper customerMapper;
	@Resource
	CustomerAccountBiz customerAccountBiz;
	@Resource
	MemberAccountBiz memberAccountBiz;
	
	/**
	 * 客户充值
	 * @return
	 * @throws BizException
	 */
	public boolean customerPay(String transcationCode,Integer customerId, BigDecimal amount) throws BizException{
		
		CustomerAccount customerAccount = customerAccountBiz.getCustomerAccountByCustomerId(customerId);
		
		CustomerAccountFlow customerAccountFlow = new CustomerAccountFlow();
		customerAccountFlow.setAccountId(customerAccount.getId());
		customerAccountFlow.setAmount(amount);
		customerAccountFlow.setTransactionCode(transcationCode);
		customerAccountFlow.setTradeTime(new Date());
		customerAccountFlow.setTradeType(CustomerAccountFlowConst.TRADE_TYPE_CHARGE_IN);
		
		try {
			if (!baseAccountTransferBiz.customerCashAccountChange(customerAccountFlow)){
				throw new BizException("客户资金账户变更失败");
			}
			
		} catch (BizException e) {
			logger.error(e.getMessage(), e);
			throw new BizException(e.getMessage());
		}
		
		return true;
	}
	
	/**
	 * 客户提现
	 */
	public boolean customerWithdraw(Integer customerId, BigDecimal amount, String transcationCode) throws BizException{
		CustomerAccount customerAccount = customerAccountBiz.getCustomerAccountByCustomerId(customerId);
		
		CustomerAccountFlow customerAccountFlow = new CustomerAccountFlow();
		customerAccountFlow.setAccountId(customerAccount.getId());
		customerAccountFlow.setAmount(amount);
		customerAccountFlow.setTransactionCode(transcationCode);
		customerAccountFlow.setTradeTime(new Date());
		customerAccountFlow.setTradeType(CustomerAccountFlowConst.TRADE_TYPE_WITHDRAW_OUT);
		
		try {
			if (!baseAccountTransferBiz.customerCashAccountChange(customerAccountFlow)){
				throw new BizException("客户资金账户变更失败");
			}
		} catch (BizException e) {
			logger.error(e.getMessage(), e);
			throw new BizException(e.getMessage());
		}
		
		return true;
	}
	
	/**
	 * 代理商提现
	 * @throws BizException
	 */
	public void brokerWithDraw(BigDecimal amt, String transcationCode) throws BizException{
		
		BrokerAccountFlow brokerAccountFlow = new BrokerAccountFlow();
		brokerAccountFlow.setAccountId(1);
		brokerAccountFlow.setAmount(amt);
		brokerAccountFlow.setTradeTime(new Date());
		brokerAccountFlow.setTradeType(BrokerAccountFlowConst.TRADE_TYPE_WITHDRAW_OUT);
		brokerAccountFlow.setTransactionCode(transcationCode);
		
		baseAccountTransferBiz.brokerCashAccountChange(brokerAccountFlow);
	}
	
	/**
	 * 会员入金
	 * @throws BizException
	 */
	public void memberCharge(Integer memberId,BigDecimal amt, String transcationCode) throws BizException{
		MemberAccount memberAccount = memberAccountBiz.getMemberAccountByMemberId(memberId);
		if (null == memberAccount){
			throw new BizException("会员账户不存在");
		}
		MemberAccountFlow memberAccountFlow = new MemberAccountFlow();
		memberAccountFlow.setAccountId(memberAccount.getId());
		memberAccountFlow.setAmount(amt);
		memberAccountFlow.setTradeTime(new Date());
		memberAccountFlow.setTradeType(MemberAccountFlowConst.TRADE_TYPE_CHARGE_IN);
		memberAccountFlow.setTransactionCode(transcationCode);
		
		baseAccountTransferBiz.memberCashAccountChange(memberAccountFlow);
	}
	
	/**
	 * 会员提现
	 * @throws BizException
	 */
	public void memberWithdraw(Integer memberId,BigDecimal amt, String transcationCode) throws BizException{
		MemberAccount memberAccount = memberAccountBiz.getMemberAccountByMemberId(memberId);
		if (null == memberAccount){
			throw new BizException("会员账户不存在");
		}
		MemberAccountFlow memberAccountFlow = new MemberAccountFlow();
		memberAccountFlow.setAccountId(memberAccount.getId());
		memberAccountFlow.setAmount(amt);
		memberAccountFlow.setTradeTime(new Date());
		memberAccountFlow.setTradeType(MemberAccountFlowConst.TRADE_TYPE_WITHDRAW_OUT);
		memberAccountFlow.setTransactionCode(transcationCode);
		
		baseAccountTransferBiz.memberCashAccountChange(memberAccountFlow);
	}
	
	/**
	 * 客户输，结算给会员
	 * @throws BizException 
	 */
	public void memberByOrderClean(MemberAccount memberAccount, BigDecimal amount) throws BizException{
		String transactionCode = codeFactory.getTranscationCode();
		
		// 资金池- 
		TradeAccountFlow tradeAccountFlow = new TradeAccountFlow();
		tradeAccountFlow.setAccountId(1);
		tradeAccountFlow.setAmount(amount);
		tradeAccountFlow.setTradeTime(new Date());
		tradeAccountFlow.setTradeType(TradeAccountFlowConst.TRADE_TYPE_CLEAN_MEMBER_OUT);
		tradeAccountFlow.setTransactionCode(transactionCode);
		baseAccountTransferBiz.tradeCashAccountChange(tradeAccountFlow);
		
		// 会员+
		MemberAccountFlow memberAccountFlow = new MemberAccountFlow();
		memberAccountFlow.setAccountId(memberAccount.getId());
		memberAccountFlow.setAmount(amount);
		memberAccountFlow.setTradeTime(new Date());
		memberAccountFlow.setTradeType(MemberAccountFlowConst.TRADE_TYPE_CLEAN_IN);
		memberAccountFlow.setTransactionCode(transactionCode);
		baseAccountTransferBiz.memberCashAccountChange(memberAccountFlow);
	}
	
	/**
	 * 客户输，资金池分佣给代理商
	 * @throws BizException 
	 */
	public void brokerByOrderClean(String transactionCode,BigDecimal amount) throws BizException{
		if (null == amount || amount.compareTo(BigDecimal.ZERO) <= 0){
			throw new BizException("交易资金不能为0或负");
		}
		
		// 资金池- 
		TradeAccountFlow tradeAccountFlow = new TradeAccountFlow();
		tradeAccountFlow.setAccountId(1);
		tradeAccountFlow.setAmount(amount);
		tradeAccountFlow.setTradeTime(new Date());
		tradeAccountFlow.setTradeType(TradeAccountFlowConst.TRADE_TYPE_CLEAN_BROKER_OUT);
		tradeAccountFlow.setTransactionCode(transactionCode);
		baseAccountTransferBiz.tradeCashAccountChange(tradeAccountFlow);
	}
	
	/**
	 * 客户输，结算给代理商
	 * @throws BizException 
	 */
	public void brokerByOrderClean(String transactionCode, BrokerAccount brokerAccount, BigDecimal amount) throws BizException{
		if (null == amount || amount.compareTo(BigDecimal.ZERO) <= 0){
			throw new BizException("交易资金不能为0或负");
		}
		// 代理商+
		BrokerAccountFlow brokerAccountFlow = new BrokerAccountFlow();
		brokerAccountFlow.setAccountId(brokerAccount.getId());
		brokerAccountFlow.setAmount(amount);
		brokerAccountFlow.setTradeTime(new Date());
		brokerAccountFlow.setTradeType(BrokerAccountFlowConst.TRADE_TYPE_CLEAN_IN);
		brokerAccountFlow.setTransactionCode(transactionCode);
		baseAccountTransferBiz.brokerCashAccountChange(brokerAccountFlow);
	}
	
	/**
	 * 交易所提现
	 * @return
	 * @throws BizException 
	 */
	public boolean TradeWithdraw(BigDecimal amount, String transactionCode) throws BizException{
		if (null == amount || amount.compareTo(BigDecimal.ZERO) <= 0){
			throw new BizException("交易资金不能为0或负");
		}
		TradeAccountFlow tradeAccountFlow = new TradeAccountFlow();
		tradeAccountFlow.setAccountId(2);
		tradeAccountFlow.setAmount(amount);
		tradeAccountFlow.setTransactionCode(transactionCode);
		tradeAccountFlow.setTradeTime(new Date());
		tradeAccountFlow.setTradeType(TradeAccountFlowConst.TRADE_TYPE_WITHDRAW_OUT);
		try {
			baseAccountTransferBiz.tradeCashAccountChange(tradeAccountFlow);
		} catch (BizException e) {
			logger.error(e.getMessage(), e);
			throw new BizException(e.getMessage(), e);
		}
		return true;
	}
	
	/**
	 * 用户下单
	 * @param customerAccount 	客户资金账户
	 * @param memberAccount		会员资金账户
	 * @param memberFee			会员手续费
	 * @param tradeFee			交易所手续费
	 * @param totalPrice		总押注费用
	 * @param totalAmount		总费用
	 * @param preAmount			预付费用
	 * @return
	 * @throws BizException
	 */
	@Transactional
	public boolean customerStake(CustomerAccount customerAccount, 
								 MemberAccount memberAccount, 
								 BigDecimal memberFee,
								 BigDecimal tradeFee,
								 BigDecimal totalPrice,
								 BigDecimal totalAmount,
								 BigDecimal preAmount) throws BizException{
		String transactionCode = codeFactory.getTranscationCode();
		
		// 用户扣款- 下注金额
		CustomerAccountFlow customerAccountFlow = new CustomerAccountFlow();
		customerAccountFlow.setAccountId(customerAccount.getId());
		customerAccountFlow.setAmount(totalPrice);
		customerAccountFlow.setTradeTime(new Date());
		customerAccountFlow.setTradeType(CustomerAccountFlowConst.TRADE_TYPE_STAKE_OUT);
		customerAccountFlow.setTransactionCode(transactionCode);
		baseAccountTransferBiz.customerCashAccountChange(customerAccountFlow);
		
		// 用户扣款- 手续费
		CustomerAccountFlow customerAccountFlow1 = new CustomerAccountFlow();
		customerAccountFlow1.setAccountId(customerAccount.getId());
		customerAccountFlow1.setAmount(memberFee.add(tradeFee));
		customerAccountFlow1.setTradeTime(new Date());
		customerAccountFlow1.setTradeType(CustomerAccountFlowConst.TRADE_TYPE_FEE_OUT);
		customerAccountFlow1.setTransactionCode(transactionCode);
		baseAccountTransferBiz.customerCashAccountChange(customerAccountFlow1);
		
		// 资金池+ 下注金额
		TradeAccountFlow tradeAccountFlow = new TradeAccountFlow();
		tradeAccountFlow.setAccountId(1);
		tradeAccountFlow.setAmount(totalPrice);
		tradeAccountFlow.setTradeTime(new Date());
		tradeAccountFlow.setTradeType(TradeAccountFlowConst.TRADE_TYPE_STAKE_CUSTOMER_IN);
		tradeAccountFlow.setTransactionCode(transactionCode);
		baseAccountTransferBiz.tradeCashAccountChange(tradeAccountFlow);
		
		// 资金池 + 会员预付款
		TradeAccountFlow tradeAccountFlow1 = new TradeAccountFlow();
		tradeAccountFlow1.setAccountId(1);
		tradeAccountFlow1.setAmount(preAmount.subtract(totalPrice));
		tradeAccountFlow1.setTradeTime(new Date());
		tradeAccountFlow1.setTradeType(TradeAccountFlowConst.TRADE_TYPE_STAKE_MEMBER_IN);
		tradeAccountFlow1.setTransactionCode(transactionCode);
		baseAccountTransferBiz.tradeCashAccountChange(tradeAccountFlow1);
		
		// 交易所+ 手续费
		TradeAccountFlow tradeAccountFlow2 = new TradeAccountFlow();
		tradeAccountFlow2.setAccountId(2);
		tradeAccountFlow2.setAmount(tradeFee);
		tradeAccountFlow2.setTradeTime(new Date());
		tradeAccountFlow2.setTradeType(TradeAccountFlowConst.TRADE_TYPE_FEE_IN);
		tradeAccountFlow2.setTransactionCode(transactionCode);
		baseAccountTransferBiz.tradeCashAccountChange(tradeAccountFlow2);
		
		// 会员单位 + 手续费
		MemberAccountFlow memberAccountFlow = new MemberAccountFlow();
		memberAccountFlow.setAccountId(memberAccount.getId());
		memberAccountFlow.setAmount(memberFee);
		memberAccountFlow.setTradeTime(new Date());
		memberAccountFlow.setTradeType(MemberAccountFlowConst.TRADE_TYPE_FEE_IN);
		memberAccountFlow.setTransactionCode(transactionCode);
		baseAccountTransferBiz.memberCashAccountChange(memberAccountFlow);
		
		// 会员单位 - 预付款
		MemberAccountFlow memberAccountFlow1 = new MemberAccountFlow();
		memberAccountFlow1.setAccountId(memberAccount.getId());
		memberAccountFlow1.setAmount(preAmount.subtract(totalPrice));
		memberAccountFlow1.setTradeTime(new Date());
		memberAccountFlow1.setTradeType(MemberAccountFlowConst.TRADE_TYPE_STAKE_OUT);
		memberAccountFlow1.setTransactionCode(transactionCode);
		baseAccountTransferBiz.memberCashAccountChange(memberAccountFlow1);
		
		return true;
	}
	
	/**
	 * 结算: 客户赢
	 * @return
	 * @throws BizException 
	 */
	public boolean stakeCleanByWin(BigDecimal amount) throws BizException{
		if (null == amount || amount.compareTo(BigDecimal.ZERO) <= 0){
			throw new BizException("交易资金不能为0或负");
		}
		
		String transactionCode = codeFactory.getTranscationCode();
		
		TradeAccountFlow tradeAccountFlow = new TradeAccountFlow();
		tradeAccountFlow.setAccountId(1);
		tradeAccountFlow.setAmount(amount);
		tradeAccountFlow.setTradeTime(new Date());
		tradeAccountFlow.setTradeType(TradeAccountFlowConst.TRADE_TYPE_CLEAN_CUSTOMER_OUT);
		tradeAccountFlow.setTransactionCode(transactionCode);
		baseAccountTransferBiz.tradeCashAccountChange(tradeAccountFlow);
		
		return true;
	}
	
	/**
	 * 结算: 客户输
	 * @return
	 * @throws BizException 
	 */
	public boolean stakeCleanByLose(MemberAccount memberAccount, BrokerAccount brokerAccount,BigDecimal brokerAmount, BigDecimal memberAmount) throws BizException{
		
		String transactionCode = codeFactory.getTranscationCode();
		
		// 
		TradeAccountFlow tradeAccountFlow = new TradeAccountFlow();
		tradeAccountFlow.setAccountId(1);
		tradeAccountFlow.setAmount(brokerAmount);
		tradeAccountFlow.setTradeTime(new Date());
		tradeAccountFlow.setTradeType(TradeAccountFlowConst.TRADE_TYPE_CLEAN_BROKER_OUT);
		tradeAccountFlow.setTransactionCode(transactionCode);
		baseAccountTransferBiz.tradeCashAccountChange(tradeAccountFlow);
		
		TradeAccountFlow tradeAccountFlow1 = new TradeAccountFlow();
		tradeAccountFlow1.setAccountId(1);
		tradeAccountFlow1.setAmount(memberAmount);
		tradeAccountFlow1.setTradeTime(new Date());
		tradeAccountFlow1.setTradeType(TradeAccountFlowConst.TRADE_TYPE_CLEAN_MEMBER_OUT);
		tradeAccountFlow1.setTransactionCode(transactionCode);
		baseAccountTransferBiz.tradeCashAccountChange(tradeAccountFlow1);
		
		MemberAccountFlow memberAccountFlow = new MemberAccountFlow();
		memberAccountFlow.setAccountId(memberAccount.getId());
		memberAccountFlow.setAmount(memberAmount);
		memberAccountFlow.setTradeTime(new Date());
		memberAccountFlow.setTradeType(MemberAccountFlowConst.TRADE_TYPE_CLEAN_IN);
		memberAccountFlow.setTransactionCode(transactionCode);
		baseAccountTransferBiz.memberCashAccountChange(memberAccountFlow);
		
		BrokerAccountFlow brokerAccountFlow = new BrokerAccountFlow();
		brokerAccountFlow.setAccountId(brokerAccount.getId());
		brokerAccountFlow.setAmount(brokerAmount);
		brokerAccountFlow.setTradeTime(new Date());
		brokerAccountFlow.setTradeType(BrokerAccountFlowConst.TRADE_TYPE_CLEAN_IN);
		brokerAccountFlow.setTransactionCode(transactionCode);
		baseAccountTransferBiz.brokerCashAccountChange(brokerAccountFlow);
		
		return true;
	}
	
	/**
	 * 客户兑奖
	 * @return
	 * @throws BizException 
	 */
	public boolean customerToCash(CustomerAccount customerAccount, OrderClean orderClean) throws BizException{
		
		String transactionCode = codeFactory.getTranscationCode();
		
		TradeAccountFlow tradeAccountFlow = new TradeAccountFlow();
		tradeAccountFlow.setAccountId(1);
		tradeAccountFlow.setAmount(orderClean.getCleanAmount());
		tradeAccountFlow.setTradeTime(new Date());
		tradeAccountFlow.setTradeType(TradeAccountFlowConst.TRADE_TYPE_CLEAN_CUSTOMER_OUT);
		tradeAccountFlow.setTransactionCode(transactionCode);
		baseAccountTransferBiz.tradeCashAccountChange(tradeAccountFlow);
		
		CustomerAccountFlow customerAccountFlow = new CustomerAccountFlow();
		customerAccountFlow.setAccountId(customerAccount.getId());
		customerAccountFlow.setAmount(orderClean.getCleanAmount());
		customerAccountFlow.setTradeTime(new Date());
		customerAccountFlow.setTradeType(CustomerAccountFlowConst.TRADE_TYPE_CLEAN_IN);
		customerAccountFlow.setTransactionCode(transactionCode);
		baseAccountTransferBiz.customerCashAccountChange(customerAccountFlow);
		
		return true;
	}
}
