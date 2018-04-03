package com.tbug.ball.service.Dto.account;

public class CustomerAccountExDto extends CustomerAccountDto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String phoneNo;

	private String customerCode;
	
	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
}
