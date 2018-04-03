package com.tbug.ball.service.model.user;

import java.util.Date;

public class Broker {
    private Integer brokerId;
    
    private Integer parentId;

    private Integer memberId;

    private String memberName;

    private String brokerCode;

    private String signCode;

    private Byte levelNum;

    private String levelCode;

    private String nickName;

    private String creater;

    private Date createTime;

    private String lastModifyUser;

    private Date modifyTime;

    private String loginName;

    private String password;

    private String salt;

    private String status;

    private Byte pwdErrTimes;

    private Date lockTime;

    private Date unlockTime;

    public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
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

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName == null ? null : memberName.trim();
    }

    public String getBrokerCode() {
        return brokerCode;
    }

    public void setBrokerCode(String brokerCode) {
        this.brokerCode = brokerCode == null ? null : brokerCode.trim();
    }

    public String getSignCode() {
        return signCode;
    }

    public void setSignCode(String signCode) {
        this.signCode = signCode == null ? null : signCode.trim();
    }

    public Byte getLevelNum() {
        return levelNum;
    }

    public void setLevelNum(Byte levelNum) {
        this.levelNum = levelNum;
    }

    public String getLevelCode() {
        return levelCode;
    }

    public void setLevelCode(String levelCode) {
        this.levelCode = levelCode == null ? null : levelCode.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getLastModifyUser() {
        return lastModifyUser;
    }

    public void setLastModifyUser(String lastModifyUser) {
        this.lastModifyUser = lastModifyUser == null ? null : lastModifyUser.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName == null ? null : loginName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Byte getPwdErrTimes() {
        return pwdErrTimes;
    }

    public void setPwdErrTimes(Byte pwdErrTimes) {
        this.pwdErrTimes = pwdErrTimes;
    }

    public Date getLockTime() {
        return lockTime;
    }

    public void setLockTime(Date lockTime) {
        this.lockTime = lockTime;
    }

    public Date getUnlockTime() {
        return unlockTime;
    }

    public void setUnlockTime(Date unlockTime) {
        this.unlockTime = unlockTime;
    }
}