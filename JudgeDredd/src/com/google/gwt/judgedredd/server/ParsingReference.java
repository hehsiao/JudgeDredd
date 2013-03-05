import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import javax.jdo.PersistenceManager;

import com.google.gwt.judgedredd.server.Crime;

import au.com.bytecode.opencsv.CSVReader;

public class ParsingReference {
	
	public static void main( String[] args ){
		
		String[] row = null;
		// TODO: need to figure out how to retrieve link on server.
		// TODO: setup http connections or sth
		// TODO: current files are from ftp sourc, might need to move it to HTTP server on ugrad servers
		/**
		 *  REFERENCE FROM GitLab lab
		 *  InputStream in = this.getClass().getClassLoader().getResourceAsStream("userlist.txt");
		 *  might be useful
		 */
		
		String csvFilename = "crime_2011.csv";
		
		try {
			// csvReader parse the files, 1 at the end indicates to skip the first line (headings)
			
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
			    
			    // TODO: setup persistant manager using factory
			    /**
			     * REFERENCE StockServiceImpl.java.
			     * something along the lines of 
			     * PersistenceManager pm = getPersistenceManager();
			     * try {
			     * 		pm.makePersistent(new Crime(row[0], crimeDate.getYear(), crimeDate.getMonth(), location));
			     * 		} finally {
			     * 		pm.close();
			     * 	}
			     */
			    // inside a few different methods
			    
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