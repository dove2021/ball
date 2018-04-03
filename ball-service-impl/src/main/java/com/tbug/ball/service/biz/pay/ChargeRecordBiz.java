package com.tbug.ball.service.biz.pay;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbug.ball.service.dao.pay.ChargeRecordMapper;
import com.tbug.ball.service.model.pay.ChargeRecord;

@Service
public class ChargeRecordBiz {

	@Autowired
	ChargeRecordMapper chargeRecordMapper;
	
	public int create(ChargeRecord chargeRecord){
		return chargeRecordMapper.insertSelective(chargeRecord);
	}
	
	public ChargeRecord getById(Integer id){
		return chargeRecordMapper.selectByPrimaryKey(id);
	}
	
	public int updChargeRecord(ChargeRecord chargeRecord){
		return chargeRecordMapper.updateByPrimaryKeySelective(chargeRecord);
	}
	
	public List<ChargeRecord> listByMap(Map<String, Object> params){
		return chargeRecordMapper.listByMap(params);
	}
	
	public int countByMap(Map<String, Object> params){
		return chargeRecordMapper.countByMap(params);
	}
	
}
