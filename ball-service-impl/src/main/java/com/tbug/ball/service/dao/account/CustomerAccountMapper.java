package com.tbug.ball.service.dao.account;

import java.util.List;
import java.util.Map;

import com.tbug.ball.service.model.account.CustomerAccount;
import com.tbug.ball.service.model.account.CustomerAccountEx;

public interface CustomerAccountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CustomerAccount record);

    int insertSelective(CustomerAccount record);

    CustomerAccount selectByPrimaryKey(Integer id);
    
    CustomerAccount selectByPrimaryKeyForUpdate(Integer id);

    int updateByPrimaryKeySelective(CustomerAccount record);

    int updateByPrimaryKey(CustomerAccount record);
    
    CustomerAccount selectByCustomerId(Integer customerId);
    
    List<CustomerAccountEx> selectByMap(Map<String, Object> params);
    
    Integer countByMap(Map<String, Object> params);
}