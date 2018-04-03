package com.tbug.ball.service.dao.pay;

import java.util.List;
import java.util.Map;

import com.tbug.ball.service.model.pay.WithdrawRecord;

public interface WithdrawRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WithdrawRecord record);

    int insertSelective(WithdrawRecord record);

    WithdrawRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WithdrawRecord record);

    int updateByPrimaryKey(WithdrawRecord record);
    
    List<WithdrawRecord> listByMap(Map<String, Object> params);
    
    int countByMap(Map<String, Object> params);
}