package com.infy.lbsprototype.model;

public class GetMultipleJsonOutput {

	private String publishingMode = "NotificationBar";
	private String briefContent;
	private String channelInfo;
	private byte[] detailContent;
	
	public String getPublishingMode() {
		return publishingMode;
	}
	public void setPublishingMode(String publishingMode) {
		this.publishingMode = publishingMode;
	}
	public String getBriefContent() {
		return briefContent;
	}
	public void setBriefContent(String briefContent) {
		this.briefContent = briefContent;
	}
	public String getChannelInfo() {
		return channelInfo;
	}
	public void setChannelInfo(String channelInfo) {
		this.channelInfo = channelInfo;
	}
	public byte[] getDetailContent() {
		return detailContent;
	}
	public void setDetailContent(byte[] detailContent) {
		this.detailContent = detailContent;
	}
	 
	 
	
	
}
