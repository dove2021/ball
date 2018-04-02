package com.tbug.ball.service.Dto.schedule;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ScheduleOddsDto implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Integer scheduleId;

    private String oddsType;

    private BigDecimal oddsScale;

    private Date createTime;

    private String creater;

    private Integer memberId;

    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getOddsType() {
        return oddsType;
    }

    public void setOddsType(String oddsType) {
        this.oddsType = oddsType == null ? null : oddsType.trim();
    }

    public BigDecimal getOddsScale() {
        return oddsScale;
    }

    public void setOddsScale(BigDecimal oddsScale) {
        this.oddsScale = oddsScale;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}