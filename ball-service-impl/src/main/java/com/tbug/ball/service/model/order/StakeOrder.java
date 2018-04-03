package com.tbug.ball.service.model.order;

import java.math.BigDecimal;
import java.util.Date;

public class StakeOrder {
    private Integer id;

    private String orderNo;
    
    private Integer customerId;

    private String customerCode;

    private Integer brokerId;

    private String brokerCode;

    private Integer memberId;

    private String memberCode;

    private Integer scheduleId;

    private String stakeType;

    private String stakeCode;

    private BigDecimal stakePrice;

    private Short orderNum;

    private BigDecimal tradeFee;

    private BigDecimal memberFee;

    private BigDecimal stakeAmount;

    private BigDecimal oddsScale;

    private BigDecimal totalAmount;

    private String payType;

    private BigDecimal preCleanAmount;

    private String orderStatus;

    private Date createTime;

    private String isClean;

    private Date cleanTime;

    public String getOrderNo() {
		return orderNo;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getBrokerCode() {
		return brokerCode;
	}

	public void setBrokerCode(String brokerCode) {
		this.brokerCode = brokerCode;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getBrokerId() {
        return brokerId;
    }

    public void setBrokerId(Integer brokerId) {
        this.brokerId = brokerId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
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
        this.stakeType = stakeType == null ? null : stakeType.trim();
    }

    public String getStakeCode() {
        return stakeCode;
    }

    public void setStakeCode(String stakeCode) {
        this.stakeCode = stakeCode == null ? null : stakeCode.trim();
    }

    public BigDecimal getStakePrice() {
        return stakePrice;
    }

    public void setStakePrice(BigDecimal stakePrice) {
        this.stakePrice = stakePrice;
    }

    public Short getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Short orderNum) {
        this.orderNum = orderNum;
    }

    public BigDecimal getTradeFee() {
        return tradeFee;
    }

    public void setTradeFee(BigDecimal tradeFee) {
        this.tradeFee = tradeFee;
    }

    public BigDecimal getMemberFee() {
        return memberFee;
    }

    public void setMemberFee(BigDecimal memberFee) {
        this.memberFee = memberFee;
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

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType == null ? null : payType.trim();
    }

    public BigDecimal getPreCleanAmount() {
        return preCleanAmount;
    }

    public void setPreCleanAmount(BigDecimal preCleanAmount) {
        this.preCleanAmount = preCleanAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus == null ? null : orderStatus.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getIsClean() {
        return isClean;
    }

    public void setIsClean(String isClean) {
        this.isClean = isClean == null ? null : isClean.trim();
    }

    public Date getCleanTime() {
        return cleanTime;
    }

    public void setCleanTime(Date cleanTime) {
        this.cleanTime = cleanTime;
    }
}