package com.infy.lbsprototype.service.Impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.infy.lbsprototype.dao.LbsDao;
import com.infy.lbsprototype.model.AddMultipleJsonInput;
import com.infy.lbsprototype.model.Customer;
import com.infy.lbsprototype.model.CustomerWithin;
import com.infy.lbsprototype.model.CustomerWithinWrapper;
import com.infy.lbsprototype.model.GetMultipleJsonOutput;
import com.infy.lbsprototype.model.InputRequest;
import com.infy.lbsprototype.model.LbsConfiguration;
import com.infy.lbsprototype.model.Message;
import com.infy.lbsprototype.model.MessageLog;
import com.infy.lbsprototype.model.Notification;
import com.infy.lbsprototype.model.PublishToResource;
import com.infy.lbsprototype.model.Weather;
import com.infy.lbsprototype.model.WeatherInfo;
import com.infy.lbsprototype.model.XMLContent;
import com.infy.lbsprototype.model.XMLItem;
import com.infy.lbsprototype.service.LbsService;

public class LbsServiceImpl implements LbsService {

	static Logger log = Logger.getLogger(LbsServiceImpl.class.getName());

	private LbsDao lbsDao;

	public void setLbsDao(LbsDao lbsDao) {
		this.lbsDao = lbsDao;
	}

	/**
	 * 
	 */
	@Override
	public List<GetMultipleJsonOutput> getMultipleJsonMessage(InputRequest customerReq, boolean isPushMultiple) {

		List<GetMultipleJsonOutput> jsonMessageList = new ArrayList<GetMultipleJsonOutput>();
		List<XMLItem> itemsNews = new ArrayList<XMLItem>();
		List<XMLItem> itemsInfo = new ArrayList<XMLItem>();

		XMLContent contentNews = new XMLContent();
		XMLContent contentInfo = new XMLContent();

		log.debug("----------------------" + customerReq.getCustomerId());
		Customer customer = lbsDao.getCustomer(customerReq.getCustomerId());
		List<Message> messageList = null;
		if (customer != null) {

			log.debug("----------------------" + customer.getCustomerId());
			if(customerReq.getPoiCategory()==null){
			messageList = lbsDao.getMessage(customerReq.getMovementType(), customerReq.getRegionType(),
					customerReq.getRegionCode(), customer.getCustomerId());
			}
			if(customerReq.getPoiCategory()!=null){
				List<LbsConfiguration> configuration = lbsDao.getCongiguration();
				if(configuration!=null){
					messageList = lbsDao.getMessageByPoi(customerReq.getPoiCategory());
				}
			}
			
			Notification notification = new Notification();
			notification.setCustomerId(customerReq.getCustomerId());
			notification.setRegionType(customerReq.getRegionType());
			notification.setRegionCode(customerReq.getRegionCode());
			notification.setMovementType(customerReq.getMovementType());
			notification.setInputTime(customerReq.getTime());
			notification.setSendNotification(customerReq.getSendNotification());
			notification.setLat(customerReq.getLat());
			notification.setLon(customerReq.getLon());
			notification.setCountry(customerReq.getCountry());
			notification.setNotificationTime(new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()));

			if (customerReq.getCity() != null && customerReq.getTown() != null) {
				notification.setCity(customerReq.getCity());
				notification.setTown(customerReq.getTown());
			} else {
				notification.setCity("N.A.");
				notification.setTown("N.A.");
			}
			// Adding the input notification to the DB
			Long id = lbsDao.saveNotification(notification);

			for (int messageIndex = 0; messageIndex < messageList.size(); messageIndex++) {
				Message msg = messageList.get(messageIndex);

				MessageLog messageLog = new MessageLog();
				messageLog.setRefReqId(id);
				messageLog.setCustomerId(customerReq.getCustomerId());
				messageLog.setMsgid(msg.getMsgid());
				messageLog.setTitle(msg.getTitle());
				messageLog.setNotificationTime(new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()));
				log.info("=-=-=-=-=-==-=-MESSAGE LIST SIZE:=-=-=-=-=-=-=-= "+messageList.size());
				log.info("=-=-=-=-=-==-=-ITERATION:=-=-=-=-=-=-=-= "+messageIndex);
				if(lbsDao.getUniqueMessageFlag().equals(true) && lbsDao.checkUniqueMessage(msg.getMsgid(), customerReq.getCustomerId()).equals(true)){
					log.info("checking for messageId "+msg.getMsgid());
					continue;
				}
				log.info("passed for messageId "+msg.getMsgid());
				
				lbsDao.createMessageLog(messageLog);

				if ("OFFER".equals(msg.getCategoryId()) || "COUPON".equals(msg.getCategoryId())) {

					GetMultipleJsonOutput jsonMessage = new GetMultipleJsonOutput();
					jsonMessage.setChannelInfo(customer.getChannelInfo());
					jsonMessage.setBriefContent(msg.getTitle());

					XMLContent content = new XMLContent();
					List<XMLItem> items = new ArrayList<XMLItem>();
					content.setCategory(msg.getCategoryId());
					XMLItem item = new XMLItem();
					item.setContentType(msg.getContentTypeId());
					item.setGeoLocation(msg.getGeolocation());
					item.setTopic(msg.getTopicId());
					item.setTitle(msg.getTitle());
					item.setDescription(msg.getMessage());
					item.setPubDate(new Date());

					items.add(item);

					content.setItem(items);
					String xmlMessage = jaxbObjectToXML(content);
					log.info("XML Content=" + xmlMessage);
					jsonMessage.setDetailContent(xmlMessage.getBytes());

					jsonMessageList.add(jsonMessage);
				} else if ("NEWS".equals(msg.getCategoryId())) {

					contentNews.setCategory(msg.getCategoryId());

					XMLItem item = new XMLItem();
					item.setContentType(msg.getContentTypeId());
					item.setGeoLocation(msg.getGeolocation());
					item.setTopic(msg.getTopicId());
					item.setTitle(msg.getTitle());
					item.setDescription(msg.getMessage());
					item.setPubDate(new Date());

					itemsNews.add(item);

				}

			}

			if (!itemsNews.isEmpty()) {
				contentNews.setItem(itemsNews);

				String xmlMessage = jaxbObjectToXML(contentNews);
				log.info("XML NEWS Content=" + xmlMessage);
				XMLItem item = itemsNews.get(0);

				GetMultipleJsonOutput jsonMessageNEWS = new GetMultipleJsonOutput();
				jsonMessageNEWS.setBriefContent(item.getTitle());
				jsonMessageNEWS.setChannelInfo(customer.getChannelInfo());
				jsonMessageNEWS.setDetailContent(xmlMessage.getBytes());

				jsonMessageList.add(jsonMessageNEWS);
			}

			if (checkWeather(customer).equals(true)) {
				log.info("------check weather true---------------------------------");
				log.info("--------------CUSTOMER CITY-----------" + customerReq.getCity());
				log.info("--------------CUSTOMER TOWN-----------" + customerReq.getTown());
				List<String> descriptionAndCity = getWeather(customerReq.getLat(), customerReq.getLon(), customerReq.getTown(),
						customerReq.getCity());

				contentInfo.setCategory("Info");

				XMLItem item = new XMLItem();
				item.setContentType("loc");

				if (customerReq.getCity() != null && customerReq.getTown() != null) {
					item.setGeoLocation(customerReq.getCity());
					item.setTitle("Weather: " + customerReq.getTown());
				}

				else {
					item.setGeoLocation(descriptionAndCity.get(1));
					item.setTitle("Weather "+descriptionAndCity.get(1));
				}
				item.setTopic("Weather");
				item.setDescription(descriptionAndCity.get(0));
				item.setPubDate(new Date());

				itemsInfo.add(item);

				contentInfo.setItem(itemsInfo);

				String xmlMessage = jaxbObjectToXML(contentInfo);
				log.info("XML INFO Content=" + xmlMessage);

				GetMultipleJsonOutput jsonMessageInfo = new GetMultipleJsonOutput();
				jsonMessageInfo.setBriefContent(itemsInfo.get(0).getTitle());
				jsonMessageInfo.setChannelInfo(customer.getChannelInfo());
				jsonMessageInfo.setDetailContent(xmlMessage.getBytes());

				jsonMessageList.add(jsonMessageInfo);
			}

		}

		
		return jsonMessageList;
	}

	@Override
	public List<AddMultipleJsonInput> preparePushMultipleInput(InputRequest customerReq, boolean isPushMultiple) {

		List<AddMultipleJsonInput> jsonMessageList = new ArrayList<AddMultipleJsonInput>();
		List<XMLItem> itemsNews = new ArrayList<XMLItem>();
		List<XMLItem> itemsInfo = new ArrayList<XMLItem>();

		XMLContent contentNews = new XMLContent();
		XMLContent contentInfo = new XMLContent();

		log.debug("----------------------" + customerReq.getCustomerId());
		Customer customer = lbsDao.getCustomer(customerReq.getCustomerId());
		List<Message> messageList = null;
		if (customer != null) {

			log.debug("----------------------" + customer.getCustomerId());

			messageList = lbsDao.getMessage(customerReq.getMovementType(), customerReq.getRegionType(),
					customerReq.getRegionCode(), customer.getCustomerId());
			
			Notification notification = new Notification();
			notification.setCustomerId(customerReq.getCustomerId());
			notification.setRegionType(customerReq.getRegionType());
			notification.setRegionCode(customerReq.getRegionCode());
			notification.setMovementType(customerReq.getMovementType());
			notification.setInputTime(customerReq.getTime());
			notification.setSendNotification(customerReq.getSendNotification());
			notification.setLat(customerReq.getLat());
			notification.setLon(customerReq.getLon());
			notification.setCountry(customerReq.getCountry());
			notification.setNotificationTime(new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()));

			if (customerReq.getCity() != null && customerReq.getTown() != null) {
				notification.setCity(customerReq.getCity());
				notification.setTown(customerReq.getTown());
			} else {
				notification.setCity("N.A.");
				notification.setTown("N.A.");
			}
			// Adding the input notification to the DB
			Long id = lbsDao.saveNotification(notification);

			for (int messageIndex = 0; messageIndex < messageList.size(); messageIndex++) {
				Message msg = messageList.get(messageIndex);

				MessageLog messageLog = new MessageLog();
				messageLog.setRefReqId(id);
				messageLog.setCustomerId(customerReq.getCustomerId());
				messageLog.setMsgid(msg.getMsgid());
				messageLog.setTitle(msg.getTitle());
				messageLog.setNotificationTime(new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()));
				
				log.info("=-=-=-=-=-==-=-MESSAGE LIST SIZE:=-=-=-=-=-=-=-= "+messageList.size());
				log.info("=-=-=-=-=-==-=-ITERATION:=-=-=-=-=-=-=-= "+messageIndex);
				if(lbsDao.getUniqueMessageFlag().equals(true) && lbsDao.checkUniqueMessage(msg.getMsgid(), customerReq.getCustomerId()).equals(true)){
					log.info("checking for messageId "+msg.getMsgid());
					continue;
				}
				log.info("passed for messageId "+msg.getMsgid());
				
				lbsDao.createMessageLog(messageLog);

				if ("OFFER".equals(msg.getCategoryId()) || "COUPON".equals(msg.getCategoryId())) {

					AddMultipleJsonInput jsonMessage = new AddMultipleJsonInput();
					jsonMessage.setChannelInfo(customer.getChannelInfo());
					jsonMessage.setBriefContent(msg.getTitle());

					XMLContent content = new XMLContent();
					List<XMLItem> items = new ArrayList<XMLItem>();
					content.setCategory(msg.getCategoryId());
					XMLItem item = new XMLItem();
					item.setContentType(msg.getContentTypeId());
					item.setGeoLocation(msg.getGeolocation());
					item.setTopic(msg.getTopicId());
					item.setTitle(msg.getTitle());
					item.setDescription(msg.getMessage());
					item.setPubDate(new Date());

					items.add(item);

					content.setItem(items);
					String xmlMessage = jaxbObjectToXML(content);
					log.info("XML Content=" + xmlMessage);
					jsonMessage.setDetailContent(xmlMessage.getBytes());

					PublishToResource pubToResource = new PublishToResource();
					pubToResource.setCustomerId(msg.getCategoryId());
					pubToResource.setResourceId("0");
					pubToResource.setResourceName("");

					jsonMessage.setPublishToResource(pubToResource);

					jsonMessageList.add(jsonMessage);
				} else if ("NEWS".equals(msg.getCategoryId())) {

					contentNews.setCategory(msg.getCategoryId());

					XMLItem item = new XMLItem();
					item.setContentType(msg.getContentTypeId());
					item.setGeoLocation(msg.getGeolocation());
					item.setTopic(msg.getTopicId());
					item.setTitle(msg.getTitle());
					item.setDescription(msg.getMessage());
					item.setPubDate(new Date());

					itemsNews.add(item);

				}

			}

			if (!itemsNews.isEmpty()) {
				contentNews.setItem(itemsNews);

				String xmlMessage = jaxbObjectToXML(contentNews);
				log.info("XML NEWS Content=" + xmlMessage);
				XMLItem item = itemsNews.get(0);

				AddMultipleJsonInput jsonMessageNEWS = new AddMultipleJsonInput();
				jsonMessageNEWS.setBriefContent(item.getTitle());
				jsonMessageNEWS.setChannelInfo(customer.getChannelInfo());
				jsonMessageNEWS.setDetailContent(xmlMessage.getBytes());

				PublishToResource pubToResource = new PublishToResource();
				pubToResource.setCustomerId(customer.getCustomerId());
				pubToResource.setResourceId("0");
				pubToResource.setResourceName("");

				jsonMessageNEWS.setPublishToResource(pubToResource);
				jsonMessageList.add(jsonMessageNEWS);
			}

			if (checkWeather(customer).equals(true) && customerReq.getMovementType().equalsIgnoreCase("movein")) {
				log.info("------check weather true---------------------------------");
				log.info("--------------CUSTOMER CITY-----------" + customerReq.getCity());
				log.info("--------------CUSTOMER TOWN-----------" + customerReq.getTown());
				List<String> descriptionAndCity = getWeather(customerReq.getLat(), customerReq.getLon(), customerReq.getTown(),
						customerReq.getCity());

				contentInfo.setCategory("Info");

				XMLItem item = new XMLItem();
				item.setContentType("loc");

				if (customerReq.getCity() != null && customerReq.getTown() != null) {
					item.setGeoLocation(customerReq.getCity());
					item.setTitle("Weather: " + customerReq.getTown());
				}

				else {
					item.setGeoLocation(descriptionAndCity.get(1));
					item.setTitle("Weather "+descriptionAndCity.get(1));
				}
				item.setTopic("Weather");
				item.setDescription(descriptionAndCity.get(0));
				item.setPubDate(new Date());

				itemsInfo.add(item);

				contentInfo.setItem(itemsInfo);

				String xmlMessage = jaxbObjectToXML(contentInfo);
				log.info("XML INFO Content=" + xmlMessage);

				AddMultipleJsonInput jsonMessageInfo = new AddMultipleJsonInput();
				jsonMessageInfo.setBriefContent(itemsInfo.get(0).getTitle());
				jsonMessageInfo.setChannelInfo(customer.getChannelInfo());
				jsonMessageInfo.setDetailContent(xmlMessage.getBytes());

				PublishToResource pubToResource = new PublishToResource();
				pubToResource.setCustomerId(customer.getCustomerId());
				pubToResource.setResourceId("0");
				pubToResource.setResourceName("");

				jsonMessageInfo.setPublishToResource(pubToResource);
				jsonMessageList.add(jsonMessageInfo);
			}
		}

		

		return jsonMessageList;
	}

	/**
	 * 
	 * @param content
	 * @return
	 */
	private static String jaxbObjectToXML(XMLContent content) {

		java.io.StringWriter sw = new StringWriter();
		try {
			JAXBContext context = JAXBContext.newInstance(XMLContent.class);
			Marshaller m = context.createMarshaller();

			// for pretty-print XML in JAXB
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.marshal(content, sw);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return sw.toString();
	}

	public Boolean checkWeather(Customer customer) {
		log.info("----------------- SERVICE CHECK WEATHER----------------------------------");
		Boolean check = lbsDao.checkWeather(customer);

		return check;
	}

	public List<String> getWeather(String lat, String lon, String town, String city) {

		HttpURLConnection c = null;
		//String description = null;
		List<String> descriptionAndCity = null;
		String url = "http://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon
				+ "&appid=44db6a862fba0b067b1930da0d769e98";
		log.info("--------------------GETTING WEATHER INFO-----------------------------------------");
		try {

			URL u = new URL(url);
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.68.248.34", 80));
			c = (HttpURLConnection) u.openConnection(proxy);
			c.setRequestMethod("GET");
			c.setRequestProperty("Content-Type", "application/json;charset=ISO-8859-1");
			c.setUseCaches(false);
			c.setAllowUserInteraction(false);
			int timeout = 1000000;
			c.setConnectTimeout(timeout);
			c.setReadTimeout(timeout);
			c.connect();
			int status = c.getResponseCode();

			switch (status) {
			case 201:
				log.error("error 201");

				break;
			case 200:
				BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line + "\n");
				}
				br.close();
				log.info("---------------------INSIDE SWITCH---------------------------------");
				log.info("--------JSONSTRING---------" + sb.toString() + "-------------------------------------");
				descriptionAndCity = toJava(sb.toString(), town, city);
				break;

			case 401:
				BufferedReader b = new BufferedReader(new InputStreamReader(c.getInputStream()));
				StringBuilder s = new StringBuilder();
				String lin;
				while ((lin = b.readLine()) != null) {
					s.append(lin + "\n");
				}
				b.close();
				log.error("------------------error 401---------------------------------");
				break;

			}
			
			log.info("===STATUS CODE FOR WEATHER==="+status);
		} catch (Exception ex) {

			log.error("Exception in class" + ex.getClass() + "--" + ex.getMessage());

		}
		
		
		return descriptionAndCity;
	}

	public List<String> toJava(String jsonString, String town, String city) {

		String description = null;
		String cityFromApi = null;
		List<String>descriptionAndCity = null;
		try {
			log.info("-------------------------------JSON STRING FROM RESPONSE-----------------------------------");
			log.info(
					"-------------------" + jsonString + "-----------------------------------------------------------");
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			WeatherInfo weatherInfo = objectMapper.readValue(jsonString, WeatherInfo.class);
			String desc = null, detailDesc = null;
			log.info("---------------" + weatherInfo.getWeather() + "--------------------------------------");
			for (Weather weather : weatherInfo.getWeather()) {
				desc = weather.getMain();
				detailDesc = weather.getDescription();
			}

			if (town != null && city != null) {

				description = "Weather conditions for your current checked in location " + town + ", " + city + ". "
						+ desc + ": " + detailDesc + ", Pressure: " + weatherInfo.getMain().getPressure()
						+ ", Humidity: " + weatherInfo.getMain().getHumidity() + ", MinTemp: "
						+ weatherInfo.getMain().getTemp_min() + ", Max Temp: " + weatherInfo.getMain().getTemp_max()
						+ ", Wind: " + weatherInfo.getWind().getSpeed();
			}

			else {

				description = "Weather conditions for your current checked in location "+weatherInfo.getName()+". " + desc + ": " + detailDesc
						+ ", Pressure: " + weatherInfo.getMain().getPressure() + ", Humidity: "
						+ weatherInfo.getMain().getHumidity() + ", MinTemp: " + weatherInfo.getMain().getTemp_min()
						+ ", Max Temp: " + weatherInfo.getMain().getTemp_max() + ", Wind: "
						+ weatherInfo.getWind().getSpeed();

			}
			cityFromApi = weatherInfo.getName();
			descriptionAndCity = new ArrayList<String>();
			descriptionAndCity.add(description);
			descriptionAndCity.add(cityFromApi);

		} catch (Exception e) {
			log.error(e.getMessage() + " " + e.getClass(), e);
		}
		return descriptionAndCity;

	}

	@Override
	public String getMovedInCustomers(String regionType, String regionCode) {

		HttpURLConnection c = null;
		String url = null;
		String status = null;
		if (regionType.equalsIgnoreCase("geofence")) {
			url = "http://124.124.249.154:8081/LBS/BusinessServices/V4Indoor/resource/list/geoFence/" + regionCode;

		} else if (regionType.equalsIgnoreCase("poi")) {
			url = "http://124.124.249.154:8081/LBS/BusinessServices/V4Indoor/resource/list/poi/" + regionCode;
		}

		log.info("--------------------GETTING CUSTOMER INFO-----------------------------------------");
		try {

			URL u = new URL(url);
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.68.248.34", 80));
			c = (HttpURLConnection) u.openConnection(proxy);
			c.setRequestMethod("GET");
			c.setRequestProperty("Authorization", "Basic bGJzYWRtaW46aW5meUAxMjM=");
			c.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
			c.setRequestProperty("X-CSRF-Header", "test");
			/*
			 * c.setUseCaches(false); c.setAllowUserInteraction(false);
			 */
			int timeout = 100000000;
			c.setConnectTimeout(timeout);
			c.setReadTimeout(timeout);
			c.connect();
			int stat = c.getResponseCode();

			switch (stat) {
			case 201:
				log.error("error 201");

				break;
			case 200:
				BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line + "\n");
				}
				br.close();
				log.info("---------------------INSIDE SWITCH---------------------------------");
				log.info("--------JSONSTRING---------" + sb.toString() + "-------------------------------------");
				status = customersWithinToJava(sb.toString(), regionType, regionCode);
				break;

			case 401:
				BufferedReader b = new BufferedReader(new InputStreamReader(c.getInputStream()));
				StringBuilder s = new StringBuilder();
				String lin;
				while ((lin = b.readLine()) != null) {
					s.append(lin + "\n");
				}
				b.close();
				log.error("------------------error 401---------------------------------");
				break;

			}

			log.info("===========RESPONSE CODE OF PUSH MULTIPLE CUSTOMERS===============" + stat);
		} catch (Exception ex) {

			log.error("Exception in class" + ex.getClass() + "--" + ex.getMessage());

		}
		return status;
	}

	@Override
	public String customersWithinToJava(String jsonString, String regionType, String regionCode)
			throws JsonParseException, JsonMappingException, IOException {

		log.info("-------------------------------JSON STRING FROM RESPONSE-----------------------------------");
		log.info("-------------------" + jsonString + "-----------------------------------------------------------");
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		List<CustomerWithin> CustomerWithinListwrapper = objectMapper.readValue(jsonString,
				new TypeReference<List<CustomerWithin>>() {
				});
		CustomerWithinWrapper wrapper = new CustomerWithinWrapper();
		wrapper.setCustomerWithinList(CustomerWithinListwrapper);
		log.info(CustomerWithinListwrapper.size());

		String status = null;
		InputRequest newNotificationReq = null;
		try {
				//CustomerWithinWrapper wrapper =
				//objectMapper.readValue(jsonString, CustomerWithinWrapper.class);

			if (wrapper.getCustomerWithinList() != null) {

				for (CustomerWithin customer : wrapper.getCustomerWithinList()) {
					newNotificationReq = new InputRequest();
					newNotificationReq.setCustomerId(customer.getCustomerId());
					newNotificationReq.setMovementType("within");
					newNotificationReq.setRegionType(regionType);
					newNotificationReq.setRegionCode(regionCode);
					newNotificationReq.setTime(new SimpleDateFormat("hh:mm:ss").format(new Date()));
					newNotificationReq.setLat(customer.getLat());
					newNotificationReq.setLon(customer.getLon());
					newNotificationReq.setSendNotification("true");
					newNotificationReq.setTown("");
					newNotificationReq.setCity("");
					newNotificationReq.setCountry("");

					List<AddMultipleJsonInput> newJsonMessage = preparePushMultipleInput(newNotificationReq, true);

					if (!newJsonMessage.isEmpty()) {

						Gson gson = new Gson();
						Type type = new TypeToken<List<AddMultipleJsonInput>>() {
						}.getType();
						String jsonStr = gson.toJson(newJsonMessage, type);
						log.info("=========JSON FOR POST=================" + jsonStr);
						URL url = new URL(
								"http://124.124.249.154:8081/LBS/BusinessServices/V4Indoor/publishing/addMultiple");
						Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.68.248.34", 80));
						HttpURLConnection c = (HttpURLConnection) url.openConnection(proxy);
						c.setRequestMethod("POST");
						c.setRequestProperty("Authorization", "Basic bGJzYWRtaW46aW5meUAxMjM=");
						c.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
						c.setRequestProperty("X-CSRF-Header", "test");
						c.setRequestProperty("Content-Length", String.valueOf(jsonStr.length()));
						c.setDoOutput(true);
						c.getOutputStream().write(jsonStr.getBytes());

						int timeout = 1000000;
						c.setConnectTimeout(timeout);
						c.setReadTimeout(timeout);
						c.connect();
						int stat = c.getResponseCode();
						log.info("==========STATUS CODE FROM SERVER AFTER POST====================" + stat);
						switch (stat) {
						case 201:
							log.error("-----------------------error 201------------------------------");
							status = "ERROR 201";

							break;
						case 200:
							BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
							StringBuilder sb = new StringBuilder();
							String line;
							while ((line = br.readLine()) != null) {
								sb.append(line + "\n");
							}
							br.close();
							log.info("---------------------INSIDE SWITCH---------------------------------");
							log.info("--------JSONSTRING---------" + sb.toString()
									+ "-------------------------------------");
							status = sb.toString();
							break;

						case 401:
							BufferedReader b = new BufferedReader(new InputStreamReader(c.getInputStream()));
							StringBuilder s = new StringBuilder();
							String lin;
							while ((lin = b.readLine()) != null) {
								s.append(lin + "\n");
							}
							b.close();
							log.error("------------------error 401---------------------------------");
							status = "ERROR 401";
							break;
						}
					}

				}
			}
		} catch (Exception ex) {
			log.error(ex.getMessage() + " " + ex.getClass(), ex);
		}

		return status;

	}

	@Override
	public String modifyFlag(String confKey) {
		String msg = lbsDao.modifyFlagDAO(confKey);
		return msg;
	}

}
