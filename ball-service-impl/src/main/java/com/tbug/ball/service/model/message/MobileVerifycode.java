package com.tbug.ball.service.model.message;

import java.util.Date;

public class MobileVerifycode {
    private Integer verifycodeId;

    private String phoneNo;

    private String verifyType;

    private String sendDay;

    private Short sendTimes;

    private Date createTime;

    private Integer verifycodeValid;

    private String verifycodeStr;

    private String verifyResult;

    private Short verifyErrNum;

    public Integer getVerifycodeId() {
        return verifycodeId;
    }

    public void setVerifycodeId(Integer verifycodeId) {
        this.verifycodeId = verifycodeId;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo == null ? null : phoneNo.trim();
    }

    public String getVerifyType() {
        return verifyType;
    }

    public void setVerifyType(String verifyType) {
        this.verifyType = verifyType == null ? null : verifyType.trim();
    }

    public String getSendDay() {
        return sendDay;
    }

    public void setSendDay(String sendDay) {
        this.sendDay = sendDay == null ? null : sendDay.trim();
    }

    public Short getSendTimes() {
        return sendTimes;
    }

    public void setSendTimes(Short sendTimes) {
        this.sendTimes = sendTimes;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getVerifycodeValid() {
        return verifycodeValid;
    }

    public void setVerifycodeValid(Integer verifycodeValid) {
        this.verifycodeValid = verifycodeValid;
    }

    public String getVerifycodeStr() {
        return verifycodeStr;
    }

    public void setVerifycodeStr(String verifycodeStr) {
        this.verifycodeStr = verifycodeStr == null ? null : verifycodeStr.trim();
    }

    public String getVerifyResult() {
        return verifyResult;
    }

    public void setVerifyResult(String verifyResult) {
        this.verifyResult = verifyResult == null ? null : verifyResult.trim();
    }

    public Short getVerifyErrNum() {
        return verifyErrNum;
    }

    public void setVerifyErrNum(Short verifyErrNum) {
        this.verifyErrNum = verifyErrNum;
    }
}