package com.tbug.ball.service.dao.pay;

import com.tbug.ball.service.model.pay.Bank;

public interface BankMapper {
    int deleteByPrimaryKey(Integer bankId);

    int insert(Bank record);

    int insertSelective(Bank record);

    Bank selectByPrimaryKey(Integer bankId);

    int updateByPrimaryKeySelective(Bank record);

    int updateByPrimaryKey(Bank record);
}