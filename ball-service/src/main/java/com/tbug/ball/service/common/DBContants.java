package com.tbug.ball.service.common;

public class DBContants {

	public interface DB{
		 
		String NULL_STR = "-";
		
		int NULL_INT = 0;
		
	}
	
	public interface BrokerAccountConst{
		/** 正常 */
		String STATUS_NORMAL = "1";
		/** 冻结 */
		String STATUS_FREEZE = "2";
		/** 锁定 */
		String STATUS_LOCK = "3";
		/** 解锁 */
		String STATUS_UNLOCK = "4";
		/** 注销 */
		String STATUS_CANCEL = "5";
	}
	
	public interface CustomerAccountConst{
		/** 正常 */
		String STATUS_NORMAL = "1";
		/** 冻结 */
		String STATUS_FREEZE = "2";
		/** 锁定 */
		String STATUS_LOCK = "3";
		/** 解锁 */
		String STATUS_UNLOCK = "4";
		/** 注销 */
		String STATUS_CANCEL = "5";
	}
	
	public interface MemberAccountConst{
		/** 正常 */
		String STATUS_NORMAL = "1";
		/** 冻结 */
		String STATUS_FREEZE = "2";
		/** 锁定 */
		String STATUS_LOCK = "3";
		/** 解锁 */
		String STATUS_UNLOCK = "4";
		/** 注销 */
		String STATUS_CANCEL = "5";		
	}
	
	public interface BrokerConst{
		/** 正常 */
		String STATUS_NORMAL = "1";
		/** 冻结 */
		String STATUS_FREEZE = "2";
		/** 锁定 */
		String STATUS_LOCK = "3";
		/** 解锁 */
		String STATUS_UNLOCK = "4";
		/** 注销 */
		String STATUS_CANCEL = "5";			
	}
	
	public interface MemberConst{
		/** 正常 */
		String STATUS_NORMAL = "1";
		/** 冻结 */
		String STATUS_FREEZE = "2";
		/** 锁定 */
		String STATUS_LOCK = "3";
		/** 解锁 */
		String STATUS_UNLOCK = "4";
		/** 注销 */
		String STATUS_CANCEL = "5";				
	}

	public interface CustomerConst{
		/** 正常 */
		String STATUS_NORMAL = "1";
		/** 冻结 */
		String STATUS_FREEZE = "2";
		/** 锁定 */
		String STATUS_LOCK = "3";
		/** 解锁 */
		String STATUS_UNLOCK = "4";
		/** 注销 */
		String STATUS_CANCEL = "5";			
	}
	
	public interface TradeUserConst{
		/** 正常 */
		String STATUS_NORMAL = "1";
		/** 冻结 */
		String STATUS_FREEZE = "2";
		/** 锁定 */
		String STATUS_LOCK = "3";
		/** 解锁 */
		String STATUS_UNLOCK = "4";
		/** 注销 */
		String STATUS_CANCEL = "5";		
	}
	
	public interface BrokerAccountFlowConst{
		/** 代理商分佣收入 */
		String TRADE_TYPE_CLEAN_IN = "01";
		/** 提现支出 */
		String TRADE_TYPE_WITHDRAW_OUT = "02";
	}
	
	public interface CustomerAccountFlowConst{
		/** 充值收入 */
		String TRADE_TYPE_CHARGE_IN = "01";
		/** 提现支出 */
		String TRADE_TYPE_WITHDRAW_OUT = "02";
		/** 下注金额 */
		String TRADE_TYPE_STAKE_OUT = "03";
		/** 结算收入 */
		String TRADE_TYPE_CLEAN_IN = "04";
		/** 下注手续费 */
		String TRADE_TYPE_FEE_OUT = "05";
	}
	
	public interface MemberAccountFlowConst{
		/** 手续费收入 */
		String TRADE_TYPE_FEE_IN = "01";
		/** 结算收入 */
		String TRADE_TYPE_CLEAN_IN = "02";
		/** 下单预付款支出 */
		String TRADE_TYPE_STAKE_OUT = "03";
		/** 提现支出 */
		String TRADE_TYPE_WITHDRAW_OUT = "04";
		/** 会员入金 */
		String TRADE_TYPE_CHARGE_IN = "05";
	}
	
	public interface TradeAccountFlowConst{
		/** 手续费收入 */
		String TRADE_TYPE_FEE_IN = "01";
		/** 下注金额: 客户 */
		String TRADE_TYPE_STAKE_CUSTOMER_IN = "02";
		/** 保证金: 会员 */
		String TRADE_TYPE_STAKE_MEMBER_IN = "03";
		/** 结算支出: 客户 */
		String TRADE_TYPE_CLEAN_CUSTOMER_OUT = "04";
		/** 结算支出: 会员 */
		String TRADE_TYPE_CLEAN_MEMBER_OUT = "05";
		/** 结算支出: 代理商 */
		String TRADE_TYPE_CLEAN_BROKER_OUT = "06";
		/** 提现支出 */
		String TRADE_TYPE_WITHDRAW_OUT = "07";
	}
	
	public interface ScheduleConst{
		/** 新建 */
		String STATUS_NEW = "01";
		/** 下注 */
		String STATUS_START = "02";
		/** 停止下注 */
		String STATUS_END = "03";
		/** 已出结果 */
		String STATUS_RESULT = "04";
		/** 正在结算 */
		String STATUS_CELAN = "05";
		/** 完成 */
		String STATUS_FINISH = "06";
		
		/** 未知 */
		String GAME_RESULT_0 = "0";
		/** 赢 */
		String GAME_RESULT_1 = "1";
		/** 平 */
		String GAME_RESULT_2 = "2";
		/** 输 */
		String GAME_RESULT_3 = "3";
	}
	
	public interface StakeOrderConst{
		/** 下注方向: 赢 */
		String STAKE_TYPE_WIN = "1";
		/** 下注方向: 平 */
		String STAKE_TYPE_DRAW = "2";
		/** 下注方向: 输 */
		String STAKE_TYPE_LOSE = "3";
		
		/** 正常 */
		String ORDER_STATUS_NORMAL = "1";
		/** 结算 */
		String ORDER_STATUS_CLEAN = "2";
		/** 完成 */
		String ORDER_STATUS_FINISH = "3";
		
		/** 是否结算: 否 */
		String IS_CLEAN_N = "0";
		/** 是否结算: 是 */
		String IS_CLEAN_Y = "1";
		
		/** 支付方式: 资金 */
		String PAY_TYPE_CASH = "1";
		/** 支付方式: 票 */
		String PAY_TYPE_TICKET = "2";
	}
	
	public interface StakeOrderHConst{
		/** 下注方向: 赢 */
		String STAKE_TYPE_WIN = "1";
		/** 下注方向: 平 */
		String STAKE_TYPE_DRAW = "2";
		/** 下注方向: 输 */
		String STAKE_TYPE_LOSE = "3";
		
		/** 正常 */
		String ORDER_STATUS_NORMAL = "1";
		/** 结算 */
		String ORDER_STATUS_CLEAN = "2";
		/** 完成 */
		String ORDER_STATUS_FINISH = "3";
		
		/** 是否结算: 否 */
		String IS_CLEAN_N = "0";
		/** 是否结算: 是 */
		String IS_CLEAN_Y = "1";
		
		/** 支付方式: 资金 */
		String PAY_TYPE_CASH = "1";
		/** 支付方式: 票 */
		String PAY_TYPE_TICKET = "2";
	}
	
	
	
	public interface OrderCleanConst{
		/** 是否兑奖: 否 */
		String IS_DRAW_N = "0";
		/** 是否兑奖: 是 */
		String IS_DRAW_Y = "1";
	}
}
