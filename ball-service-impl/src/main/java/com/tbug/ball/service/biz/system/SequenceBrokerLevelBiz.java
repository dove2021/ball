package com.tbug.ball.service.biz.system;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.tbug.ball.service.biz.BizException;
import com.tbug.ball.service.dao.system.SequenceBrokerLevelMapper;
import com.tbug.ball.service.model.system.SequenceBrokerLevel;

@Component
public class SequenceBrokerLevelBiz {
	private static final Logger logger = LoggerFactory.getLogger(SequenceBrokerLevelBiz.class);
	
	@Resource
	SequenceBrokerLevelMapper sequeceBrokerLevelMapper;
	
	/** 一级代理商层级编号 */
	public static final String BROKER_LEVEL_ONE = "BROKER_LEVEL_ONE";
	
	/** 是否循环：否 */
	private static final String IS_CYCLE_NO = "0";
	/** 是否循环：是 */
	private static final String IS_CYCLE_YES = "1";
	
	
	/**
	 * 代理商层级编号
	 * 规则 例：  一层 0001
	 * 		    二层 0001-0001
	 * 		    三层 0001-0001-0001
	 * @param parentBrokerCode
	 * @return
	 * @throws BizException 
	 */
	public String getLevelCode(String parentBrokerCode) throws BizException{
		
		String code = BROKER_LEVEL_ONE;
		if (!StringUtils.isEmpty(parentBrokerCode)){
			code = parentBrokerCode;
		}
		
		String value = "" + getNextVal(code);
		value = formatSequence(value, 4);
		
		if (Integer.valueOf(value).compareTo(9999) >= 0){
			throw new BizException("下属代理商已满,无法继续创建");
		}
		
		if (!StringUtils.isEmpty(parentBrokerCode)){
			return parentBrokerCode + "-" + value;
		}
		
		return value;
	}
	
	/**
	 * 下一序列号
	 * @param name 序列名
	 * @return
	 * @throws BizException
	 */
	private synchronized int getNextVal(String name) throws BizException{
		
		SequenceBrokerLevel sequence = sequeceBrokerLevelMapper.selectByPrimaryKey(name);
		if(null == sequence){
			
			SequenceBrokerLevel newSequence = new SequenceBrokerLevel();
			newSequence.setName(name);
			newSequence.setCurrentValue(0);
			newSequence.setIncrement(1);
			newSequence.setIsCycle(IS_CYCLE_YES);
			newSequence.setMaxValue(99999999);
			newSequence.setVersion(0);
			
			if(sequeceBrokerLevelMapper.insert(newSequence) == 1)
				sequence = newSequence;
		}
		
		SequenceBrokerLevel updSequence = tryGainCode(sequence, name);
		
		int tryNum = 1;
		while(sequence.getVersion() + 1 != updSequence.getVersion()){
			
			if(++tryNum > 3) throw new BizException("尝试3次无法取到序列" + name);
			
			sequence = updSequence;
			updSequence = tryGainCode(updSequence, name);
			
		}
		
		logger.debug("获取序列:【{}】,值:【{}】, 程序尝试:【{}】次", name, updSequence.getCurrentValue(), tryNum);
		return updSequence.getCurrentValue();
	}
	
	private SequenceBrokerLevel tryGainCode(SequenceBrokerLevel sequence, String name) throws BizException{
		

		if(sequence.getCurrentValue() >= sequence.getMaxValue() && IS_CYCLE_NO.equals(sequence.getIsCycle())){
			
			throw new BizException("序列已达到最大值");
		} 
		else if(sequence.getCurrentValue() >= sequence.getMaxValue() && IS_CYCLE_YES.equals(sequence.getIsCycle())){
			
			sequeceBrokerLevelMapper.cycleSequence(name);
		} 
		else {
			
			sequeceBrokerLevelMapper.incrementSequence(name);
		}
		
		return sequeceBrokerLevelMapper.selectByPrimaryKey(name);
	}
	
	/**
	 * 格式化序列号成指定长度
	 * 1、序列号长度过长: 从右边截取n位
	 * 2、序列号过短: 左边填"0"
	 * 3、序列号与指定长度相同: 不处理
	 * @param sequenceValue
	 * @param valueLenth
	 * @return
	 */
	private static String formatSequence(String sequenceValue, int valueLenth){
		
		if (sequenceValue.length() < valueLenth) {
			
			StringBuilder addCode = new StringBuilder("");
			for (int i = sequenceValue.length(); i < valueLenth; i ++){
				
				addCode.append("0");
			}
			sequenceValue = addCode.append(sequenceValue).toString();
		}
		else if (sequenceValue.length() > valueLenth){
			sequenceValue =  sequenceValue.substring(sequenceValue.length() - valueLenth);
		}
		
		return sequenceValue;
	}
}
