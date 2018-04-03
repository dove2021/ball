package com.tbug.ball.service.dao.flow;

import java.util.List;
import java.util.Map;

import com.tbug.ball.service.model.flow.TradeAccountFlow;

public interface TradeAccountFlowMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TradeAccountFlow record);

    int insertSelective(TradeAccountFlow record);

    TradeAccountFlow selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TradeAccountFlow record);

    int updateByPrimaryKey(TradeAccountFlow record);
    
    List<TradeAccountFlow> selectByMap(Map<String, Object> params);
    
    Integer countByMap(Map<String, Object> params);
}