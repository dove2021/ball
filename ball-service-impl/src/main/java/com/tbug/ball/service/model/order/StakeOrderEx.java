package com.tbug.ball.service.model.order;

public class StakeOrderEx extends StakeOrder {

	private String customerPhoneNo;
	
	private String brokerSignCode;
	
	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCustomerPhoneNo() {
		return customerPhoneNo;
	}

	public void setCustomerPhoneNo(String customerPhoneNo) {
		this.customerPhoneNo = customerPhoneNo;
	}

	public String getBrokerSignCode() {
		return brokerSignCode;
	}

	public void setBrokerSignCode(String brokerSignCode) {
		this.brokerSignCode = brokerSignCode;
	}
	
}
