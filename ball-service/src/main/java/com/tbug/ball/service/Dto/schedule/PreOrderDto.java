package com.tbug.ball.service.Dto.schedule;

import java.io.Serializable;
import java.math.BigDecimal;

public class PreOrderDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer customerId;
	
	private Integer scheduleId;
	
	private String stakeType;	// 下注方向 1:涨 2:平 3:输
	
	private String stakeCode;	// 下注标的
	
	private Integer orderNum;	// 下注手数
	
	private BigDecimal totalFee;
	
	private BigDecimal stakeAmount;
	
	private BigDecimal oddsScale;
	
	private BigDecimal totalAmount;

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(Integer scheduleId) {
		this.scheduleId = scheduleId;
	}

	public String getStakeType() {
		return stakeType;
	}

	public void setStakeType(String stakeType) {
		this.stakeType = stakeType;
	}

	public String getStakeCode() {
		return stakeCode;
	}

	public void setStakeCode(String stakeCode) {
		this.stakeCode = stakeCode;
	}

	public BigDecimal getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	public BigDecimal getStakeAmount() {
		return stakeAmount;
	}

	public void setStakeAmount(BigDecimal stakeAmount) {
		this.stakeAmount = stakeAmount;
	}

	public BigDecimal getOddsScale() {
		return oddsScale;
	}

	public void setOddsScale(BigDecimal oddsScale) {
		this.oddsScale = oddsScale;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	
}
