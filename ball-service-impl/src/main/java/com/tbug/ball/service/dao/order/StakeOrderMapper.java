package com.tbug.ball.service.dao.order;

import java.util.List;
import java.util.Map;

import com.tbug.ball.service.model.order.StakeOrder;
import com.tbug.ball.service.model.order.StakeOrderEx;

public interface StakeOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StakeOrder record);

    int insertSelective(StakeOrder record);

    StakeOrder selectByPrimaryKey(Integer id);
    
    StakeOrder selectByPrimaryKeyForUpdate(Integer id);

    int updateByPrimaryKeySelective(StakeOrder record);

    int updateByPrimaryKey(StakeOrder record);
    
    List<StakeOrderEx> listByMap(Map<String, Object> params);
    
    Integer countByMap(Map<String, Object> params);
}