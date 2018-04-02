package com.tbug.ball.service.dao.flow;

import java.util.List;
import java.util.Map;

import com.tbug.ball.service.model.flow.CustomerAccountFlow;
import com.tbug.ball.service.model.flow.CustomerAccountFlowSum;

public interface CustomerAccountFlowMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CustomerAccountFlow record);

    int insertSelective(CustomerAccountFlow record);

    CustomerAccountFlow selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CustomerAccountFlow record);

    int updateByPrimaryKey(CustomerAccountFlow record);
    
    List<CustomerAccountFlow> selectByMap(Map<String, Object> params);
    
    Integer countByMap(Map<String, Object> params);
    
    CustomerAccountFlowSum totalByMap(Map<String, Object> params);
}