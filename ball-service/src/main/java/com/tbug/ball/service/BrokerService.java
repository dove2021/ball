package com.tbug.ball.service;

import java.util.List;
import java.util.Map;

import com.tbug.ball.service.Dto.account.BrokerAccountDto;
import com.tbug.ball.service.Dto.account.BrokerAccountExDto;
import com.tbug.ball.service.Dto.flow.BrokerAccountFlowDto;
import com.tbug.ball.service.Dto.order.BrokerProfitDto;
import com.tbug.ball.service.Dto.user.BrokerDto;

public interface BrokerService {

	boolean createBroker(String creater, String memberCode, String phoneNo, String nickName, String password,Integer parentId) throws ServiceException;
	
	BrokerDto getBrokerDtoById(Integer id);
	
	BrokerDto getBrokerDtoByCode(String brokerCode);
	
	BrokerDto getBrokerDtoBySignCode(String signCode);
	
	List<BrokerDto> listBrokerDtoByMap(Map<String, Object> params);
	
	Integer countBrokerDtoByMap(Map<String, Object> params);
	
	boolean updBroker(BrokerDto brokerDto);
	
	boolean moveBroker(Integer newParentBrokerId, BrokerDto brokerDto) throws ServiceException;
	
	BrokerProfitDto getBrokerProfitDtoById(Integer id);
	
	List<BrokerProfitDto> listProfitAll();
	
	boolean updProfit(BrokerProfitDto brokerProfitDto);
	
	BrokerAccountDto getBrokerAccountById(Integer id);
	
	List<BrokerAccountExDto> listBrokerAccount(Map<String, Object> params);
	
	int countBrokerAccount(Map<String, Object> params);
	
	boolean updBrokerAccount(BrokerAccountDto brokerAccountDto);
	
	List<BrokerAccountFlowDto> listBrokerAccountFlowByMap(Map<String, Object> params);
	
	Integer countBrokerAccountFlowByMap(Map<String, Object> params);
	
}
