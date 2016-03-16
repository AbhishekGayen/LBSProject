package com.infy.lbsprototype.model;

import java.util.List;

public class GetMyNewsStoriesJsonData {
	
	private String status;
	private String copyright;
	private Integer numOfResults;
	private List<ResultsJSON> resultsJSONList;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCopyright() {
		return copyright;
	}
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}
	public Integer getNumOfResults() {
		return numOfResults;
	}
	public void setNumOfResults(Integer numOfResults) {
		this.numOfResults = numOfResults;
	}
	public List<ResultsJSON> getResultsJSONList() {
		return resultsJSONList;
	}
	public void setResultsJSONList(List<ResultsJSON> resultsJSONList) {
		this.resultsJSONList = resultsJSONList;
	}
}