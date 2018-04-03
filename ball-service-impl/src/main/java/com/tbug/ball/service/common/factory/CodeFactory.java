package com.tbug.ball.service.common.factory;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.httpclient.util.DateUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.tbug.ball.service.biz.BizException;
import com.tbug.ball.service.biz.system.SequenceBiz;

/**
 * 编号生成工厂
 * @author Tbug at 2017-05-10
 */
@Component
public class CodeFactory {

	/** 支付编号 */
	public static final String CHARGE_CODE = "CHARGE_CODE";
	/** 事务编号 */
	public static final String TRANSCATION_CODE = "TRANSCATION_CODE";
	/** 交易流水表 */
	public static final String FLOW_NO = "FLOW_NO";
	/** 交易流水表 */
	public static final String FLOW_CODE = "FLOW_CODE";
	/** 提现订单编号 */
	public static final String WITHDRAE_CODE = "WITHDRAE_CODE";
	/** 代理商编号 */
	public static final String BROKER_CODE = "BROKER_CODE";
	/** 后台管理员编号 */
	public static final String USER_CODE = "USER_CODE";
	/** 客户编号 */
	public static final String CUSTOMER_CODE = "CUSTOMER_CODE";
	/** 订单编号 */
	public static final String ORDER_NO = "ORDER_NO";
	
	@Resource
	SequenceBiz sequenceBiz;
	
	/**
	 * 生成下注订单编号
	 * 规则: OD 2位  + yyMMddHHmm 10位 + 顺序增长序列 4位
	 * @return
	 * @throws BizException 
	 */
	public String getOrderNo() throws BizException{
		String DateStr = DateUtil.formatDate(new Date(), "yyMMddHHmm");
		String value = "" + sequenceBiz.getNextVal(ORDER_NO);
		return "OD" + DateStr + value;
	}
	
	/**
	 * 生成入金编号
	 * 规则: RJ 2位  + yyMMddHH 8位 + 顺序增长序列 6位
	 * @return
	 * @throws BizException 
	 */
	public String getChargeCode() throws BizException{
		
		String DateStr = DateUtil.formatDate(new Date(), "yyMMddHH");
		
		String value = "" + sequenceBiz.getNextVal(CHARGE_CODE);
		
		value = formatSequence(value, 6);
		
		return "RJ" + DateStr + value;
	}

	/**
	 * 生成出金编号
	 * 规则: CJ 2位 + yyMMddHH 8位 + 顺序增长序列流水 6位 
	 * @return
	 * @throws BizException
	 */
	public String getWithdrawCode() throws BizException{
		
		String DateStr = DateUtil.formatDate(new Date(), "yyMMddHH");
		
		String value = "" + sequenceBiz.getNextVal(WITHDRAE_CODE);
		
		value = formatSequence(value, 6);
		
		return "CJ" + DateStr + value;
	}
	
	/**
	 * 生成事务编号
	 * 规则： TRSC 4位  + yyyyMMdd 10位   +  顺序增长序列 6位
	 * @return
	 * @throws BizException
	 */
	public String getTranscationCode() throws BizException{
		
		String DateStr = DateUtil.formatDate(new Date(), "yyyyMMddHH");
		
		String value = "" + sequenceBiz.getNextVal(TRANSCATION_CODE);
		
		value = formatSequence(value, 6);
		
		return "TRSC" + DateStr + value;
		
	}
	
	/**
	 * 生成资金交易流水表
	 * 规则： TD 2位     + yyyyMMdd 8位  +  顺序增长序列流水10位
	 * @return
	 * @throws BizException
	 */
	public String getFlowNo() throws BizException{
		String DateStr = DateUtil.formatDate(new Date(), "yyyyMMdd");
		
		String value = "" + sequenceBiz.getNextVal(FLOW_NO);
		
		value = formatSequence(value, 10);
		
		return "TD" + DateStr + value;		
	}
	
	/**
	 * 生成资金交易流水表
	 * 规则： TD 2位     + yyyyMMdd 8位  +  顺序增长序列流水10位
	 * @return
	 * @throws BizException
	 */
	public String getFlowCode() throws BizException{
		String DateStr = DateUtil.formatDate(new Date(), "yyyyMMdd");
		
		String value = "" + sequenceBiz.getNextVal(FLOW_CODE);
		
		value = formatSequence(value, 10);
		
		return "TD" + DateStr + value;		
	}
	
	/**
	 * 代理商编号
	 * 规则: B + 会员编号 + 顺序增长序列流水6位  + 4位随机数
	 * @return
	 * @throws BizException
	 */
	public String getBrokerCode(String memberCode) throws BizException{
		
		String value = "" + sequenceBiz.getNextVal(BROKER_CODE);
		
		value = formatSequence(value, 6);
		
		return "B" + (StringUtils.isEmpty(memberCode) ? "00000" : memberCode) + value + String.valueOf((int) ((Math.random() *9 + 1) *1000));		
	}
	
	/**
	 * 后台用户编号: 表sys_user 字段 user_code
	 * 规则: U + yyMMdd + 00001 5位流水
	 * @return
	 * @throws BizException 
	 */
	public String getUserCode() throws BizException{
		
		String dateStr = DateUtil.formatDate(new Date(), "yyMMdd");
		
		String value = "" + sequenceBiz.getNextVal(USER_CODE);
		value = formatSequence(value, 5);
		
		return "U" + dateStr + value;
		
	}
	
	/**
	 * 客户编号： 表customer 字段 customer_code
	 * 规则:  C + 5位会员code + 6位 年月日 + 0001 4位流水
	 * @return
	 * @throws BizException 
	 */
	public String getCustomerCode(String memberCode) throws BizException{
		
		String dateStr = DateUtil.formatDate(new Date(), "yyMMdd");
		
		String value = "" + sequenceBiz.getNextVal(CUSTOMER_CODE + memberCode);
		value = formatSequence(value, 4);
		
		return "C" + memberCode + dateStr  + value;
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
