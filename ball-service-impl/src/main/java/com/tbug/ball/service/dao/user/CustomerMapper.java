package com.tbug.ball.service.dao.user;

import java.util.List;
import java.util.Map;

import com.tbug.ball.service.model.user.Customer;

public interface CustomerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Customer record);

    int insertSelective(Customer record);

    Customer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Customer record);

    int updateByPrimaryKey(Customer record);
    
    Customer selectByCode(String code);
    
    Customer selectByLoginName(String loginName);
    
    Customer selectByPhoneNo(String phoneNo);
    
    List<Customer> listCustomerByMap(Map<String, Object> params);
    
    Integer countCustomerByMap(Map<String, Object> params);
    
    List<Customer> selectByLevelCode(String levelCode);
    
}