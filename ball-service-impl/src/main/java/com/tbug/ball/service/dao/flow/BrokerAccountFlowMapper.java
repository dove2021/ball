package com.tbug.ball.service.dao.flow;

import java.util.List;
import java.util.Map;

import com.tbug.ball.service.model.flow.BrokerAccountFlow;

public interface BrokerAccountFlowMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BrokerAccountFlow record);

    int insertSelective(BrokerAccountFlow record);

    BrokerAccountFlow selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BrokerAccountFlow record);

    int updateByPrimaryKey(BrokerAccountFlow record);
    
    List<BrokerAccountFlow> selectByMap(Map<String, Object> params);
    
    Integer countByMap(Map<String, Object> params);
}