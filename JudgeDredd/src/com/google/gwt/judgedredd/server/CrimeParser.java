package com.google.gwt.judgedredd.server;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
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
		
}
