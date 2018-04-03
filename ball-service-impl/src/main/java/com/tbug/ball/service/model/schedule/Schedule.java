package com.tbug.ball.service.model.schedule;

import java.math.BigDecimal;
import java.util.Date;

public class Schedule {
    private Integer id;

    private Integer memberId;
    
    private String memberCode;

    private String memberName;

    private String title;

    private String aName;

    private Byte aScore;

    private String bName;

    private Byte bScore;

    private Date createDate;

    private String createPerson;

    private Date startTime;

    private Date endTime;

    private String gameResult;

    private String status;

    private BigDecimal oddsWin;

    private BigDecimal oddsDraw;

    private BigDecimal oddsLose;

    public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName == null ? null : memberName.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getaName() {
        return aName;
    }

    public void setaName(String aName) {
        this.aName = aName == null ? null : aName.trim();
    }

    public Byte getaScore() {
        return aScore;
    }

    public void setaScore(Byte aScore) {
        this.aScore = aScore;
    }

    public String getbName() {
        return bName;
    }

    public void setbName(String bName) {
        this.bName = bName == null ? null : bName.trim();
    }

    public Byte getbScore() {
        return bScore;
    }

    public void setbScore(Byte bScore) {
        this.bScore = bScore;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson == null ? null : createPerson.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getGameResult() {
        return gameResult;
    }

    public void setGameResult(String gameResult) {
        this.gameResult = gameResult == null ? null : gameResult.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public BigDecimal getOddsWin() {
        return oddsWin;
    }

    public void setOddsWin(BigDecimal oddsWin) {
        this.oddsWin = oddsWin;
    }

    public BigDecimal getOddsDraw() {
        return oddsDraw;
    }

    public void setOddsDraw(BigDecimal oddsDraw) {
        this.oddsDraw = oddsDraw;
    }

    public BigDecimal getOddsLose() {
        return oddsLose;
    }

    public void setOddsLose(BigDecimal oddsLose) {
        this.oddsLose = oddsLose;
    }
}