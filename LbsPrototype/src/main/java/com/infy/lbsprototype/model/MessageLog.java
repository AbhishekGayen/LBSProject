package com.infy.lbsprototype.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class MessageLog implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5884538808105031922L;
	private Long refReqId;
	private String customerId;
	private Long msgid;
	private String title;
	private Timestamp notificationTime;
	
	
	public Long getRefReqId() {
		return refReqId;
	}
	public void setRefReqId(Long refReqId) {
		this.refReqId = refReqId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public Long getMsgid() {
		return msgid;
	}
	public void setMsgid(Long msgid) {
		this.msgid = msgid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Timestamp getNotificationTime() {
		return notificationTime;
	}
	public void setNotificationTime(Timestamp notificationTime) {
		this.notificationTime = notificationTime;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
		result = prime * result + ((msgid == null) ? 0 : msgid.hashCode());
		result = prime * result + ((refReqId == null) ? 0 : refReqId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MessageLog other = (MessageLog) obj;
		if (customerId == null) {
			if (other.customerId != null)
				return false;
		} else if (!customerId.equals(other.customerId))
			return false;
		if (msgid == null) {
			if (other.msgid != null)
				return false;
		} else if (!msgid.equals(other.msgid))
			return false;
		if (refReqId == null) {
			if (other.refReqId != null)
				return false;
		} else if (!refReqId.equals(other.refReqId))
			return false;
		return true;
	}

	
}
