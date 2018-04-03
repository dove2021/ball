package com.tbug.ball.service.biz.account;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbug.ball.service.dao.account.TradeAccountMapper;
import com.tbug.ball.service.model.account.TradeAccount;

@Service
public class TradeAccountBiz {

	@Autowired
	TradeAccountMapper tradeAccountMapper;
	
	public TradeAccount getTradeAccountById(Integer id){
		return tradeAccountMapper.selectByPrimaryKey(id);
	}
	
	public TradeAccount getTradeAccountByIdForUpdate(Integer id){
		return tradeAccountMapper.selectByPrimaryKeyForUpdate(id);
	}

	public List<TradeAccount> AllTradeAccount(){
		return tradeAccountMapper.selectByAll();
	}
	
	public Integer updTradeAccount(TradeAccount tradeAccount){
		return tradeAccountMapper.updateByPrimaryKeySelective(tradeAccount);
	}
}
