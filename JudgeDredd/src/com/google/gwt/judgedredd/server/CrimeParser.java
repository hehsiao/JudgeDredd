package com.google.gwt.judgedredd.server;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.google.maps.gwt.client.LatLng;


public class CrimeParser {

	final private int MAX_NUMBER_OF_CRIMES = 200; // max number of crimes to store in datastore
	private int[] monthlyCrimes;

	/**
	 * Constructor
	 * Initialize monthlyCrimes counter
	 */
	public CrimeParser (){
		monthlyCrimes = new int[12];
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
		double[] latlng = new double[2];
		ArrayList<Crime> crimeReport = new ArrayList<Crime>();

		Collections.shuffle(crimeList);
		for (ArrayList<String> aCrime: crimeList) {

			String crimeType = aCrime.get(0);
			int year = Integer.parseInt(aCrime.get(1));
			int month = Integer.parseInt(aCrime.get(2));

			String cleanAddress = parseAddress(aCrime.get(3));

			double latitude = 0.00;
			double longitude = 0.00;
			try {
				Geocoder geocode = new Geocoder(cleanAddress);
				latlng = geocode.getLatlng();
				latitude = latlng[0];
				longitude = latlng[1];
				cleanAddress = geocode.getFormattedAddress();
			} catch (XPathExpressionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(cleanAddress == null || !cleanAddress.contains(", Vancouver")){
				// Skip entry if geocoder results is not returned.
				// DEBUG
				//		    	if(cleanAddress != null) 
				//		    		System.out.println("SKIPPED: " + crimes_counter + " " + crimeType+ " " +year+ " " +month+ " " +cleanAddress+ " " +latitude+ " " +longitude);

			} else {

				// DEBUG
				System.out.println("ADDED: " + crimes_counter + " " + crimeType+ " " +year+ " " +month+ " " +cleanAddress+ " " +latitude+ " " +longitude);
				//logger.log(Level.FINE, "ADDED: " + crimes_counter + " " + crimeType+ " " +year+ " " +month+ " " +cleanAddress+ " " +latitude+ " " +longitude);
				
				// retain only the address and building portion of address
				cleanAddress = cleanAddress.substring(0, cleanAddress.indexOf(", Vancouver"));
				crimeReport.add(new Crime(crimeType, year, month, cleanAddress, latitude, longitude));
				monthlyCrimes[month-1]++;
				crimes_counter++;
			}

			// max entries from dataset
			if(crimes_counter == MAX_NUMBER_OF_CRIMES){
				break;
			}

		}


		// DEBUG CODE 
		//		for(int i = 0; i < monthlyCrimes.length; i++){
		//			int month = i+1;
		//			System.out.println("Month " + month + " has " + monthlyCrimes[i] + " crimes.");
		//		}

		return crimeReport;
	}

	/**
	 * helper method to clean Address and prepare for Geocoding.
	 * replaces XX to 00 and "/" to "and" and add Vancouver, BC to the end.
	 * 
	 * @param location
	 * @return a cleaned address
	 */
	public String parseAddress(String location) {

		location = location.replace("XX", "00");
		location = location.replace("/", "and");

		location += ", Vancouver, BC";

		// DEBUG
		//	    System.out.println(location);

		return location;
	}

	/**
	 * Getting Crime count by month
	 * 
	 * @return Array of crimes counter by Month, count is stored in month-1
	 *		ie: monthlyCrimes[0] = January, monthlyCrimes[3] = April...
	 */
	public int[] getCrimesCountByMonth(){
		return monthlyCrimes;
	}

}