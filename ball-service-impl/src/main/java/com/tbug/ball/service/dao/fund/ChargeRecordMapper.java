package com.tbug.ball.service.dao.fund;

import com.tbug.ball.service.model.fund.ChargeRecord;

public interface ChargeRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChargeRecord record);

    int insertSelective(ChargeRecord record);

    ChargeRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChargeRecord record);

    int updateByPrimaryKey(ChargeRecord record);
}