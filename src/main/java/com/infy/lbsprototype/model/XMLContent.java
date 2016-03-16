package com.infy.lbsprototype.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "content")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {   "category",       "listItem"})
public class XMLContent {
		
		private String category;
		@XmlElement(name = "item")
		private List<XMLItem> listItem;
		
		public String getCategory() {
			return category;
		}
		
		
		public void setCategory(String category) {
			this.category = category;
		}
		
		public List<XMLItem> getItem() {
			return listItem;
		}
		
		
		public void setItem(List<XMLItem> items) {
			this.listItem = items;
		}	
		
}
