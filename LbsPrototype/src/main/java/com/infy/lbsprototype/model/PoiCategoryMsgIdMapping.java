package com.infy.lbsprototype.model;

import java.io.Serializable;

public class PoiCategoryMsgIdMapping implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3595885942862564498L;
	private Long msgIdRef;
	private String poiCategory;
	public Long getMsgIdRef() {
		return msgIdRef;
	}
	public void setMsgIdRef(Long msgIdRef) {
		this.msgIdRef = msgIdRef;
	}
	public String getPoiCategory() {
		return poiCategory;
	}
	public void setPoiCategory(String poiCategory) {
		this.poiCategory = poiCategory;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((msgIdRef == null) ? 0 : msgIdRef.hashCode());
		result = prime * result + ((poiCategory == null) ? 0 : poiCategory.hashCode());
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
		PoiCategoryMsgIdMapping other = (PoiCategoryMsgIdMapping) obj;
		if (msgIdRef == null) {
			if (other.msgIdRef != null)
				return false;
		} else if (!msgIdRef.equals(other.msgIdRef))
			return false;
		if (poiCategory == null) {
			if (other.poiCategory != null)
				return false;
		} else if (!poiCategory.equals(other.poiCategory))
			return false;
		return true;
	}
	
	
}
