package com.infy.lbsprototype.model;

public class InputRequest {
	
	private String customerId;
	private String regionType;
	private String regionCode;
	private String poiCategory;
	private String movementType;
	private String time;
	private String sendNotification;
	private String lat;
	private String lon;
	private String city;
	private String town;
	private String country;
	
	
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
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getMovementType() {
		return movementType;
	}
	public void setMovementType(String movementType) {
		this.movementType = movementType;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getSendNotification() {
		return sendNotification;
	}
	public void setSendNotification(String sendNotification) {
		this.sendNotification = sendNotification;
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
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPoiCategory() {
		return poiCategory;
	}
	public void setPoiCategory(String poiCategory) {
		this.poiCategory = poiCategory;
	}
	 
	
}
