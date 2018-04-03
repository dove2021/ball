package com.tbug.ball.service.model.order;

import java.math.BigDecimal;
import java.util.Date;

public class OrderClean {
    private Integer id;

    private String cleaner;

    private Date cleanTime;

    private Integer customerId;

    private BigDecimal cleanAmount;

    private String isDraw;

    private Date drawTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCleaner() {
        return cleaner;
    }

    public void setCleaner(String cleaner) {
        this.cleaner = cleaner == null ? null : cleaner.trim();
    }

    public Date getCleanTime() {
        return cleanTime;
    }

    public void setCleanTime(Date cleanTime) {
        this.cleanTime = cleanTime;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getCleanAmount() {
        return cleanAmount;
    }

    public void setCleanAmount(BigDecimal cleanAmount) {
        this.cleanAmount = cleanAmount;
    }

    public String getIsDraw() {
        return isDraw;
    }

    public void setIsDraw(String isDraw) {
        this.isDraw = isDraw == null ? null : isDraw.trim();
    }

    public Date getDrawTime() {
        return drawTime;
    }

    public void setDrawTime(Date drawTime) {
        this.drawTime = drawTime;
    }
}