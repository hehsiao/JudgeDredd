package com.google.gwt.judgedredd.server;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;

public class CrimeParser {

	ArrayList<ArrayList<String>> crimeList; 
	
	/**
	 * Constructor
	 */
	public CrimeParser (){
		
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
				crimeList.add( myList);
			}

		}
		catch(IOException ex) {
			ex.printStackTrace();

		} finally {
			if(scanner!= null)
				scanner.close();
		}
	}
	
	private void moveCrimetoDataStore(){
		for (ArrayList<String> aCrime: crimeList) {
			
			String crimeType = aCrime.get(0);
			int year = Integer.parseInt(aCrime.get(1));
		    int month = Integer.parseInt(aCrime.get(2));
		    
		    Date crimeDate = new Date(year, month, 1);
		    String location = aCrime.get(3).replace("XX", "00");
		    addCrime(crimeType + "\t" + crimeDate.getYear() + "\t" + crimeDate.getMonth() + "\t" + location);
	
		}
	}
		
}
