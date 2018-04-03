package com.tbug.ball.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.tbug.ball.service.OrderService;
import com.tbug.ball.service.ServiceException;
import com.tbug.ball.service.Dto.order.OrderCleanDto;
import com.tbug.ball.service.Dto.order.OrderCleanExDto;
import com.tbug.ball.service.Dto.order.StakeOrderExDto;
import com.tbug.ball.service.Dto.order.StakeOrderHDto;
import com.tbug.ball.service.Dto.schedule.PreOrderDto;
import com.tbug.ball.service.biz.BizException;
import com.tbug.ball.service.biz.account.BrokerAccountBiz;
import com.tbug.ball.service.biz.account.CustomerAccountBiz;
import com.tbug.ball.service.biz.account.MemberAccountBiz;
import com.tbug.ball.service.biz.cash.AccountTransferBiz;
import com.tbug.ball.service.biz.order.OrderCleanBiz;
import com.tbug.ball.service.biz.order.StakeOrderBiz;
import com.tbug.ball.service.biz.order.StakeOrderHBiz;
import com.tbug.ball.service.biz.schedule.ScheduleBiz;
import com.tbug.ball.service.biz.schedule.StakeBiz;
import com.tbug.ball.service.biz.user.BrokerBiz;
import com.tbug.ball.service.biz.user.CustomerBiz;
import com.tbug.ball.service.biz.user.MemberBiz;
import com.tbug.ball.service.common.DBContants.CustomerAccountConst;
import com.tbug.ball.service.common.DBContants.CustomerConst;
import com.tbug.ball.service.common.DBContants.MemberAccountConst;
import com.tbug.ball.service.common.DBContants.MemberConst;
import com.tbug.ball.service.common.DBContants.OrderCleanConst;
import com.tbug.ball.service.common.DBContants.ScheduleConst;
import com.tbug.ball.service.common.DBContants.StakeOrderConst;
import com.tbug.ball.service.common.DBContants.StakeOrderHConst;
import com.tbug.ball.service.common.factory.CodeFactory;
import com.tbug.ball.service.model.account.BrokerAccount;
import com.tbug.ball.service.model.account.CustomerAccount;
import com.tbug.ball.service.model.account.MemberAccount;
import com.tbug.ball.service.model.order.OrderClean;
import com.tbug.ball.service.model.order.OrderCleanEx;
import com.tbug.ball.service.model.order.StakeOrder;
import com.tbug.ball.service.model.order.StakeOrderEx;
import com.tbug.ball.service.model.order.StakeOrderH;
import com.tbug.ball.service.model.schedule.Schedule;
import com.tbug.ball.service.model.schedule.Stake;
import com.tbug.ball.service.model.user.Broker;
import com.tbug.ball.service.model.user.Customer;
import com.tbug.ball.service.model.user.Member;

@Service
public class OrderServiceImpl implements OrderService {
	private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	@Autowired
	CustomerBiz customerBiz;
	@Autowired
	CustomerAccountBiz customerAccountBiz;
	@Autowired
	ScheduleBiz scheduleBiz;
	@Autowired
	MemberBiz memberBiz;
	@Autowired
	MemberAccountBiz memberAccountBiz;
	@Autowired
	StakeBiz stakeBiz;
	@Autowired
	AccountTransferBiz accountTransferBiz;
	@Autowired
	BrokerBiz brokerBiz;
	@Autowired
	StakeOrderBiz stakeOrderBiz;
	@Autowired
	OrderCleanBiz orderCleanBiz;
	@Autowired
	BrokerAccountBiz brokerAccountBiz;
	@Autowired
	StakeOrderHBiz stakeOrderHBiz;
	@Autowired
	CodeFactory codeFactory;
	
	/**
	 * 下注
	 */
	@Override
	@Transactional(rollbackFor={ServiceException.class})
	public boolean stakeOpen(PreOrderDto preOrderDto) throws ServiceException {
		try {
			if (preOrderDto == null){
				throw new ServiceException("下注信息不能为空");
			}
			// 基础校验
			Customer customer = customerBiz.getCustomerById(preOrderDto.getCustomerId());
			if (null == customer){
				throw new ServiceException("客户不能为空");
			}
			if (!CustomerConst.STATUS_NORMAL.equals(customer.getStatus())){
				throw new ServiceException("客户状态异常");
			}
			CustomerAccount customerAccount = customerAccountBiz.getCustomerAccountByCustomerId(preOrderDto.getCustomerId());
			if (!CustomerAccountConst.STATUS_NORMAL.equals(customerAccount.getStatus())){
				throw new ServiceException("客户资金状态异常");
			}
			Schedule schedule = scheduleBiz.getScheduleById(preOrderDto.getScheduleId());
			if (!ScheduleConst.STATUS_START.equals(schedule.getStatus())){
				throw new ServiceException("此赛程暂不支持下注");
			}
			Member member = memberBiz.getMemberById(customer.getMemberId());
			if (!MemberConst.STATUS_NORMAL.equals(member.getStatus())){
				throw new ServiceException("会员状态异常");
			}
			MemberAccount memberAccount = memberAccountBiz.getMemberAccountByMemberId(member.getId());
			if (!MemberAccountConst.STATUS_NORMAL.equals(memberAccount.getStatus())){
				throw new ServiceException("会员资金状态异常");
			}
			
			// 资金校验
			Stake stake = stakeBiz.getStakeByCode(preOrderDto.getStakeCode());
			if (stake == null){
				throw new ServiceException("下注标的不存在");
			}
			int orderNum = preOrderDto.getOrderNum();
			if (orderNum > stake.getStakeLimitQuantity()){
				throw new ServiceException("下单手数超限");
			}
			
			BigDecimal memberFee = stake.getMemberFee();
			BigDecimal tradeFee = stake.getTradeFee();
			Integer price = stake.getPrice();
			
			BigDecimal realMemberFee = memberFee.multiply(new BigDecimal(orderNum));
			BigDecimal realTradeFee = tradeFee.multiply(new BigDecimal(orderNum));
			BigDecimal realTotalFee = realMemberFee.add(realTradeFee);
			if (realTotalFee.compareTo(preOrderDto.getTotalFee()) != 0){
				throw new ServiceException("手续费计算异常");
			}
			BigDecimal realTotalPrice = new BigDecimal(price).multiply(new BigDecimal(orderNum));
			if (realTotalPrice.compareTo(preOrderDto.getStakeAmount()) != 0){
				throw new ServiceException("下注金额计算异常");
			}
			
			BigDecimal realTotalAmount = realTotalFee.add(realTotalPrice);
			if (realTotalAmount.compareTo(preOrderDto.getTotalAmount()) != 0){
				throw new ServiceException("总费用计算异常");
			}
			
			BigDecimal scale = null;
			if (StakeOrderConst.STAKE_TYPE_WIN.equals(preOrderDto.getStakeType())){
				scale = schedule.getOddsWin();
			} else if (StakeOrderConst.STAKE_TYPE_DRAW.equals(preOrderDto.getStakeType())){
				scale = schedule.getOddsDraw();
			} else if (StakeOrderConst.STAKE_TYPE_LOSE.equals(preOrderDto.getStakeType())){
				scale = schedule.getOddsLose();
			} else {
				throw new ServiceException("下注方式不存在");
			}
			if (scale.compareTo(preOrderDto.getOddsScale()) != 0){
				throw new ServiceException("赔率不 一致");
			}
			
			// 分钱
			BigDecimal preAmount = realTotalPrice.multiply(scale);
			accountTransferBiz.customerStake(customerAccount, memberAccount, realMemberFee, realTradeFee, realTotalPrice,realTotalAmount, preAmount);
			
			// 下注订单
			Broker broker = brokerBiz.getBrokerById(customer.getBrokerId());
			StakeOrder stakeOrder = new StakeOrder();
			if (null != broker){
				stakeOrder.setBrokerId(broker.getBrokerId());
				stakeOrder.setBrokerCode(broker.getBrokerCode());
			} else {
				stakeOrder.setBrokerId(customer.getBrokerId());
				stakeOrder.setBrokerCode("");
			}
			stakeOrder.setPayType(StakeOrderConst.PAY_TYPE_CASH);
			stakeOrder.setCreateTime(new Date());
			stakeOrder.setCustomerId(customer.getId());
			stakeOrder.setCustomerCode(customer.getCustomerCode());
			stakeOrder.setMemberFee(memberFee);
			stakeOrder.setMemberId(member.getId());
			stakeOrder.setMemberCode(member.getMemberCode());
			stakeOrder.setPreCleanAmount(preAmount);
			stakeOrder.setOddsScale(scale);
			stakeOrder.setOrderNum((short) orderNum);
			stakeOrder.setTradeFee(tradeFee);
			stakeOrder.setOrderStatus(StakeOrderConst.ORDER_STATUS_NORMAL);
			stakeOrder.setScheduleId(preOrderDto.getScheduleId());
			stakeOrder.setStakeAmount(realTotalPrice);
			stakeOrder.setStakeCode(preOrderDto.getStakeCode());
			stakeOrder.setStakePrice(new BigDecimal(stake.getPrice()));
			stakeOrder.setStakeType(preOrderDto.getStakeType());
			stakeOrder.setTotalAmount(realTotalAmount);
			stakeOrder.setIsClean(StakeOrderConst.IS_CLEAN_N);
			
			if (stakeOrderBiz.createStakeOrder(stakeOrder) == 1){
				return true;
			}
			
			return false;
		} catch(Exception e){
			logger.error("客户下注失败,下注信息:{}, 异常信息:{}",JSON.toJSONString(preOrderDto),e.getMessage(),e);
			throw new ServiceException(e.getMessage(), e);
		}
		
	}

	@Override
	@Transactional(rollbackFor={ServiceException.class})
	public boolean stakeCleanBySingle(String cleaner,Integer id) throws ServiceException {
		try {
			stakeOrderClean(cleaner, id);
		} catch (Exception e){
			logger.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
		return true;
	}

	private void stakeOrderClean(String cleaner,Integer id) throws ServiceException, BizException{
		if (id == null){
			throw new ServiceException("订单id不能为空");
		}
		StakeOrder stakeOrder = stakeOrderBiz.getStakeOrderByIdForUpdate(id);
		if (stakeOrder == null){
			throw new ServiceException("订单不存在");
		}
		logger.info("单笔订单结算,订单详情:{}", JSON.toJSONString(stakeOrder));
		if (!StakeOrderConst.ORDER_STATUS_NORMAL.equals(stakeOrder.getOrderStatus())){
			throw new ServiceException("订单状态不符合结算条件");
		}
		
		Schedule schedule = scheduleBiz.getScheduleById(stakeOrder.getScheduleId());
		if (!ScheduleConst.STATUS_RESULT.equals(schedule.getStatus())
				&& !ScheduleConst.STATUS_CELAN.equals(schedule.getStatus())){
			throw new ServiceException("赛程状态不支持结算, 状态:" + schedule.getGameResult());
		}
		if (schedule.getaScore() == null){
			throw new ServiceException("赛程甲方得分不存在");
		}
		if (schedule.getaScore().compareTo(Byte.valueOf("0")) < 0){
			throw new ServiceException("赛程甲方分数不能为负值");
		}
		if (schedule.getbScore() == null){
			throw new ServiceException("赛程乙方得分不存在");
		}
		if (schedule.getbScore().compareTo(Byte.valueOf("0")) < 0){
			throw new ServiceException("赛程乙方分数不能为负值");
		}
		if (schedule.getGameResult() == null){
			throw new ServiceException("赛程结果不能为空");
		}
		if ((schedule.getaScore().compareTo(schedule.getbScore()) > 0 && !StakeOrderConst.STAKE_TYPE_WIN.equals(schedule.getGameResult()))
				|| (schedule.getaScore().compareTo(schedule.getbScore()) == 0 && !StakeOrderConst.STAKE_TYPE_DRAW.equals(schedule.getGameResult()))
				|| (schedule.getaScore().compareTo(schedule.getbScore()) < 0 && !StakeOrderConst.STAKE_TYPE_LOSE.equals(schedule.getGameResult()))){
			throw new ServiceException("赛程结果与比分不符");
		}
		
		String transactionCode = codeFactory.getTranscationCode();
		// 客户赢
		if (schedule.getGameResult().equals(stakeOrder.getStakeType())){
			OrderClean orderClean = new OrderClean();
			orderClean.setId(stakeOrder.getId());
			orderClean.setCleanAmount(stakeOrder.getPreCleanAmount());
			orderClean.setCleaner(cleaner);
			orderClean.setCleanTime(new Date());
			orderClean.setCustomerId(stakeOrder.getCustomerId());
			orderClean.setIsDraw(OrderCleanConst.IS_DRAW_N);
			
			if (orderCleanBiz.createOrderClean(orderClean) != 1){
				throw new ServiceException("订单结算创建失败");
			}
		}
		// 会员赢
		else {
			BigDecimal memberAmount = stakeOrder.getPreCleanAmount();
			
			// 代理商分佣总额
			BigDecimal totalBrokerProfit = new BigDecimal("0");
			if (stakeOrder.getBrokerId() != -1){
				Broker broker = brokerBiz.getBrokerById(stakeOrder.getBrokerId());
				if (broker == null){
					throw new ServiceException("代理商不存在");
				}
				
				Map<Integer, BigDecimal> brokerProfit = brokerBiz.getBrokerPorfit(broker.getBrokerCode(), stakeOrder.getStakeAmount());
				if (!CollectionUtils.isEmpty(brokerProfit)){
					for (Entry<Integer, BigDecimal> entry : brokerProfit.entrySet()){
						
						BrokerAccount brokerAccount = brokerAccountBiz.getBrokerAccountByBrokerId(entry.getKey());
						accountTransferBiz.brokerByOrderClean(transactionCode,brokerAccount,entry.getValue());
						
						totalBrokerProfit = totalBrokerProfit.add(entry.getValue());
					}
					if (totalBrokerProfit.compareTo(BigDecimal.ZERO) > 0){
						accountTransferBiz.brokerByOrderClean(transactionCode, totalBrokerProfit);
					}
				}
			}
			MemberAccount memberAccount = memberAccountBiz.getMemberAccountById(stakeOrder.getMemberId());
			accountTransferBiz.memberByOrderClean(memberAccount, memberAmount.subtract(totalBrokerProfit));
		}
		
		if (stakeOrderBiz.delStakeOrder(stakeOrder.getId()) != 1){
			throw new ServiceException("持仓订单删除失败");
		}
		
		StakeOrderH stakeOrderH = new StakeOrderH();
		BeanUtils.copyProperties(stakeOrder, stakeOrderH);
		stakeOrderH.setOrderStatus(StakeOrderHConst.ORDER_STATUS_FINISH);
		stakeOrderH.setIsClean(StakeOrderHConst.IS_CLEAN_Y);
		stakeOrderH.setCleanTime(new Date());
		if (stakeOrderHBiz.createStakeOrderH(stakeOrderH) != 1){
			throw new ServiceException("生成历史仓单异常");
		}
	}
	
	@Override
	public Integer stakeCleanByList(List<Integer> ids, String cleaner) {
		int returnNum = 0;
		if (CollectionUtils.isEmpty(ids)){
			return 0;
		}
		for (Integer id : ids){
			try {
				stakeCleanBySingle(cleaner, id);
				returnNum++;
			} catch (Exception e){
				logger.error(e.getMessage(), e);
			}
		}
		
		return returnNum;
	}

	@Override
	@Transactional(rollbackFor={ServiceException.class})
	public boolean stakeToCash(Integer cleanId) throws ServiceException {
		try {
			OrderClean orderClean = orderCleanBiz.getOrderCleanByIdForUpdate(cleanId);
			if (!OrderCleanConst.IS_DRAW_N.equals(orderClean.getIsDraw())){
				throw new ServiceException("此兑奖清单已兑换");
			}
			
			CustomerAccount customerAccount = customerAccountBiz.getCustomerAccountByCustomerId(orderClean.getCustomerId());
			accountTransferBiz.customerToCash(customerAccount, orderClean);
			
			orderClean.setDrawTime(new Date());
			orderClean.setIsDraw(OrderCleanConst.IS_DRAW_Y);
			orderCleanBiz.updOrderClean(orderClean);
		} catch (Exception e){
			logger.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
		
		return true;
	}

	@Override
	public List<StakeOrderExDto> listStakeOrderByMap(Map<String, Object> params) {
		List<StakeOrderExDto> stakeOrderExDtoList = new ArrayList<>();
		
		List<StakeOrderEx> stakeOrderExList = stakeOrderBiz.listStakeOrderByMap(params);
		if (!CollectionUtils.isEmpty(stakeOrderExList)){
			for (StakeOrderEx stakeOrderEx : stakeOrderExList){
				StakeOrderExDto stakeOrderExDto = new StakeOrderExDto();
				BeanUtils.copyProperties(stakeOrderEx, stakeOrderExDto);
				
				stakeOrderExDtoList.add(stakeOrderExDto);
			}
		}
		return stakeOrderExDtoList;
	}

	@Override
	public int countStakeOrderByMap(Map<String, Object> params) {
		return stakeOrderBiz.countStakeOrderByMap(params);
	}

	@Override
	public StakeOrderExDto getStakeOrderDtoById(Integer id) {
		StakeOrderExDto stakeOrderExDto = new StakeOrderExDto();
		
		StakeOrder stakeOrder = stakeOrderBiz.getStakeOrderById(id);
		if (null != stakeOrder){
			BeanUtils.copyProperties(stakeOrder, stakeOrderExDto);
		}
		return stakeOrderExDto;
	}

	@Override
	public List<OrderCleanExDto> listOrderCleanByCustomerId(Integer customerId, String isDraw) {
		List<OrderCleanExDto> dtoList = new ArrayList<>();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("customerId", customerId);
		params.put("isDraw", isDraw);
		List<OrderCleanEx> list = orderCleanBiz.listByMap(params);
		if (!CollectionUtils.isEmpty(list)){
			for (OrderClean orderClean : list){
				OrderCleanExDto dto = new OrderCleanExDto();
				BeanUtils.copyProperties(orderClean, dto);
				
				dtoList.add(dto);
			}
		}
		return dtoList;
	}

	@Override
	public String stakeCleanBySchedule(String cleaner,Integer scheduleId) {
		Schedule schedule = scheduleBiz.getScheduleById(scheduleId);
		if (!ScheduleConst.STATUS_RESULT.equals(schedule.getStatus())){
			return "赛程不满足结算条件";
		}
		
		schedule.setStatus(ScheduleConst.STATUS_CELAN);
		scheduleBiz.updSchedule(schedule);
		
		// 开始结算
		int cleanNum = 0;
		String resultMsg = "";
		Map<String, Object> params = new HashMap<>();
		params.put("scheduleId", scheduleId);
		List<StakeOrderEx> stakeOrderExList = stakeOrderBiz.listStakeOrderByMap(params);
		if (!CollectionUtils.isEmpty(stakeOrderExList) && stakeOrderExList.size() > 0){
			for (StakeOrderEx stakeOrderEx : stakeOrderExList){
				try {
					stakeCleanBySingle(cleaner,stakeOrderEx.getId());
					cleanNum ++;
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
			resultMsg = "待结算总数: " + stakeOrderExList.size() + "笔, 结算成功: " + cleanNum + "笔";
		}else {
			resultMsg = "待结算总数: 0";
		}
		
		schedule.setStatus(ScheduleConst.STATUS_FINISH);
		scheduleBiz.updSchedule(schedule);
		
		return resultMsg;
	}

	@Override
	public OrderCleanDto getOrderCleanById(Integer id) {
		OrderCleanDto dto = new OrderCleanDto();
		
		OrderClean orderClean = orderCleanBiz.getOrderCleanById(id);
		if (null != orderClean){
			BeanUtils.copyProperties(orderClean, dto);
		}
		return dto;
	}

	@Override
	public StakeOrderHDto getStakeOrderHDtoById(Integer id) {
		StakeOrderHDto dto = new StakeOrderHDto();
		StakeOrderH stakeOrderH = stakeOrderHBiz.getStakeOrderHById(id);
		if (null != stakeOrderH){
			BeanUtils.copyProperties(stakeOrderH, dto);
		}
		return dto;
	}

	@Override
	public List<StakeOrderHDto> listStakeOrderHDtoByMap(Map<String, Object> params) {
		List<StakeOrderHDto> dtoList = new ArrayList<>();
		
		List<StakeOrderH> list = stakeOrderHBiz.listByMap(params);
		if (!CollectionUtils.isEmpty(list)){
			for (StakeOrderH stakeOrderH : list){
				StakeOrderHDto dto = new StakeOrderHDto();
				BeanUtils.copyProperties(stakeOrderH, dto);
				
				dtoList.add(dto);
			}
		}
		return dtoList;
	}

	@Override
	public Integer countStakeOrderHDtoByMap(Map<String, Object> params) {
		return stakeOrderHBiz.countByMap(params);
	}

}
