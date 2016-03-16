package com.infy.lbsprototype.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "item")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"contentType","geoLocation","topic","title","description","link","pubDate"})
public class XMLItem {
	
	private String contentType;
	private String geoLocation;
	private String topic;
	private String title;
	private String description;
	private String link;
	private Date pubDate;

	public String getContentType() {
		return contentType;
	}
	
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getGeoLocation() {
		return geoLocation;
	}
	
	public void setGeoLocation(String geoLocation) {
		this.geoLocation = geoLocation;
	}
	public String getTopic() {
		return topic;
	}
	
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	public Date getPubDate() {
		return pubDate;
	}
	
	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	
}
