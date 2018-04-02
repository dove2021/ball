package com.tbug.ball.service.model.schedule;

import java.math.BigDecimal;
import java.util.Date;

public class Stake {
    private Integer id;

    private String stakeCode;

    private Integer price;

    private String status;

    private Date createTime;

    private Integer stakeLimitQuantity;

    private BigDecimal tradeFee;

    private BigDecimal memberFee;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStakeCode() {
        return stakeCode;
    }

    public void setStakeCode(String stakeCode) {
        this.stakeCode = stakeCode == null ? null : stakeCode.trim();
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStakeLimitQuantity() {
        return stakeLimitQuantity;
    }

    public void setStakeLimitQuantity(Integer stakeLimitQuantity) {
        this.stakeLimitQuantity = stakeLimitQuantity;
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
}