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
	private Calendar crimeDate;
	@Persistent
	private String location;
	@Persistent
	private Date dateAdded;
	@Persistent
	private User judge; 
	@Persistent
	private Boolean approved;
	
	/**
	 * Constructors 
	 */
	public Crime() {
		this.dateAdded = new Date();
	}
	
	public Crime(String type, Calendar crimeDate, String location){
		this();
		this.crimeType = type;
		this.crimeDate = crimeDate;
		this.location = location;
//		this.judge = user;
		this.approved = false;
	}
	
	/**
	 * Getters for other classes
	 */
	
	public Long getKey(){
		return this.id;
	}
	
	public String getType(){
		return this.crimeType;
	}
	
	public Calendar getCrimeDate(){
		return this.crimeDate;
	}
	
	public int getCrimeMonth(){
		return this.crimeDate.get(Calendar.MONTH)+1; // +1 to change to correct month
	}
	
	public int getCrimeDay(){
		return this.crimeDate.get(Calendar.DAY_OF_MONTH);
	}
	public Date getDateAdded() {
		return this.dateAdded;
	}
	
	public User getJudge(){
		return this.judge;
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
	
}