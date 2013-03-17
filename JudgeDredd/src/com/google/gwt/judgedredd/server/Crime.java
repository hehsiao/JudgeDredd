/**
 * Crime.java
 * Data Class for Crime
 * UBC CPSC 310 - Judge Dredd
 * Team Red Hot Techie Pepper
 * 
 * Create Date: February 28, 2013
 * Last Modified: February 28, 2013
 */

package com.google.gwt.judgedredd.server;

import java.util.Calendar;
import java.util.Date;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import com.google.appengine.api.users.User;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Crime {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	@Persistent
	private String crimeType;
	@Persistent
	private Integer year;
	@Persistent
	private Integer month;
	@Persistent
	private String location;
	@Persistent
	private double latitude;
	@Persistent
	private double longitude;
	@Persistent
	private Date dateAdded;
	@Persistent
	private Boolean approved;
	
	/**
	 * Constructors 
	 */
	public Crime() {
		this.dateAdded = new Date();
	}
	
	public Crime(String type, int year, int month, String location, double latitude, double longitude){
		this();
		this.crimeType = type;
		this.year = year;
		this.month = month;
		this.location = location;
		this.latitude = latitude;
		this.longitude = longitude;
		this.approved = false;
	}
	
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
	
	public double getLatitude() {
		return latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	/**
	 * Setters
	 */
	
	public void setApproval(){
		this.approved = true;
	}


	
}