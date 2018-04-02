package com.tbug.ball.service.biz.account;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbug.ball.service.biz.BizException;
import com.tbug.ball.service.common.DBContants.BrokerAccountConst;
import com.tbug.ball.service.common.security.PasswordHelper;
import com.tbug.ball.service.dao.account.BrokerAccountMapper;
import com.tbug.ball.service.model.account.BrokerAccount;
import com.tbug.ball.service.model.account.BrokerAccountEx;

@Service
public class BrokerAccountBiz {

	@Autowired
	private BrokerAccountMapper brokerAccountMapper;
	
	public int createBrokerAccount(BrokerAccount brokerAccount) throws BizException{
		
		if (null == brokerAccount){
			throw new BizException("代理商账户不能为空");
		}
		
		brokerAccount.setBalance(BigDecimal.ZERO);
		brokerAccount.setCreateDate(new Date());
		brokerAccount.setStatus(BrokerAccountConst.STATUS_NORMAL);
		
		String password = brokerAccount.getAccountPassword();
		String salt = PasswordHelper.generateSalt();
		String newPassword = PasswordHelper.encryptPassword(salt, password);
		
		brokerAccount.setAccountPassword(newPassword);
		brokerAccount.setSalt(salt);
		
		return brokerAccountMapper.insert(brokerAccount);
	}
	
	public BrokerAccount getBrokerAccountById(Integer id){
		return brokerAccountMapper.selectByPrimaryKey(id);
	}
	
	public BrokerAccount getBrokerAccountByIdForUpdate(Integer id){
		return brokerAccountMapper.selectByPrimaryKeyForUpdate(id);
	}
	
	public List<BrokerAccountEx> listBrokerAccountByMap(Map<String, Object> params){
		return brokerAccountMapper.selectByMap(params);
	}
	
	public Integer countBrokerAccountByMap(Map<String, Object> params){
		return brokerAccountMapper.countByMap(params);
	}
	
	public Integer updBrokerAccount(BrokerAccount brokerAccount){
		return brokerAccountMapper.updateByPrimaryKeySelective(brokerAccount);
	}
	
	public BrokerAccount getBrokerAccountByBrokerId(Integer brokerId){
		return brokerAccountMapper.selectByBrokerId(brokerId);
	}
}
