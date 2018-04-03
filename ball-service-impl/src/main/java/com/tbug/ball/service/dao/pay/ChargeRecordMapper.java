package com.tbug.ball.service.dao.pay;

import java.util.List;
import java.util.Map;

import com.tbug.ball.service.model.pay.ChargeRecord;

public interface ChargeRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChargeRecord record);

    int insertSelective(ChargeRecord record);

    ChargeRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChargeRecord record);

    int updateByPrimaryKey(ChargeRecord record);
    
    List<ChargeRecord> listByMap(Map<String, Object> params);
    
    int countByMap(Map<String, Object> params);
}