package com.google.gwt.judgedredd.server;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import com.google.appengine.api.users.User;


public class CrimeParser {

	private int MAX_NUMBER_OF_CRIMES = 500; // max number of crimes to store in datastore
	private int[] monthlyCrimes = new int[12];
	
	/**
	 * Constructor
	 */
	public CrimeParser (){
	}

	public ArrayList<Crime> retrieveCrimeDataset(String link) {
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

		// OPTIONAL
		ArrayList<Crime> report = selectCrimes(crimeList);
		
		return report;

	}

	/**
	 * selectCrimes shuffles dataset, parse data into correct format.
	 * takes the first "MAX_NUMBER_OF_CRIMES" entries in the shuffle dataset and store it in datastore
	 * 
	 * ** workaround for google app engine limit
	 * ** developer can change the max number from the global constant at the top
	 * 
	 * @param crimeList
	 */
	public ArrayList<Crime> selectCrimes(ArrayList<ArrayList<String>> crimeList){
		
		int crimes_counter = 0;
		
		ArrayList<Crime> crimeReport = new ArrayList<Crime>();
		
		Collections.shuffle(crimeList);
		
		for (ArrayList<String> aCrime: crimeList) {
			String crimeType = aCrime.get(0);
			int year = Integer.parseInt(aCrime.get(1));
		    int month = Integer.parseInt(aCrime.get(2));

		    String cleanAddress = parseAddress(aCrime.get(3));
   
		    crimeReport.add(new Crime(crimeType, year, month, cleanAddress));
		    monthlyCrimes[month-1]++;
		    crimes_counter++;
		    
		    // max entries from dataset
		    if(crimes_counter == MAX_NUMBER_OF_CRIMES){
		    	break;
		    }
		    
		}
		
		for(int i = 0; i < monthlyCrimes.length; i++){
			int month = i+1;
			System.out.println("Month " + month + " has " + monthlyCrimes[i] + " crimes.");
		}
		
		return crimeReport;
	}
	
	private String parseAddress(String location) {
		// TODO Auto-generated method stub
	    // clean address and prepare for geocoding
	    // replaces XX with 00 in the location field
	    location.replace("XX", "00");
	    // if location is an intersection, change "/" to "and"
	    if(location.contains("/"))
	    	location.replaceFirst("/", " and ");
	    // adds Vancouver, BC at the end of address
	    location += ", Vancouver, BC";
	    
	    return location;
	}

	public int[] getCrimesCountByMonth(){
		return monthlyCrimes;
	}
	
}