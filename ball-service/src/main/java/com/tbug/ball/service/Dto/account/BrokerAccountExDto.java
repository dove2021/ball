package com.tbug.ball.service.Dto.account;

public class BrokerAccountExDto extends BrokerAccountDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String brokerCode;
	
	private String signCode;

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
}
