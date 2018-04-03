package com.tbug.ball.service.dao.pay;

import com.tbug.ball.service.model.pay.CustomerWithdraw;

public interface CustomerWithdrawMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CustomerWithdraw record);

    int insertSelective(CustomerWithdraw record);

    CustomerWithdraw selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CustomerWithdraw record);

    int updateByPrimaryKey(CustomerWithdraw record);
}