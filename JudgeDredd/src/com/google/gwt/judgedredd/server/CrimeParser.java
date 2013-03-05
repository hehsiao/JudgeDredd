package com.google.gwt.judgedredd.server;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.users.User;



public class CrimeParser {

	private ArrayList<ArrayList<String>> rawCrimeList; 
	private ArrayList<Crime> crimeList;
	private PersistenceManager pm;
	private User judge;
	
	/**
	 * Constructor
	 */
	public CrimeParser (PersistenceManager pm, User user){
		this.pm = pm;
		this.judge = user;
		retrieveCrimeDataset ("http://www.henrychsiao.com/crime_2011.csv");
		
	}

	private void retrieveCrimeDataset(String link) {
		Scanner scanner = null;
		try {
			URL url = new URL(link);
			scanner = new Scanner(url.openStream());
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				ArrayList<String> myList = new ArrayList<String>(); 
				Collections.addAll(myList, line.split(",")); 
				rawCrimeList.add( myList);
			}

		}
		catch(IOException ex) {
			ex.printStackTrace();

		} finally {
			if(scanner!= null)
				scanner.close();
		}
		
		
		for (ArrayList<String> aCrime: rawCrimeList) {
			
			String crimeType = aCrime.get(0);
			int year = Integer.parseInt(aCrime.get(1));
		    int month = Integer.parseInt(aCrime.get(2));
		    
		    // default day field to 1, as no actual date is provided from dataset
		    Date crimeDate = new Date(year, month, 1);
		    // replaces XX with 00 in the location field
		    String location = aCrime.get(3).replace("XX", "00");
		    crimeList.add(new Crime(crimeType, crimeDate, location, judge));
	
		}
		
		try{
			pm.makePersistentAll(crimeList);
		}
		finally {
			pm.close();
		}
		
	}
		
}
