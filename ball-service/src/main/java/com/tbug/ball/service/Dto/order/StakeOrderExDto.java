package com.tbug.ball.service.Dto.order;

public class StakeOrderExDto extends StakeOrderDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String customerPhoneNo;
	
	private String memberCode;
	
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

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getBrokerSignCode() {
		return brokerSignCode;
	}

	public void setBrokerSignCode(String brokerSignCode) {
		this.brokerSignCode = brokerSignCode;
	}
}
