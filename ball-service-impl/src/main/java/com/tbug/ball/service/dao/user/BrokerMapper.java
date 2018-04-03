package com.tbug.ball.service.dao.user;

import java.util.List;
import java.util.Map;

import com.tbug.ball.service.model.user.Broker;

public interface BrokerMapper {
    int deleteByPrimaryKey(Integer brokerId);

    int insert(Broker record);

    int insertSelective(Broker record);

    Broker selectByPrimaryKey(Integer brokerId);

    int updateByPrimaryKeySelective(Broker record);

    int updateByPrimaryKey(Broker record);
    
    Broker selectByBrokerCode(String brokerCode);
    
    Broker selectBySignCode(String signCode);
    
    Broker selectByLoginName(String loginName);
    
    Broker selectByLevelCode(String levelCode);
    
    List<Broker> listByMap(Map<String, Object> params);
    
    int countByMap(Map<String, Object> params);
}