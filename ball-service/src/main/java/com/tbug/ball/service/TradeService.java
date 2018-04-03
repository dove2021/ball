package com.tbug.ball.service;

import java.util.List;
import java.util.Map;

import com.tbug.ball.service.Dto.account.TradeAccountDto;
import com.tbug.ball.service.Dto.flow.TradeAccountFlowDto;
import com.tbug.ball.service.Dto.user.TradeUserDto;

public interface TradeService {

	TradeUserDto login(String userName, String password) throws ServiceException;
	
	List<TradeUserDto> listTradeUserDto(Map<String, Object> params);
	
	Integer countTradeUserDto(Map<String, Object> params);
	
	TradeUserDto getTradeUserDtoById(Integer id);
	
	TradeUserDto getTradeUserDtoByCode(String code);
	
	TradeUserDto getTradeUserDtoByLoginName(String loginName) throws ServiceException;
	
	boolean saveTradeUserDto(String creater, String loginName, String passowrd, String[] roles) throws ServiceException;
	
	boolean updTradeUserDto(TradeUserDto tradeUserDto) throws ServiceException;
	
	boolean updTradeUserPwd(Integer id, String password, String newPassword) throws ServiceException;
	
	TradeAccountDto getTradeAccountById(Integer id);
	
	List<TradeAccountDto> listTradeAccountByMap();
	
	boolean updTradeAccount(TradeAccountDto tradeAccountDto);
	
	List<TradeAccountFlowDto> listTradeAccountFlowByMap(Map<String, Object> params);
	
	Integer countTradeAccountFlowByMap(Map<String, Object> params);
}
