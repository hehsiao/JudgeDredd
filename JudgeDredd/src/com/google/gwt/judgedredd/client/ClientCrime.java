package com.google.gwt.judgedredd.client;

import java.io.Serializable;
import java.util.Date;


public class ClientCrime implements Serializable {
	private Long id;
	private String crimeType;
	private int year;
	private int month;
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
	
	public int getCrimeYear(){
		return this.year;
	}
	
	public int getCrimeMonth(){
		return this.month;
	}
	
	public Date getDateAdded() {
		return this.dateAdded;
	}
	
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

	public void setCrimeYear(int year){
		this.year = year;
	}
	
	public void setCrimeMonth(int month){
		this.month = month;
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
