package com.tbug.ball.service.biz.system;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tbug.ball.service.biz.BizException;
import com.tbug.ball.service.dao.system.SequenceMapper;
import com.tbug.ball.service.model.system.Sequence;

/**
 * 序列编号生成器
 * @author Tbug at 2017-05-10
 */
@Service
public class SequenceBiz {
	private static final Logger logger = LoggerFactory.getLogger(SequenceBiz.class);
	
	
	/** 是否循环：否 */
	private static final String IS_CYCLE_NO = "0";
	/** 是否循环：是 */
	private static final String IS_CYCLE_YES = "1";
	
	@Resource
	private SequenceMapper sequenceMapper;
	
	/**
	 * 下一序列号
	 * @param name 序列名
	 * @return
	 * @throws BizException
	 */
	public synchronized int getNextVal(String name) throws BizException{
		
		Sequence sequence = sequenceMapper.selectByPrimaryKey(name);
		if(null == sequence){
			
			Sequence newSequence = new Sequence();
			newSequence.setName(name);
			newSequence.setCurrentValue(0);
			newSequence.setIncrement(1);
			newSequence.setIsCycle(IS_CYCLE_YES);
			newSequence.setMaxValue(99999999);
			newSequence.setVersion(0);
			
			if(sequenceMapper.insert(newSequence) == 1)
				sequence = newSequence;
		}
		
		Sequence updSequence = tryGainCode(sequence, name);
		
		int tryNum = 1;
		while(sequence.getVersion() + 1 != updSequence.getVersion()){
			
			if(++tryNum > 3) throw new BizException("尝试3次无法取到序列" + name);
			
			sequence = updSequence;
			updSequence = tryGainCode(updSequence, name);
			
		}
		
		logger.debug("获取序列:【{}】,值:【{}】, 程序尝试:【{}】次", name, updSequence.getCurrentValue(), tryNum);
		return updSequence.getCurrentValue();
	}
	
	private Sequence tryGainCode(Sequence sequence, String name) throws BizException{
		

		if(sequence.getCurrentValue() >= sequence.getMaxValue() && IS_CYCLE_NO.equals(sequence.getIsCycle())){
			
			throw new BizException("序列已达到最大值");
		} 
		else if(sequence.getCurrentValue() >= sequence.getMaxValue() && IS_CYCLE_YES.equals(sequence.getIsCycle())){
			
			sequenceMapper.cycleSequence(name);
		} 
		else {
			
			sequenceMapper.incrementSequence(name);
		}
		
		return sequenceMapper.selectByPrimaryKey(name);
	}
	
	/**
	 * 当前序列号
	 * @param name 序列名
	 * @return
	 * @throws BizException
	 */
	public int getCurrentValue(String name) throws BizException{
		Sequence sequence = sequenceMapper.selectByPrimaryKey(name);
		if(null == sequence){
			Sequence newSequence = new Sequence();
			newSequence.setName(name);
			newSequence.setCurrentValue(0);
			newSequence.setIncrement(1);
			newSequence.setIsCycle(IS_CYCLE_YES);
			newSequence.setMaxValue(99999999);
			newSequence.setVersion(0);
			
			if(sequenceMapper.insert(newSequence) == 1)
				sequence = newSequence;
		}
		
		return sequence.getCurrentValue();
	}
}