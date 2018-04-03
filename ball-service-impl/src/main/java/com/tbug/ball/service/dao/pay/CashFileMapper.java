package com.tbug.ball.service.dao.pay;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tbug.ball.service.model.pay.CashFile;

public interface CashFileMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CashFile record);

    int insertSelective(CashFile record);

    CashFile selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CashFile record);

    int updateByPrimaryKey(CashFile record);
    
    List<CashFile> listCashFile(@Param("channelId")Integer channelId, @Param("status")String status);
    
    CashFile selectByChannelAndName(@Param("channelId") Integer channel, @Param("orgFileName") String fileName);
}