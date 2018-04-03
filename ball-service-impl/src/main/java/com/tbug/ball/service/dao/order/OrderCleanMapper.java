package com.tbug.ball.service.dao.order;

import java.util.List;
import java.util.Map;

import com.tbug.ball.service.model.order.OrderClean;
import com.tbug.ball.service.model.order.OrderCleanEx;

public interface OrderCleanMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderClean record);

    int insertSelective(OrderClean record);

    OrderClean selectByPrimaryKey(Integer id);
    
    OrderClean selectByPrimaryKeyForUpdate(Integer id);

    int updateByPrimaryKeySelective(OrderClean record);

    int updateByPrimaryKey(OrderClean record);
    
    List<OrderCleanEx> listByMap(Map<String, Object> params);
}