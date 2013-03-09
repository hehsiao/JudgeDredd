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


	// Array list of cleanup data in individual crimes
	private ArrayList<Crime> crimeReport = new ArrayList<Crime>();
	private PersistenceManager pm;

	/**
	 * Constructor
	 */
	public CrimeParser (PersistenceManager pm){
		System.out.println("CrimeParser Constructor created");
		this.pm = pm;
		storeCrimeList(retrieveCrimeDataset ("http://www.henrychsiao.com/crime_2010.csv"));

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
	 * storeCrimeList shuffle dataset, parse data into correct format.
	 * takes the first 500 entries in the shuffle dataset and store it in datastore
	 * 
	 * @param crimeList
	 * 
	 */
	private void storeCrimeList(ArrayList<ArrayList<String>> crimeList) {
		
		System.out.println("Attempt to store data to datastore");
		
		// Setup Data entry count
		int count = 0;
		Collections.shuffle(crimeList);
		
		for (ArrayList<String> aCrime: crimeList) {

			String crimeType = aCrime.get(0);
			int year = Integer.parseInt(aCrime.get(1));
		    int month = Integer.parseInt(aCrime.get(2));

		    // replaces XX with 00 in the location field
		    String location = aCrime.get(3).replace("XX", "00");
		    crimeReport.add(new Crime(crimeType, year, month, location));
		    count++;
		    
		    // max entries from dataset
		    if(count == 500){
		    	break;
		    }
		    
		}

		try{
			System.out.println("make persistent call");
			pm.makePersistentAll(crimeReport);
		}
		finally {
			pm.close();
		}
	}

}