package com.tbug.ball.service.biz.flow;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbug.ball.service.biz.BizException;
import com.tbug.ball.service.common.factory.CodeFactory;
import com.tbug.ball.service.dao.flow.CustomerAccountFlowMapper;
import com.tbug.ball.service.model.flow.CustomerAccountFlow;
import com.tbug.ball.service.model.flow.CustomerAccountFlowSum;

@Service
public class CustomerAccountFlowBiz {

	@Autowired
	CustomerAccountFlowMapper customerAccountFlowMapper;
	@Autowired
	CodeFactory codeFactory;
	
	public Integer createCustomerAccountFlow(CustomerAccountFlow customerAccountFlow) throws BizException{
		
		if (null == customerAccountFlow){
			throw new BizException("客户资金流水不能为空");
		}
		
		customerAccountFlow.setFlowNo(codeFactory.getFlowNo());
		
		return customerAccountFlowMapper.insert(customerAccountFlow);
	}
	
	public CustomerAccountFlow getCustomerAccountFlowById(Integer id){
		return customerAccountFlowMapper.selectByPrimaryKey(id);
	}
	
	public Integer updCustomerAccountFlow(CustomerAccountFlow customerAccountFlow){
		return customerAccountFlowMapper.updateByPrimaryKeySelective(customerAccountFlow);
	}
	
	public List<CustomerAccountFlow> listCustomerAccountFlowByMap(Map<String, Object> params){
		return customerAccountFlowMapper.selectByMap(params);
	}
	
	public Integer countCustomerAccountFlowByMap(Map<String, Object> params){
		return customerAccountFlowMapper.countByMap(params);
	}
	
	public CustomerAccountFlowSum sumFlow(Map<String, Object> params){
		return customerAccountFlowMapper.totalByMap(params);
	}
}
