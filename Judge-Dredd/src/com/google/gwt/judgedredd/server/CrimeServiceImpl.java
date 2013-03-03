package com.google.gwt.judgedredd.server;

/**
 * JDO Persistence Manager Imports
 */
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;


import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.judgedredd.client.NotLoggedInException;
import com.google.gwt.judgedredd.client.CrimeService;
import com.google.gwt.judgedredd.server.Crime;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * CSV Parsing Imports
 */
import au.com.bytecode.opencsv.CSVReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;


public class CrimeServiceImpl extends RemoteServiceServlet {

	  
	public void addCrimes(){
		
		String[] row = null;
		String csvFilename = "com.google.gwt.judgedredd.server.crime_2011.csv";
		try {
			CSVReader csvReader = new CSVReader(new FileReader(csvFilename), ',', '\'', 1);
			List content = csvReader.readAll();
			
			for (Object object : content) {
			    row = (String[]) object;
			    
			    int year = Integer.parseInt(row[1]);
			    int month = Integer.parseInt(row[2]);
//			    System.out.println(row[1] + " " + row[2]);
			    Date crimeDate = new Date(year, month, 1);
			    String location = row[3].replace("XX", "00");
//			    location = location + ", Vancouver, BC, Canada";
			    System.out.println(row[0] + "\t" + crimeDate.getYear() + "\t" + crimeDate.getMonth() + "\t" + location);
			    
			    
			}
			
			
			csvReader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
}