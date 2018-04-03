package com.tbug.ball.service.model.order;

import java.math.BigDecimal;

public class BrokerProfit {
    private Integer id;

    private Byte levelNum;

    private BigDecimal amountScale;

    private BigDecimal scaleOne;

    private BigDecimal scaleTwo;

    private BigDecimal scaleThree;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Byte getLevelNum() {
        return levelNum;
    }

    public void setLevelNum(Byte levelNum) {
        this.levelNum = levelNum;
    }

    public BigDecimal getAmountScale() {
        return amountScale;
    }

    public void setAmountScale(BigDecimal amountScale) {
        this.amountScale = amountScale;
    }

    public BigDecimal getScaleOne() {
        return scaleOne;
    }

    public void setScaleOne(BigDecimal scaleOne) {
        this.scaleOne = scaleOne;
    }

    public BigDecimal getScaleTwo() {
        return scaleTwo;
    }

    public void setScaleTwo(BigDecimal scaleTwo) {
        this.scaleTwo = scaleTwo;
    }

    public BigDecimal getScaleThree() {
        return scaleThree;
    }

    public void setScaleThree(BigDecimal scaleThree) {
        this.scaleThree = scaleThree;
    }
}