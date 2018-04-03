package com.tbug.ball.service.Dto.pay;

import java.io.Serializable;
import java.util.Date;

public class CashFileDto implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer channelId;
	
    private String orgFileName;

    private Date createDate;

    private String createPerson;

    private String newFileName;

    private String newFlieUrl;

    private Integer fileSize;

    private String status;

    public Integer getChannelId() {
		return channelId;
	}

	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrgFileName() {
        return orgFileName;
    }

    public void setOrgFileName(String orgFileName) {
        this.orgFileName = orgFileName == null ? null : orgFileName.trim();
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

    public String getNewFileName() {
        return newFileName;
    }

    public void setNewFileName(String newFileName) {
        this.newFileName = newFileName == null ? null : newFileName.trim();
    }

    public String getNewFlieUrl() {
        return newFlieUrl;
    }

    public void setNewFlieUrl(String newFlieUrl) {
        this.newFlieUrl = newFlieUrl == null ? null : newFlieUrl.trim();
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}