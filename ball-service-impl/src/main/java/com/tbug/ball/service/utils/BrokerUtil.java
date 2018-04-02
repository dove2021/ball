package com.tbug.ball.service.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tbug.ball.service.biz.BizException;
import com.tbug.ball.service.biz.system.SequenceBrokerLevelBiz;
import com.tbug.ball.service.common.DBContants.DB;
import com.tbug.ball.service.dao.user.BrokerMapper;
import com.tbug.ball.service.model.user.Broker;

@Component
public class BrokerUtil {

	public static final byte LEVEL_NUM_MAX = 3;
	
	@Autowired
	BrokerMapper brokerMapper;
	@Autowired 
	SequenceBrokerLevelBiz sequenceBrokerLevelBiz;
	
	public void setBrokerLevel(Integer parentBrokerId, Broker broker) throws BizException{
		
		// 一级代理商
		if (parentBrokerId == DB.NULL_INT){
			broker.setParentId(DB.NULL_INT);
			broker.setLevelNum((byte) 1);
			broker.setLevelCode(sequenceBrokerLevelBiz.getLevelCode(""));
		}
		// 非一级代理
		else {
			Broker parentBroker = brokerMapper.selectByPrimaryKey(parentBrokerId);
			if (null == parentBroker){
				throw new BizException("上级代理商不存在");
			}
			if (parentBroker.getLevelNum().compareTo(LEVEL_NUM_MAX) >= 0){
				throw new BizException("代理商最大支持" + LEVEL_NUM_MAX + "层");
			}
			
			broker.setParentId(parentBroker.getBrokerId());
			broker.setMemberId(parentBroker.getMemberId());
			broker.setMemberName(parentBroker.getMemberName());
			broker.setLevelNum((byte) (parentBroker.getLevelNum() + 1));
			broker.setLevelCode(sequenceBrokerLevelBiz.getLevelCode(parentBroker.getLevelCode()));
		}
	}
}
