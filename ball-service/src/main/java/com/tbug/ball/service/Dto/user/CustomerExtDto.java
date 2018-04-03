package com.tbug.ball.service.Dto.user;

import java.math.BigDecimal;

public class CustomerExtDto extends CustomerDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String memberName;
	
	private String memberCode;
	
	private String brokerCode;
	
	private String signCode;
	
	private BigDecimal balance;

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getBrokerCode() {
		return brokerCode;
	}

	public void setBrokerCode(String brokerCode) {
		this.brokerCode = brokerCode;
	}

	public String getSignCode() {
		return signCode;
	}

	public void setSignCode(String signCode) {
		this.signCode = signCode;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
}
