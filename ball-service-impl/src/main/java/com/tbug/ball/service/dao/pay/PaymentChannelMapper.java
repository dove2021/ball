package com.tbug.ball.service.dao.pay;

import java.util.List;

import com.tbug.ball.service.model.pay.PaymentChannel;

public interface PaymentChannelMapper {
    int deleteByPrimaryKey(Integer channelId);

    int insert(PaymentChannel record);

    int insertSelective(PaymentChannel record);

    PaymentChannel selectByPrimaryKey(Integer channelId);

    int updateByPrimaryKeySelective(PaymentChannel record);

    int updateByPrimaryKey(PaymentChannel record);
    
    List<PaymentChannel> listByALL();
}