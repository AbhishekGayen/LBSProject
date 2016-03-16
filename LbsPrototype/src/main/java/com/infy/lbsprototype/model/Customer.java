package com.infy.lbsprototype.model;

public class Customer {
	
	private String customerId;
	private String channelInfo;
	private String personalPref;
	
	public Customer(){
		
	}
	
	public Customer(String customerId){
		this.customerId = customerId;
		
	}
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	 
	public String getChannelInfo() {
		return channelInfo;
	}

	public void setChannelInfo(String channelInfo) {
		this.channelInfo = channelInfo;
	}

	public String getPersonalPref() {
		return personalPref;
	}

	public void setPersonalPref(String personalPref) {
		this.personalPref = personalPref;
	}
	
}
