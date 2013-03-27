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

import java.util.Date;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import com.google.appengine.api.users.User;

/**
 * @author goodweather
 *
 */
/**
 * @author goodweather
 *
 */
/**
 * @author goodweather
 *
 */
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
	 * Returns the id of Crime
	 * @return Key
	 */
	public Long getKey(){
		return this.id;
	}
	
	/**
	 * returns the address of crime
	 * @return location
	 */
	public String getLocation(){
		return this.location;
	}
	
	/**
	 * returns type of crime
	 * @return crimeType
	 */
	public String getType(){
		return this.crimeType;
	}
	
	/**
	 * returns the year of crime
	 * @return year
	 */
	public int getCrimeYear(){
		return this.year;
	}

	/**
	 * returns the month of crime
	 * @return month
	 */
	public int getCrimeMonth(){
		return this.month;
	}

	/**
	 * returns the date added to datastore
	 * @return dateAdded
	 */
	public Date getDateAdded() {
		return this.dateAdded;
	}
	
	/**
	 * returns whether or not the crime has been approved by administrator.
	 * @return approval status
	 */
	public boolean isApproved() {
		return this.approved;
	}
	
	/**
	 * returns the latitude of where the crime occurred
	 * @return latitude
	 */
	public double getLatitude() {
		return latitude;
	}
	
	/**
	 * returns the longitude of where the crime occurred
	 * @return longitude
	 */	
	public double getLongitude() {
		return longitude;
	}
	
	/**
	 * Approves the crime in data store
	 */
	public void setApproval(){
		this.approved = true;
	}

}