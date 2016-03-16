package com.infy.lbsprototype.model;

import java.sql.Timestamp;

public class Notification {
	private Long requestId;
	private String customerId;
	private String regionType;
	private String regionCode;
	private String movementType;
	private String inputTime;
	private String sendNotification;
	private String lat;
	private String lon;
	private String city;
	private String town;
	private String country;
	private Timestamp notificationTime;
	
	public Long getRequestId() {
		return requestId;
	}
	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getRegionType() {
		return regionType;
	}
	public void setRegionType(String regionType) {
		this.regionType = regionType;
	}
	public String getRegionCode() {
		return regionCode;
	}
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public String getMovementType() {
		return movementType;
	}
	public void setMovementType(String movementType) {
		this.movementType = movementType;
	}
	public String getSendNotification() {
		return sendNotification;
	}
	public void setSendNotification(String sendNotification) {
		this.sendNotification = sendNotification;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLon() {
		return lon;
	}
	public void setLon(String lon) {
		this.lon = lon;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getInputTime() {
		return inputTime;
	}
	public void setInputTime(String inputTime) {
		this.inputTime = inputTime;
	}
	public Timestamp getNotificationTime() {
		return notificationTime;
	}
	public void setNotificationTime(Timestamp notificationTime) {
		this.notificationTime = notificationTime;
	}
	
}
