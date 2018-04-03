package com.tbug.ball.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.tbug.ball.service.BrokerService;
import com.tbug.ball.service.ServiceException;
import com.tbug.ball.service.Dto.account.BrokerAccountDto;
import com.tbug.ball.service.Dto.account.BrokerAccountExDto;
import com.tbug.ball.service.Dto.flow.BrokerAccountFlowDto;
import com.tbug.ball.service.Dto.order.BrokerProfitDto;
import com.tbug.ball.service.Dto.user.BrokerDto;
import com.tbug.ball.service.biz.account.BrokerAccountBiz;
import com.tbug.ball.service.biz.flow.BrokerAccountFlowBiz;
import com.tbug.ball.service.biz.order.BrokerProfitBiz;
import com.tbug.ball.service.biz.user.BrokerBiz;
import com.tbug.ball.service.biz.user.CustomerBiz;
import com.tbug.ball.service.common.DBContants.BrokerConst;
import com.tbug.ball.service.model.account.BrokerAccount;
import com.tbug.ball.service.model.account.BrokerAccountEx;
import com.tbug.ball.service.model.flow.BrokerAccountFlow;
import com.tbug.ball.service.model.order.BrokerProfit;
import com.tbug.ball.service.model.user.Broker;
import com.tbug.ball.service.model.user.Customer;
import com.tbug.ball.service.utils.BrokerUtil;

@Service
public class BrokerServiceImpl implements BrokerService {
	private static final Logger logger = LoggerFactory.getLogger(BrokerServiceImpl.class);
	
	@Autowired BrokerBiz brokerBiz;
	
	@Autowired BrokerProfitBiz brokerProfitBiz;
	
	@Autowired BrokerAccountBiz brokerAccountBiz;
	
	@Autowired BrokerUtil brokerUtil;
	
	@Autowired CustomerBiz customerBiz;
	
	@Autowired BrokerAccountFlowBiz brokerAccountFlowBiz;
	
	@Override
	@Transactional(rollbackFor={ServiceException.class})
	public boolean createBroker(String creater,String memberCode, String phoneNo, String nickName, String password, Integer parentId) throws ServiceException {
		try {
			// 代理商账户
			Broker broker = brokerBiz.createBroker(creater,memberCode,phoneNo,nickName,password,parentId);
			
			// 资金账户
			BrokerAccount brokerAccount = new BrokerAccount();
			brokerAccount.setBrokerId(broker.getBrokerId());
			brokerAccountBiz.createBrokerAccount(brokerAccount);
			
		} catch (Exception e){
			logger.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
		
		return false;
	}

	@Override
	public BrokerDto getBrokerDtoById(Integer id) {
		BrokerDto brokerDto = null;
		
		Broker broker = brokerBiz.getBrokerById(id);
		if (broker != null){
			brokerDto = new BrokerDto();
			BeanUtils.copyProperties(broker, brokerDto);
		}
		
		return brokerDto;
	}

	@Override
	public List<BrokerDto> listBrokerDtoByMap(Map<String, Object> params) {
		List<BrokerDto> brokerDtoList = new ArrayList<>();
		
		List<Broker> brokerList = brokerBiz.listBrokerByMap(params);
		if (!CollectionUtils.isEmpty(brokerList)){
			
			for (Broker broker : brokerList){
				BrokerDto brokerDto = new BrokerDto();
				BeanUtils.copyProperties(broker, brokerDto);
				
				brokerDtoList.add(brokerDto);
			}
		}
		
		return brokerDtoList;
	}

	@Override
	public Integer countBrokerDtoByMap(Map<String, Object> params) {
		
		return brokerBiz.countBrokerByMap(params);
	}

	@Override
	public boolean updBroker(BrokerDto brokerDto) {
		if (brokerDto == null){
			return false;
		}
		
		Broker broker = new Broker();
		BeanUtils.copyProperties(brokerDto, broker);
		
		if (brokerBiz.updBroker(broker) == 1){
			return true;
		}
		
		return false;
	}

	@Override
	@Transactional(rollbackFor={ServiceException.class})
	public boolean moveBroker(Integer newParentBrokerId, BrokerDto brokerDto) throws ServiceException{
		try {
			if (brokerDto == null){
				throw new ServiceException("待转移代理商不能为空");
			}
			Broker broker = brokerBiz.getBrokerById(brokerDto.getBrokerId());
			if (broker == null){
				throw new ServiceException("待转移代理商不存在");
			}
			if (!BrokerConst.STATUS_NORMAL.equals(broker.getStatus())){
				throw new ServiceException("待转移代理商状态异常");
			}
			
			byte oldLevelNum = broker.getLevelNum();
			String oldLevelCode = broker.getLevelCode();

			// 递归转移
			batchMove(newParentBrokerId, oldLevelNum, oldLevelCode);
		} catch (Exception e){
			logger.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
		
		return true;
	}
	
	
	private void batchMove(Integer newParentBrokerId, byte oldLevelNum, String oldLevelCode) throws ServiceException{
		try {
			List<Broker> brokerList = brokerBiz.ListBrokerByLevelNumAndCode(oldLevelNum, oldLevelCode);
			
			if (!CollectionUtils.isEmpty(brokerList)){
				for (Broker broker : brokerList){
					
					byte oldLevelNumX = broker.getLevelNum();
					String oldLevelCodeX = broker.getLevelCode();
					
					// 设置代理商层级
					brokerUtil.setBrokerLevel(newParentBrokerId, broker);
					if (brokerBiz.updBroker(broker) != 1){
						throw new ServiceException("代理商更新失败");
					}
					
					// 转移客户
					List<Customer> customerList = customerBiz.getCustomerByLevelCode(oldLevelCodeX);
					if (!CollectionUtils.isEmpty(customerList)){
						for (Customer customer : customerList){
							customer.setLevelCode(broker.getLevelCode());
							customer.setLevelNum(broker.getLevelNum());
							
							if (customerBiz.updCustomer(customer) != 1){
								throw new ServiceException("代理商转移用户更新失败");
							}
						}
					}
					
					// 递归
					batchMove(broker.getBrokerId(), (byte) (oldLevelNumX + 1), oldLevelCodeX);
				}
			} else {
				return;
			}
		} catch (Exception e){
			logger.error(e.getMessage(), e);
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public BrokerDto getBrokerDtoByCode(String brokerCode) {
		BrokerDto brokerDto = null;
		
		Broker broker = brokerBiz.getBrokerByCode(brokerCode);
		if (broker != null){
			brokerDto = new BrokerDto();
			BeanUtils.copyProperties(broker, brokerDto);
		}
		
		return brokerDto;
	}

	@Override
	public BrokerDto getBrokerDtoBySignCode(String signCode) {
		BrokerDto brokerDto = null;
		
		Broker broker = brokerBiz.getBrokerBySignCode(signCode);
		if (broker != null){
			brokerDto = new BrokerDto();
			BeanUtils.copyProperties(broker, brokerDto);
		}
		
		return brokerDto;
	}

	@Override
	public List<BrokerProfitDto> listProfitAll() {
		List<BrokerProfitDto> brokerProfitDtoList = new ArrayList<>();
		
		List<BrokerProfit> brokerProfitList = brokerProfitBiz.listAll();
		if (!CollectionUtils.isEmpty(brokerProfitList)){
			for (BrokerProfit brokerProfit : brokerProfitList){
				BrokerProfitDto brokerProfitDto = new BrokerProfitDto();
				BeanUtils.copyProperties(brokerProfit, brokerProfitDto);
				
				brokerProfitDtoList.add(brokerProfitDto);
			}
		}
		return brokerProfitDtoList;
	}

	@Override
	public boolean updProfit(BrokerProfitDto brokerProfitDto) {
		
		BrokerProfit brokerProfit = new BrokerProfit();
		BeanUtils.copyProperties(brokerProfitDto, brokerProfit);
		
		if (brokerProfitBiz.update(brokerProfit) != 1){
			return false;
		}
		return true;
	}

	@Override
	public BrokerProfitDto getBrokerProfitDtoById(Integer id) {
		
		BrokerProfitDto brokerProfitDto = new BrokerProfitDto();
		BrokerProfit brokerProfit = brokerProfitBiz.getById(id);
		if (null != brokerProfit){
			BeanUtils.copyProperties(brokerProfit, brokerProfitDto);
		}
		
		return brokerProfitDto;
	}

	@Override
	public BrokerAccountDto getBrokerAccountById(Integer id) {
		BrokerAccountDto dto = new BrokerAccountDto();
		BrokerAccount brokerAccount = brokerAccountBiz.getBrokerAccountById(id);
		if (null != brokerAccount){
			BeanUtils.copyProperties(brokerAccount, dto);
		}
		return dto;
	}

	@Override
	public List<BrokerAccountExDto> listBrokerAccount(Map<String, Object> params) {
		List<BrokerAccountExDto> dtoList = new ArrayList<>();
		
		List<BrokerAccountEx> list = brokerAccountBiz.listBrokerAccountByMap(params);
		if (!CollectionUtils.isEmpty(list)){
			for (BrokerAccount brokerAccount : list){
				BrokerAccountExDto dto = new BrokerAccountExDto();
				BeanUtils.copyProperties(brokerAccount, dto);
				
				dtoList.add(dto);
			}
		}
		return dtoList;
	}

	@Override
	public int countBrokerAccount(Map<String, Object> params) {
		return brokerAccountBiz.countBrokerAccountByMap(params);
	}

	@Override
	public boolean updBrokerAccount(BrokerAccountDto brokerAccountDto) {
		
		BrokerAccount brokerAccount = new BrokerAccount();
		BeanUtils.copyProperties(brokerAccountDto, brokerAccount);
		
		if (brokerAccountBiz.updBrokerAccount(brokerAccount) != 1){
			return false;
		}
		return true;
	}

	@Override
	public List<BrokerAccountFlowDto> listBrokerAccountFlowByMap(Map<String, Object> params) {
		List<BrokerAccountFlowDto> dtoList = new ArrayList<>();
		
		List<BrokerAccountFlow> list = brokerAccountFlowBiz.listBrokerAccountFlowByMap(params);
		if (!CollectionUtils.isEmpty(list)){
			for (BrokerAccountFlow brokerAccountFlow : list){
				BrokerAccountFlowDto dto = new BrokerAccountFlowDto();
				BeanUtils.copyProperties(brokerAccountFlow, dto);
				
				dtoList.add(dto);
			}
		}
		return dtoList;
	}

	@Override
	public Integer countBrokerAccountFlowByMap(Map<String, Object> params) {
	
		return brokerAccountFlowBiz.countBrokerAccountFlowByMap(params);
	}

}
