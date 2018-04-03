package com.tbug.ball.service.biz.pay;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbug.ball.service.common.DBContants.CashFileConst;
import com.tbug.ball.service.dao.pay.CashFileMapper;
import com.tbug.ball.service.model.pay.CashFile;

@Service
public class CashFileBiz {

	@Autowired
	CashFileMapper cashFileMapper;
	
	public CashFile getCashFileById(Integer id){
		return cashFileMapper.selectByPrimaryKey(id);
	}
	
	public CashFile getByChanenelAndFileName(Integer channelId, String fileName){
		return cashFileMapper.selectByChannelAndName(channelId, fileName);
	}
	
	public int createCashFile(CashFile cashFile){
		
		cashFile.setCreateDate(new Date());
		cashFile.setStatus(CashFileConst.STATUS_1);
		return cashFileMapper.insert(cashFile);
	}
	
	public Integer delCashFile(Integer id){
		CashFile cashFile = new CashFile();
		cashFile.setId(id);
		cashFile.setStatus(CashFileConst.STATUS_2);
		return cashFileMapper.updateByPrimaryKeySelective(cashFile);
	}
	
	public List<CashFile> listByStatus(Integer channelId,String status){
		return cashFileMapper.listCashFile(channelId, status);
	}
	
}
