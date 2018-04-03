package com.tbug.ball.service.Dto.pay;

import java.io.Serializable;

public class BankDto implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer bankId;

    private String bankName;

    private Integer sort;

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}