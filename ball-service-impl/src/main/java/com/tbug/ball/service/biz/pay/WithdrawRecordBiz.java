package com.tbug.ball.service.biz.pay;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbug.ball.service.dao.pay.WithdrawRecordMapper;
import com.tbug.ball.service.model.pay.WithdrawRecord;

@Service
public class WithdrawRecordBiz {

	@Autowired
	WithdrawRecordMapper withdrawRecordMapper;
	
	public int create(WithdrawRecord withdrawRecord){
		return withdrawRecordMapper.insertSelective(withdrawRecord);
	}
	
	public WithdrawRecord getById(Integer id){
		return withdrawRecordMapper.selectByPrimaryKey(id);
	}
	
	public List<WithdrawRecord> listByMap(Map<String, Object> params){
		return withdrawRecordMapper.listByMap(params);
	}
	
	public int countByMap(Map<String, Object> params){
		return withdrawRecordMapper.countByMap(params);
	}
	
	public int updWithdrawRecord(WithdrawRecord withdrawRecord){
		return withdrawRecordMapper.updateByPrimaryKeySelective(withdrawRecord);
	}
	
}
