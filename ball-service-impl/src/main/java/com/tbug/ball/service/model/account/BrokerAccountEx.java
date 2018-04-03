package com.tbug.ball.service.model.account;

public class BrokerAccountEx extends BrokerAccount {

	private String brokerCode;
	
	private String signCode;
	
	private String levelCode;

	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
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
	
}
