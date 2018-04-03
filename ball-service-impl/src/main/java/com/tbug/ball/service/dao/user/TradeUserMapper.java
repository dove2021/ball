package com.tbug.ball.service.dao.user;

import java.util.List;
import java.util.Map;

import com.tbug.ball.service.model.user.TradeUser;

public interface TradeUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TradeUser record);

    int insertSelective(TradeUser record);

    TradeUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TradeUser record);

    int updateByPrimaryKey(TradeUser record);
    
    List<TradeUser> selectByLoginName(String loginName);
    
    TradeUser selectByCode(String userCode);
    
    List<TradeUser> listByMap(Map<String, Object> params);
    
    Integer countByMap(Map<String, Object> params);
}