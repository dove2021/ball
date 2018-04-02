package com.tbug.ball.service.dao.pay;

import com.tbug.ball.service.model.pay.PaymentChannelBank;

public interface PaymentChannelBankMapper {
    int deleteByPrimaryKey(Integer channelBankId);

    int insert(PaymentChannelBank record);

    int insertSelective(PaymentChannelBank record);

    PaymentChannelBank selectByPrimaryKey(Integer channelBankId);

    int updateByPrimaryKeySelective(PaymentChannelBank record);

    int updateByPrimaryKey(PaymentChannelBank record);
}