package com.tbug.ball.service.dao.pay;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tbug.ball.service.model.pay.WithdrawFile;

public interface WithdrawFileMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WithdrawFile record);

    int insertSelective(WithdrawFile record);

    WithdrawFile selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WithdrawFile record);

    int updateByPrimaryKeyWithBLOBs(WithdrawFile record);

    int updateByPrimaryKey(WithdrawFile record);
    
    List<WithdrawFile> ListByCustomerId(Integer customerId);
    
    WithdrawFile getWithdrawFile(@Param("customerId")Integer customerId, @Param("channelId") Integer channelId);
}