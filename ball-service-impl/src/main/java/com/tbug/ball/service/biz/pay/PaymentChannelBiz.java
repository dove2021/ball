package com.tbug.ball.service.biz.pay;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbug.ball.service.dao.pay.PaymentChannelMapper;
import com.tbug.ball.service.model.pay.PaymentChannel;

@Service
public class PaymentChannelBiz {

	@Autowired
	PaymentChannelMapper paymentChannelMapper;
	
	public PaymentChannel getById(Integer id){
		return paymentChannelMapper.selectByPrimaryKey(id);
	}
	
	public List<PaymentChannel> listByAll(){
		return paymentChannelMapper.listByALL();
	}
	
	public Integer updPaymentChannel(PaymentChannel record){
		return paymentChannelMapper.updateByPrimaryKeySelective(record);
	}
}
