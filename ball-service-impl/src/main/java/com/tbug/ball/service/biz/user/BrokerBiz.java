package com.tbug.ball.service.biz.user;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tbug.ball.service.biz.BizException;
import com.tbug.ball.service.common.DBContants.BrokerConst;
import com.tbug.ball.service.common.factory.CodeFactory;
import com.tbug.ball.service.common.security.PasswordHelper;
import com.tbug.ball.service.dao.order.BrokerProfitMapper;
import com.tbug.ball.service.dao.user.BrokerMapper;
import com.tbug.ball.service.dao.user.MemberMapper;
import com.tbug.ball.service.model.order.BrokerProfit;
import com.tbug.ball.service.model.user.Broker;
import com.tbug.ball.service.model.user.Member;
import com.tbug.ball.service.utils.BrokerUtil;

@Service
public class BrokerBiz {

	@Autowired
	BrokerMapper brokerMapper;
	@Autowired
	CodeFactory codeFactory;
	@Autowired
	MemberMapper memberMapper;
	@Autowired
	BrokerUtil brokerUtil;
	@Autowired
	BrokerProfitMapper brokerProfitMapper;
	
	public Broker createBroker(String creater,String memberCode, String phoneNo, String nickName,String password, Integer parentBrokerId) throws BizException{
		
		if (StringUtils.isEmpty(creater)){
			throw new BizException("创建人不能为空");
		}
		if (StringUtils.isEmpty(phoneNo)){
			throw new BizException("手机号/邀请码不能为空");
		}
		if (StringUtils.isEmpty(nickName)){
			throw new BizException("名称不能为空");
		}
		if (StringUtils.isEmpty(password)){
			throw new BizException("密码不能为空");
		}
		Broker dbBroker = brokerMapper.selectByLoginName(phoneNo);
		if (dbBroker != null){
			throw new BizException("手机号已存在");
		}
		Broker dbBroker1 = brokerMapper.selectBySignCode(phoneNo);
		if (dbBroker1 != null){
			throw new BizException("代理商标识码已存在");
		}
		
		Broker broker = new Broker();
		broker.setLoginName(phoneNo);
		broker.setCreateTime(new Date());
		broker.setCreater(creater);
		broker.setSignCode(phoneNo);
		broker.setNickName(nickName);
		broker.setStatus(BrokerConst.STATUS_NORMAL);
		
		// 代理商层级
		brokerUtil.setBrokerLevel(parentBrokerId, broker);
		
		Member member = null;
		if (parentBrokerId.equals(0)){
			member = memberMapper.selectByCode(memberCode);
		} else {
			member = memberMapper.selectByPrimaryKey(broker.getMemberId());
		}
		if (member == null){
			throw new BizException("会员不能为空");
		}
		broker.setMemberId(member.getId());
		broker.setMemberName(member.getName());
		broker.setBrokerCode(codeFactory.getBrokerCode(member.getMemberCode()));
		
		String salt = PasswordHelper.generateSalt();
		String encryptPassword = PasswordHelper.encryptPassword(salt, password);
		
		broker.setPassword(encryptPassword);
		broker.setSalt(salt);
		
		if (brokerMapper.insertSelective(broker) != 1){
			throw new BizException("创建代理商失败");
		}
		
		return broker;
	}
	
	public Broker getBrokerById(Integer id){
		return brokerMapper.selectByPrimaryKey(id);
	}
	
	public Broker getBrokerByCode(String code){
		return brokerMapper.selectByBrokerCode(code);
	}
	
	public Broker getBrokerBySignCode(String signCode){
		return brokerMapper.selectBySignCode(signCode);
	}
	
	public Broker getBrokerByLoginName(String loginName){
		return brokerMapper.selectByLoginName(loginName);
	}
	
	public Broker getBrokerByLevelCode(String levelCode){
		return brokerMapper.selectByLevelCode(levelCode);
	}
	
	public Integer updBroker(Broker broker){
		return brokerMapper.updateByPrimaryKeySelective(broker);
	}
	
	public List<Broker> listBrokerByMap(Map<String, Object> params){
		return brokerMapper.listByMap(params);
	}
	
	public List<Broker> listBrokerByLevelCode(String levelCode){
		Map<String, Object> params = new HashMap<>();
		params.put("levelCode", levelCode);
		return brokerMapper.listByMap(params);
	}
	
	public Integer countBrokerByMap(Map<String, Object> params){
		return brokerMapper.countByMap(params);
	}
	
	public List<Broker> ListBrokerByLevelNumAndCode(byte levelNum, String levelCode){
		Map<String, Object> params = new HashMap<>();
		params.put("levelNum", levelNum);
		params.put("levelCode", levelCode);
		return brokerMapper.listByMap(params);
	}
	
	public Map<Integer, BigDecimal> getBrokerPorfit(String brokerCode, BigDecimal totalPrice) throws BizException{
		Map<Integer, BigDecimal> resultMap = new HashMap<>();
		
		Broker broker = brokerMapper.selectByBrokerCode(brokerCode);
		BrokerProfit brokerProfit = brokerProfitMapper.selectByLevelNum(broker.getLevelNum());
		
		if (broker.getLevelNum().intValue() == 1){
			BigDecimal brokerOneAmount = totalPrice.multiply(brokerProfit.getAmountScale()).setScale(2, BigDecimal.ROUND_DOWN);
			resultMap.put(broker.getBrokerId(), brokerOneAmount);
		}
		else if (broker.getLevelNum().intValue() == 2){
			Broker brokerOne = brokerMapper.selectByLevelCode(broker.getLevelCode().split("-")[0]);
			BigDecimal profit = totalPrice.multiply(brokerProfit.getAmountScale());
			
			BigDecimal brokerOneAmount = profit.multiply(brokerProfit.getScaleOne()).setScale(2, BigDecimal.ROUND_DOWN);
			BigDecimal brokerTwoAmount = profit.subtract(brokerOneAmount);
			
			resultMap.put(broker.getBrokerId(), brokerOneAmount);
			resultMap.put(brokerOne.getBrokerId(), brokerTwoAmount);
		}
		else if (broker.getLevelNum().intValue() == 3){
			String[] levelCodeArr = broker.getLevelCode().split("-");
			Broker brokerOne = brokerMapper.selectByLevelCode(levelCodeArr[0]);
			Broker brokerTwo = brokerMapper.selectByLevelCode(levelCodeArr[0] + "-" + levelCodeArr[1]);
			
			BigDecimal profit = totalPrice.multiply(brokerProfit.getAmountScale());
			
			BigDecimal brokerOneAmount = profit.multiply(brokerProfit.getScaleOne()).setScale(2, BigDecimal.ROUND_DOWN);
			BigDecimal brokerTwoAmount = profit.multiply(brokerProfit.getScaleTwo()).setScale(2, BigDecimal.ROUND_DOWN);
			BigDecimal brokerThreeAmount = profit.subtract(brokerOneAmount).subtract(brokerTwoAmount);
			
			resultMap.put(brokerOne.getBrokerId(), brokerOneAmount);
			resultMap.put(brokerTwo.getBrokerId(), brokerTwoAmount);
			resultMap.put(broker.getBrokerId(), brokerThreeAmount);
		}
		else {
			throw new BizException("不存在的层级");
		}
		
		return resultMap;
	}
	
}
