package com.tbug.ball.service.biz.flow;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbug.ball.service.biz.BizException;
import com.tbug.ball.service.common.factory.CodeFactory;
import com.tbug.ball.service.dao.flow.BrokerAccountFlowMapper;
import com.tbug.ball.service.model.flow.BrokerAccountFlow;

@Service
public class BrokerAccountFlowBiz {

	@Autowired
	BrokerAccountFlowMapper brokerAccountFlowMapper;
	@Autowired
	CodeFactory codeFactory;
	
	public Integer createBrokerAccountFlow(BrokerAccountFlow brokerAccountFlow) throws BizException{
		
		if (null == brokerAccountFlow){
			throw new BizException("代理商账号为空");
		}
		
		brokerAccountFlow.setFlowNo(codeFactory.getFlowNo());
		return brokerAccountFlowMapper.insert(brokerAccountFlow);
	}
	
	public BrokerAccountFlow getBrokerAccountFlowById(Integer id){
		return brokerAccountFlowMapper.selectByPrimaryKey(id);
	}
	
	public List<BrokerAccountFlow> listBrokerAccountFlowByMap(Map<String, Object> params){
		return brokerAccountFlowMapper.selectByMap(params);
	}
	
	public Integer countBrokerAccountFlowByMap(Map<String, Object> params){
		return brokerAccountFlowMapper.countByMap(params);
	}
}
