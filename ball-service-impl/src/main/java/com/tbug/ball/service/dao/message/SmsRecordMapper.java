package com.tbug.ball.service.dao.message;

import com.tbug.ball.service.model.message.SmsRecord;

public interface SmsRecordMapper {
    int deleteByPrimaryKey(Integer recordId);

    int insert(SmsRecord record);

    int insertSelective(SmsRecord record);

    SmsRecord selectByPrimaryKey(Integer recordId);

    int updateByPrimaryKeySelective(SmsRecord record);

    int updateByPrimaryKey(SmsRecord record);
}