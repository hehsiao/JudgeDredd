package com.google.gwt.judgedredd.server;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;



public class CrimeParser {


	// Array list of cleanup data in individual crimes
	private ArrayList<Crime> crimeReport = new ArrayList<Crime>();
	private PersistenceManager pm;
	private User judge;
	
	/**
	 * Constructor
	 */
	public CrimeParser (PersistenceManager pm, User user){
		System.out.println("CrimeParser Constructor created");
		this.pm = pm;
		this.judge = user;
		storeCrimeList(retrieveCrimeDataset ("http://www.henrychsiao.com/crime_2011.csv"));
		
	}

	private ArrayList<ArrayList<String>> retrieveCrimeDataset(String link) {
		// Array list of raw data from dataset
		ArrayList<ArrayList<String>> crimeList = new ArrayList<ArrayList<String>>(); 
		System.out.println("Reading File");
		Scanner scanner = null;
		try {
			URL url = new URL(link);
			scanner = new Scanner(url.openStream());
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				ArrayList<String> list = new ArrayList<String>(); 
				Collections.addAll(list, line.split(",")); 
				if(!list.get(0).equals("TYPE")){
					crimeList.add(list);
					System.out.println(list.toString());
				}
			}

		}
		catch(IOException ex) {
			ex.printStackTrace();

		} finally {
			if(scanner!= null)
				scanner.close();
		}
		
		return crimeList;
		
	}


	/**
	 * storeCrimeList cleans dataset and make data persistent
	 * @param crimeList
	 */
	private void storeCrimeList(ArrayList<ArrayList<String>> crimeList) {
		System.out.println("Attempt to store data to datastore");
		for (ArrayList<String> aCrime: crimeList) {
			
			String crimeType = aCrime.get(0);
			int year = Integer.parseInt(aCrime.get(1));
		    int month = Integer.parseInt(aCrime.get(2));
		    
		    // default day field to 1, as no actual date is provided from dataset
		    Calendar crimeDate = new GregorianCalendar(year, month-1, 1); // month-1 is to offset calendar
		    // replaces XX with 00 in the location field
		    String location = aCrime.get(3).replace("XX", "00");
		    crimeReport.add(new Crime(crimeType, crimeDate, location, getUser()));
	
		}
		
		try{
			System.out.println("make persistent call");
			pm.makePersistentAll(crimeReport);
		}
		finally {
			pm.close();
		}
	}
	
	private User getUser() {
		UserService userService = UserServiceFactory.getUserService();
		return userService.getCurrentUser();
	}

		
}
