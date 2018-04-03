package com.tbug.ball.service.model.pay;

public class CustomerWithdraw {
    private Integer id;

    private String linkMan;

    private String linkPhoneNo;

    private String accountNo;

    private String accountName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan == null ? null : linkMan.trim();
    }

    public String getLinkPhoneNo() {
        return linkPhoneNo;
    }

    public void setLinkPhoneNo(String linkPhoneNo) {
        this.linkPhoneNo = linkPhoneNo == null ? null : linkPhoneNo.trim();
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo == null ? null : accountNo.trim();
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName == null ? null : accountName.trim();
    }
}