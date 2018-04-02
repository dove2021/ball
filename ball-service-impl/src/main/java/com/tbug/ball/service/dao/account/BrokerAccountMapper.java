package com.tbug.ball.service.dao.account;

import java.util.List;
import java.util.Map;

import com.tbug.ball.service.model.account.BrokerAccount;
import com.tbug.ball.service.model.account.BrokerAccountEx;

public interface BrokerAccountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BrokerAccount record);

    int insertSelective(BrokerAccount record);

    BrokerAccount selectByPrimaryKey(Integer id);
    
    BrokerAccount selectByPrimaryKeyForUpdate(Integer id);

    int updateByPrimaryKeySelective(BrokerAccount record);

    int updateByPrimaryKey(BrokerAccount record);
    
    List<BrokerAccountEx> selectByMap(Map<String, Object> params);
    
    Integer countByMap(Map<String, Object> params);
    
    BrokerAccount selectByBrokerId(Integer brokerId);
}