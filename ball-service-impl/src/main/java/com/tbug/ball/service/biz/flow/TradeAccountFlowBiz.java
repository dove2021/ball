package com.tbug.ball.service.biz.flow;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbug.ball.service.biz.BizException;
import com.tbug.ball.service.common.factory.CodeFactory;
import com.tbug.ball.service.dao.flow.TradeAccountFlowMapper;
import com.tbug.ball.service.model.flow.TradeAccountFlow;

@Service
public class TradeAccountFlowBiz {

	@Autowired
	TradeAccountFlowMapper tradeAccountFlowMapper;
	@Autowired
	CodeFactory codeFactory;
	
	public Integer createTradeAccountFlow(TradeAccountFlow tradeAccountFlow) throws BizException{
		if (null == tradeAccountFlow){
			throw new BizException("交易所资金流水不能为空");
		}
		
		tradeAccountFlow.setFlowCode(codeFactory.getFlowCode());
		return tradeAccountFlowMapper.insertSelective(tradeAccountFlow);
	}
	
	public TradeAccountFlow getTradeAccountFlowById(Integer id){
		return tradeAccountFlowMapper.selectByPrimaryKey(id);
	}
	
	public List<TradeAccountFlow> listByMap(Map<String, Object> params){
		return tradeAccountFlowMapper.selectByMap(params);
	}
	
	public Integer countByMap(Map<String, Object> params){
		return tradeAccountFlowMapper.countByMap(params);
	}
}
