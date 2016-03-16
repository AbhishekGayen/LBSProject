package com.infy.lbsprototype.model;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long msgid;
	private String moveType;
	private String categoryId;
	private String topicId;
	private String contentTypeId;
	private String geoLocationType;
	private String geolocation;
	private String title;
	private String message;
	private String activeYN;
	private Date activeFrom;
	private Date activeTo;
	
	public Message() {
	}

	 

 	public String getMoveType() {
		return moveType;
	}



	public void setMoveType(String moveType) {
		this.moveType = moveType;
	}



	public String getGeoLocationType() {
		return geoLocationType;
	}



	public void setGeoLocationType(String geoLocationType) {
		this.geoLocationType = geoLocationType;
	}



	public String getGeolocation() {
		return geolocation;
	}



	public void setGeolocation(String geolocation) {
		this.geolocation = geolocation;
	}



	public Date getActiveFrom() {
		return activeFrom;
	}



	public void setActiveFrom(Date activeFrom) {
		this.activeFrom = activeFrom;
	}



	public Date getActiveTo() {
		return activeTo;
	}



	public void setActiveTo(Date activeTo) {
		this.activeTo = activeTo;
	}



	public String getCategoryId() {
		return categoryId;
	}



	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}



	public String getTopicId() {
		return topicId;
	}



	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}



	public String getContentTypeId() {
		return contentTypeId;
	}



	public void setContentTypeId(String contentTypeId) {
		this.contentTypeId = contentTypeId;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getMessage() {
		return message;
	}



	public void setMessage(String message) {
		this.message = message;
	}



	public String getActiveYN() {
		return activeYN;
	}



	public void setActiveYN(String activeYN) {
		this.activeYN = activeYN;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public Long getMsgid() {
		return msgid;
	}



	public void setMsgid(Long msgid) {
		this.msgid = msgid;
	}



	@Override
	public String toString() {
		return "Message [categoryId=" + categoryId + ", topicId=" + topicId
				+ ", contentTypeId=" + contentTypeId + ", geolocationType=" + geoLocationType
				+ ", title=" + title+ ", message=" + message
				+ ", activeYN=" + activeYN+ ", activeFrom=" + activeFrom  + ", activeTo=" + activeTo
				+ "]";
	}

	
}
