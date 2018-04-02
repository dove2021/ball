package com.tbug.ball.service.dao.order;

import java.util.List;
import java.util.Map;

import com.tbug.ball.service.model.order.StakeOrderH;

public interface StakeOrderHMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StakeOrderH record);

    int insertSelective(StakeOrderH record);

    StakeOrderH selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StakeOrderH record);

    int updateByPrimaryKey(StakeOrderH record);
    
    List<StakeOrderH> listByMap(Map<String, Object> params);
    
    Integer countByMap(Map<String, Object> params);
    
}