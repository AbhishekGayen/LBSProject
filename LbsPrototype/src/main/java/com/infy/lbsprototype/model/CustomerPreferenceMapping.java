package com.infy.lbsprototype.model;

import java.io.Serializable;

public class CustomerPreferenceMapping implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3282202493332633110L;
	private String customerId;
	private String preference;
	
	public CustomerPreferenceMapping(){
		
	}
	
	public CustomerPreferenceMapping(String customerId, String preference){
		this.customerId = customerId;
		this.preference = preference;
	}
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getPreference() {
		return preference;
	}
	public void setPreference(String preference) {
		this.preference = preference;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
		result = prime * result + ((preference == null) ? 0 : preference.hashCode());
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
		CustomerPreferenceMapping other = (CustomerPreferenceMapping) obj;
		if (customerId == null) {
			if (other.customerId != null)
				return false;
		} else if (!customerId.equals(other.customerId))
			return false;
		if (preference == null) {
			if (other.preference != null)
				return false;
		} else if (!preference.equals(other.preference))
			return false;
		return true;
	}

	
	
}
