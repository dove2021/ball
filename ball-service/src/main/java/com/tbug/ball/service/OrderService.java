package com.tbug.ball.service;

import java.util.List;
import java.util.Map;

import com.tbug.ball.service.Dto.order.OrderCleanDto;
import com.tbug.ball.service.Dto.order.OrderCleanExDto;
import com.tbug.ball.service.Dto.order.StakeOrderExDto;
import com.tbug.ball.service.Dto.order.StakeOrderHDto;
import com.tbug.ball.service.Dto.schedule.PreOrderDto;

public interface OrderService {

	public boolean stakeOpen(PreOrderDto preOrderDto) throws ServiceException;
	
	public boolean stakeCleanBySingle(String cleaner,Integer id) throws ServiceException;
	
	public Integer stakeCleanByList(List<Integer> ids,String cleaner);
	
	/**
	 * 按赛程结算
	 * @param scheduleId
	 * @return
	 */
	public String stakeCleanBySchedule(String cleaner,Integer scheduleId);
	
	/**
	 * 兑现： 客户
	 * @return
	 */
	public boolean stakeToCash(Integer cleanId) throws ServiceException;
	
	public List<StakeOrderExDto> listStakeOrderByMap(Map<String, Object> params);
	
	public int countStakeOrderByMap(Map<String, Object> params);
	
	public StakeOrderExDto getStakeOrderDtoById(Integer id);
	
	List<OrderCleanExDto> listOrderCleanByCustomerId(Integer customerId, String isDraw);
	
	OrderCleanDto getOrderCleanById(Integer id);
	
	StakeOrderHDto getStakeOrderHDtoById(Integer id);
	
	List<StakeOrderHDto> listStakeOrderHDtoByMap(Map<String, Object> params);
	
	Integer countStakeOrderHDtoByMap(Map<String, Object> params);
}
