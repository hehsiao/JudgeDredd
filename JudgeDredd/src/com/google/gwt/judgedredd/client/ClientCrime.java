package com.google.gwt.judgedredd.client;

import java.io.Serializable;
import java.util.Date;


public class ClientCrime implements Serializable {
	private Long id;
	private String crimeType;
	private Date crimeDate;
	private String location;
	private Date dateAdded;

	private Boolean approved;
	
	
	/**
	 * Getters for other classes
	 */
	
	public Long getKey(){
		return this.id;
	}
	
	public String getLocation(){
		return this.location;
	}
	
	public String getType(){
		return this.crimeType;
	}
	
	public Date getCrimeDate(){
		return this.crimeDate;
	}

	public int getCrimeMonth(){
		return crimeDate.getMonth();
	}
	
	public int getCrimeDay(){
		return crimeDate.getDate();
	}
	
	public Date getDateAdded() {
		return this.dateAdded;
	}
	
//	public User getJudge(){
//		return this.judge;
//	}
	
	public boolean isApproved() {
		return this.approved;
	}
	
	/**
	 * Setters
	 */
	
	public void setApproval(){
		this.approved = true;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCrimeType(String crimeType) {
		this.crimeType = crimeType;
	}

	public void setCrimeDate(Date crimeDate) {
		this.crimeDate = crimeDate;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}
}
