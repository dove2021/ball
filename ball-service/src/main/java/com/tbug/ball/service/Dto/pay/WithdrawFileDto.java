package com.tbug.ball.service.Dto.pay;

import java.io.Serializable;
import java.util.Date;

public class WithdrawFileDto implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private Integer customerId;
    
    private Integer channelId;

    private String orgFileName;

    private Date createrDate;

    private Date modifyDate;

    private String newFileName;

    private String filePath;

    private Integer fileSize;
    
    private String status;
    
    private byte[] imgContent;

    public byte[] getImgContent() {
		return imgContent;
	}

	public void setImgContent(byte[] imgContent) {
		this.imgContent = imgContent;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

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

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getOrgFileName() {
        return orgFileName;
    }

    public void setOrgFileName(String orgFileName) {
        this.orgFileName = orgFileName == null ? null : orgFileName.trim();
    }

    public Date getCreaterDate() {
        return createrDate;
    }

    public void setCreaterDate(Date createrDate) {
        this.createrDate = createrDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getNewFileName() {
        return newFileName;
    }

    public void setNewFileName(String newFileName) {
        this.newFileName = newFileName == null ? null : newFileName.trim();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public Integer getFileSize() {
        return fileSize;
    }

    public void setFileSize(Integer fileSize) {
        this.fileSize = fileSize;
    }
}