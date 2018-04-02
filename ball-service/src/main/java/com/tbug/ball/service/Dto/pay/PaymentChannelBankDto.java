package com.tbug.ball.service.Dto.pay;

import java.io.Serializable;

public class PaymentChannelBankDto implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer channelBankId;

    private Integer channelId;

    private Integer bankId;

    private String bankCode;

    public Integer getChannelBankId() {
        return channelBankId;
    }

    public void setChannelBankId(Integer channelBankId) {
        this.channelBankId = channelBankId;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode == null ? null : bankCode.trim();
    }
}