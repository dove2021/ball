package com.tbug.ball.service.model.pay;

import java.math.BigDecimal;
import java.util.Date;

public class PaymentChannel {
    private Integer channelId;

    private String channelName;

    private Date createTime;

    private Date modifyTime;

    private String chargeSwitch;

    private Integer chargeTimes;

    private BigDecimal chargeFee;

    private String withdrawSwitch;

    private BigDecimal withdrawMin;

    private BigDecimal withdrawMax;

    private BigDecimal withdrawFee;

    private Integer withdrawTimes;

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName == null ? null : channelName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getChargeSwitch() {
        return chargeSwitch;
    }

    public void setChargeSwitch(String chargeSwitch) {
        this.chargeSwitch = chargeSwitch == null ? null : chargeSwitch.trim();
    }

    public Integer getChargeTimes() {
        return chargeTimes;
    }

    public void setChargeTimes(Integer chargeTimes) {
        this.chargeTimes = chargeTimes;
    }

    public BigDecimal getChargeFee() {
        return chargeFee;
    }

    public void setChargeFee(BigDecimal chargeFee) {
        this.chargeFee = chargeFee;
    }

    public String getWithdrawSwitch() {
        return withdrawSwitch;
    }

    public void setWithdrawSwitch(String withdrawSwitch) {
        this.withdrawSwitch = withdrawSwitch == null ? null : withdrawSwitch.trim();
    }

    public BigDecimal getWithdrawMin() {
        return withdrawMin;
    }

    public void setWithdrawMin(BigDecimal withdrawMin) {
        this.withdrawMin = withdrawMin;
    }

    public BigDecimal getWithdrawMax() {
        return withdrawMax;
    }

    public void setWithdrawMax(BigDecimal withdrawMax) {
        this.withdrawMax = withdrawMax;
    }

    public BigDecimal getWithdrawFee() {
        return withdrawFee;
    }

    public void setWithdrawFee(BigDecimal withdrawFee) {
        this.withdrawFee = withdrawFee;
    }

    public Integer getWithdrawTimes() {
        return withdrawTimes;
    }

    public void setWithdrawTimes(Integer withdrawTimes) {
        this.withdrawTimes = withdrawTimes;
    }
}