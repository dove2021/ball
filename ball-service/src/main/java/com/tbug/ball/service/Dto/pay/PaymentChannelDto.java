package com.tbug.ball.service.Dto.pay;

import java.io.Serializable;
import java.util.Date;

public class PaymentChannelDto implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer channelId;

    private String channelName;

    private String rechargeSwitch;

    private String withdrawSwitch;

    private Date createTime;

    private Date modifyTime;

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

    public String getRechargeSwitch() {
        return rechargeSwitch;
    }

    public void setRechargeSwitch(String rechargeSwitch) {
        this.rechargeSwitch = rechargeSwitch == null ? null : rechargeSwitch.trim();
    }

    public String getWithdrawSwitch() {
        return withdrawSwitch;
    }

    public void setWithdrawSwitch(String withdrawSwitch) {
        this.withdrawSwitch = withdrawSwitch == null ? null : withdrawSwitch.trim();
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
}