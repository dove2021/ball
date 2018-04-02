package com.tbug.ball.service.biz.flow;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbug.ball.service.biz.BizException;
import com.tbug.ball.service.common.factory.CodeFactory;
import com.tbug.ball.service.dao.flow.MemberAccountFlowMapper;
import com.tbug.ball.service.model.flow.MemberAccountFlow;

@Service
public class MemberAccountFlowBiz {

	@Autowired
	MemberAccountFlowMapper memberAccountFlowMapper;
	@Autowired
	CodeFactory codeFactory;
	
	public Integer createMemberAccountFlow(MemberAccountFlow memberAccountFlow) throws BizException{
		if (null == memberAccountFlow){
			throw new BizException("会员交易流水不能为空");
		}
		
		memberAccountFlow.setFlowNo(codeFactory.getFlowNo());
		
		return memberAccountFlowMapper.insert(memberAccountFlow);
	}
	
	public MemberAccountFlow getMemberAccountFlowById(Integer id){
		return memberAccountFlowMapper.selectByPrimaryKey(id);
	}
	
	public List<MemberAccountFlow> listByMap(Map<String, Object> params){
		return memberAccountFlowMapper.selectByMap(params);
	}
	
	public Integer countByMap(Map<String, Object> params){
		return memberAccountFlowMapper.countByMap(params);
	}
}
