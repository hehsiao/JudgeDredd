package com.google.gwt.judgedredd.server;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;


public class ParsingReference {
	
	public static void main( String[] args ){
		
		ArrayList<ArrayList<String>> crimeList = new ArrayList<ArrayList<String>>();
		
		Scanner scanner = null;
		try {
			URL url = new URL("http://www.henrychsiao.com/crime_2010.csv");
			scanner = new Scanner(url.openStream());
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				ArrayList<String> myList = new ArrayList<String>(); 
				Collections.addAll(myList, line.split(",")); 
				//				System.out.println(myList.get(0) + " "+ myList.get(1) + " " + myList.get(2) + " " + myList.get(3));
				if(!myList.get(0).equals("TYPE")){
					crimeList.add(myList);
				}
//				System.out.println(myList);
			}
			Collections.shuffle(crimeList);
		}
		catch(IOException ex) {
			ex.printStackTrace();

		} finally {
			if(scanner!= null)
				scanner.close();
		}

		int count = 0;
		for (ArrayList<String> aCrime: crimeList) { 
			String crimeType = aCrime.get(0);
			int year = Integer.parseInt(aCrime.get(1));
		    int month = Integer.parseInt(aCrime.get(2));
		    
		    String location = aCrime.get(3).replace("XX", "00");
		    System.out.println(crimeType + ", " + year + ", " + month + ", " + location);
		    count++;
		    if(count == 500){
		    	break;
		    }
		    
		}
	}

	
}