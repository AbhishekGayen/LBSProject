package com.infy.lbsprototype.dao;

import java.util.List;

import com.infy.lbsprototype.model.Customer;
import com.infy.lbsprototype.model.LbsConfiguration;
import com.infy.lbsprototype.model.Message;
import com.infy.lbsprototype.model.MessageLog;
import com.infy.lbsprototype.model.Notification;

public interface LbsDao {
	List<Message> getMessage(String MoveType, String geoLocationType, String geoLocation, String customerId);
	
	Customer getCustomer(String customerId);
	
	Boolean checkWeather(Customer customer);
	
	Long saveNotification(Notification notification);
	
	void createMessageLog(MessageLog messageLog);

	List<LbsConfiguration> getCongiguration();

	List<Message> getMessageByPoi(String poiCategory);
	
	String modifyFlagDAO(String confKey);
	
	Boolean getUniqueMessageFlag();
	
	Boolean checkUniqueMessage(Long messageId, String customerId);
	
}
