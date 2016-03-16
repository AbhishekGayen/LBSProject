package com.infy.lbsprototype.dao.Impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.infy.lbsprototype.dao.LbsDao;
import com.infy.lbsprototype.model.Customer;
import com.infy.lbsprototype.model.LbsConfiguration;
import com.infy.lbsprototype.model.Message;
import com.infy.lbsprototype.model.MessageLog;
import com.infy.lbsprototype.model.Notification;

public class LbsDaoImpl extends HibernateDaoSupport implements LbsDao {
	
	static Logger log = Logger.getLogger(LbsDaoImpl.class.getName());

	@Override
	public List<Message> getMessage(String MoveType, String geoLocationType, String geoLocation, String customerId) {

 		@SuppressWarnings("unchecked")
		List<Message> messageList = getHibernateTemplate().find("from Message where moveType='"+MoveType+"' and geolocation='"+geoLocation+"' and geoLocationType='"+geoLocationType+"' and activeYN='Y' and (categoryId='OFFER' or categoryId='COUPON' or (categoryId='NEWS' and contentTypeId='LOC') or (categoryId='NEWS' and contentTypeId='PER' and topicId in (select preference from CustomerPreferenceMapping where customerId='"+customerId+"')))");
 		return messageList;
	}

	
	@Override
	public Customer getCustomer(String customerId) {
		@SuppressWarnings("unchecked")
		List<Customer> customerList = getHibernateTemplate().find("from Customer where customerId='"+customerId+"'");
		
		Customer customerFromDB= null;
		if (customerList != null && customerList.size()>0)
		{
			customerFromDB = customerList.get(0);
			
		}
		return customerFromDB;
	}


	@Override
	public Boolean checkWeather(Customer customer) {
		log.info("--------------------WEATHER CHECK IN DAO------------------------------------");
		log.info("---------------------BEFORE QUERY--------------------------------------");
		@SuppressWarnings("unchecked")
		List<Customer> customerList = getHibernateTemplate().find("from CustomerPreferenceMapping where preference ='weather' and customerId='"+customer.getCustomerId()+"'");
		log.info("---------------------------------------------AFTER QUERY--------------------------------------------------------------");
		if(!customerList.isEmpty()){
			log.info("-------------------WEATHER REQUEST FOUND-----------------------------------");
			return true;
		}
		else{
			log.info("------------------------------CUSTOMER LIST NOT FOUND-------------------------------");
			
			return false;
		}
		
	}


	@Override
	public Long saveNotification(Notification notification) {
		log.info("----------------BEFORE SAVING NOTIF------------------");
		Long id=null;
		try{
			
			id = (Long)getHibernateTemplate().save(notification);
			log.info("----------------AFTER SAVING NOTIF WITH ID"+id+" ------------------");
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
		
		return id;
	}


	@Override
	public void createMessageLog(MessageLog messageLog) {
		log.info("=============before creating MESSAGELOG===================");
		getHibernateTemplate().save(messageLog);
		log.info("=============after creating MESSAGELOG===================");
		
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<LbsConfiguration> getCongiguration() {
		log.info("=============before checking config===================");
		List<LbsConfiguration> configuration=null;
		try{
			configuration = getHibernateTemplate().find("from LbsConfiguration where confkey='POICategoryEnabled' and confValue='true'");

		}catch(Exception e){
			log.info("====="+e.getMessage()+e.getClass());
		}
		log.info("=============after checking config===================");
		return configuration;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Message> getMessageByPoi(String poiCategory) {
		log.info("=============before checking getMessageByPoi===================");
		List<Message> msgList = null;
		try{
			msgList = getHibernateTemplate().find("from Message where msgid in (select msgIdRef from PoiCategoryMsgIdMapping where poiCategory='"+poiCategory+"')");

		}catch(Exception e){
			log.info("----------"+e.getMessage()+e.getClass());
		}
		log.info("=============after checking getMessageByPoi===================");

		return msgList;
	}


	@SuppressWarnings("unchecked")
	@Override
	public String modifyFlagDAO(String confKey) {
		log.info("===BEFORE MODIFYING======");
		String status=null;
		try{
			List<LbsConfiguration> mappingList = getHibernateTemplate().find("from LbsConfiguration where confkey='"+confKey+"'");
			LbsConfiguration conf = mappingList.get(0);
			
			if(conf.getConfValue().equalsIgnoreCase("false")){
				conf.setConfValue("true");
			}
			else if(conf.getConfValue().equalsIgnoreCase("true")){
				conf.setConfValue("false");

			}
			status = conf.getConfValue();
			getHibernateTemplate().update(conf);
			log.info("===AFTER MODIFYING======");
		}catch(Exception e){
			log.info("----------"+e.getMessage()+e.getClass());
		}
		
		return confKey+" Flag set to "+status;
	}


	@SuppressWarnings("unchecked")
	@Override
	public Boolean getUniqueMessageFlag() {
		Boolean flag = false;
		log.info("=============before checking getUniqueMessageFlag===================");

		try{
			List<LbsConfiguration> mappingList = getHibernateTemplate().find("from LbsConfiguration where confkey='UniqueMessageEnabled'");
			LbsConfiguration conf = mappingList.get(0);
			if(conf.getConfValue().equalsIgnoreCase("true")){
				flag = true;
			}
		}catch(Exception e){
			log.info("----------"+e.getMessage()+e.getClass());
		}
		log.info("=============after checking getUniqueMessageFlag===================");
		return flag;
	}


	@SuppressWarnings("unchecked")
	@Override
	public Boolean checkUniqueMessage(Long messageId, String customerId) {
		Boolean flag = false;
		List<MessageLog> messageLogList=null;
		log.info("=============before checking checkUniqueMessage===================");

		try{
			messageLogList = getHibernateTemplate().find("from MessageLog where msgid="+messageId+" and customerId='"+customerId+"'");
			if(messageLogList!=null && !messageLogList.isEmpty()){
				flag=true;
			}
		}catch(Exception e){
			log.info("----------"+e.getMessage()+e.getClass());
		}
		log.info("=============after checking checkUniqueMessage===================");
		log.info("=============value of checkUniqueMessage flag  "+flag+"==========For customer========="+customerId+" for message Id: "+messageId);

		return flag;
	}

}
