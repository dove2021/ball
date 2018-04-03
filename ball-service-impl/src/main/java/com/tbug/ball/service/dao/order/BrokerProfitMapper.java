package com.tbug.ball.service.dao.order;

import java.util.List;

import com.tbug.ball.service.model.order.BrokerProfit;

public interface BrokerProfitMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BrokerProfit record);

    int insertSelective(BrokerProfit record);

    BrokerProfit selectByPrimaryKey(Integer id);
    
    BrokerProfit selectByLevelNum(short levelNum);

    int updateByPrimaryKeySelective(BrokerProfit record);

    int updateByPrimaryKey(BrokerProfit record);
    
    List<BrokerProfit> listAll();
}