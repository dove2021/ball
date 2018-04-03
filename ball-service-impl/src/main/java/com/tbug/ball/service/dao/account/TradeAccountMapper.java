package com.tbug.ball.service.dao.account;

import java.util.List;

import com.tbug.ball.service.model.account.TradeAccount;

public interface TradeAccountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TradeAccount record);

    int insertSelective(TradeAccount record);

    TradeAccount selectByPrimaryKey(Integer id);
    
    TradeAccount selectByPrimaryKeyForUpdate(Integer id);

    int updateByPrimaryKeySelective(TradeAccount record);

    int updateByPrimaryKey(TradeAccount record);
    
    List<TradeAccount> selectByAll();
    
}