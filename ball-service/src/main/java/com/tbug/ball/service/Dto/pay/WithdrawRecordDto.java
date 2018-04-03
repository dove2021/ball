package com.tbug.ball.service.Dto.pay;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class WithdrawRecordDto implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private Integer id;

    private String withdrawCode;

    private Integer customerId;

    private String customerCode;

    private String memberCode;

    private BigDecimal withdrawAmount;

    private BigDecimal withdrawFee;

    private Date applyDate;

    private Date finishDate;

    private String withdrawType;

    private String status;

    private Integer channelId;

    private String remark;

    private String operater;

    private String operateInfo;

    public BigDecimal getWithdrawFee() {
		return withdrawFee;
	}

	public void setWithdrawFee(BigDecimal withdrawFee) {
		this.withdrawFee = withdrawFee;
	}

	public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOperater() {
		return operater;
	}

	public void setOperater(String operater) {
		this.operater = operater;
	}

	public String getOperateInfo() {
		return operateInfo;
	}

	public void setOperateInfo(String operateInfo) {
		this.operateInfo = operateInfo;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWithdrawCode() {
        return withdrawCode;
    }

    public void setWithdrawCode(String withdrawCode) {
        this.withdrawCode = withdrawCode == null ? null : withdrawCode.trim();
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode == null ? null : customerCode.trim();
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode == null ? null : memberCode.trim();
    }

    public BigDecimal getWithdrawAmount() {
        return withdrawAmount;
    }

    public void setWithdrawAmount(BigDecimal withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public String getWithdrawType() {
        return withdrawType;
    }

    public void setWithdrawType(String withdrawType) {
        this.withdrawType = withdrawType == null ? null : withdrawType.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}