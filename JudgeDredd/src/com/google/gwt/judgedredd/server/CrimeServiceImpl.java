package com.google.gwt.judgedredd.server;

import java.util.ArrayList;
import java.util.Arrays;

import javax.jdo.JDOHelper;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.judgedredd.client.ClientCrime;
import com.google.gwt.judgedredd.client.CrimeService;
import com.google.gwt.judgedredd.client.NotLoggedInException;
import com.google.gwt.judgedredd.server.Crime;
import com.google.gwt.judgedredd.server.CrimeServiceImpl;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class CrimeServiceImpl extends RemoteServiceServlet implements CrimeService {

	private static final Logger LOG = Logger.getLogger(CrimeServiceImpl.class.getName());
	private static final PersistenceManagerFactory PMF =  
			JDOHelper.getPersistenceManagerFactory("transactions-optional");

	public int[] addReport(String url) throws NotLoggedInException{

		//		checkLoggedIn();
		
		CrimeParser parser = new CrimeParser();
		ArrayList<Crime> crimeReport = parser.retrieveCrimeDataset(url);
		
		// can be used as a seperate call
		approveReport(crimeReport);
		
		return parser.getCrimesCountByMonth();
	}
	
	public void approveReport (ArrayList<Crime> crimeReport){

		PersistenceManager pm = getPersistenceManager();
		// make persistent
		try{
			System.out.println("make persistent call");
			pm.makePersistentAll(crimeReport);
		}
		finally {
			pm.close();
		}
	}


	public ClientCrime[] getCrimesByMonth(int[] targetMonths) throws NotLoggedInException {

		//	    checkLoggedIn();
		PersistenceManager pm = getPersistenceManager();
		List<ClientCrime> crimes = new ArrayList<ClientCrime>();

		try {
			for(int month: targetMonths){
				System.out.println("Set new query for " + month);
				Query q = pm.newQuery(Crime.class, "month == targetMonth && approved == f");
				q.declareParameters("Integer targetMonth, Boolean f");
				q.setOrdering("crimeType asc");
				List<Crime> report = (List<Crime>) q.execute(month, false);
				System.out.println("executed");
				for (Crime c : report) {
					ClientCrime clientC = new ClientCrime();
					clientC.setLocation(c.getLocation());
					clientC.setCrimeYear(c.getCrimeYear());
					clientC.setCrimeMonth(c.getCrimeMonth());
					clientC.setCrimeType(c.getType());
					clientC.setApproved(c.isApproved());
					crimes.add(clientC);
//					System.out.println(clientC.getType() + ", " + clientC.getCrimeYear() + ", " + clientC.getCrimeMonth() + ", " + clientC.getLocation() + " Size: " + crimes.size());
				}
//				System.out.println("done printing list");
			}
		} finally {
			pm.close();
		}
		System.out.println("Full list of crimes returning");
//		System.out.println("Test 1: " + crimes.get(0).getLocation());
//		System.out.println("Test 2: " + crimes.get(crimes.size()-2).getLocation());
		for (ClientCrime c: crimes){
			System.out.println(c.getType() + ", " + c.getCrimeYear() + ", " + c.getCrimeMonth() + ", " + c.getLocation());
		}


		return (ClientCrime[]) crimes.toArray(new ClientCrime[0]);
	}

	private void checkLoggedIn() throws NotLoggedInException {
		System.out.println("checking users");
		if (getUser() == null) {
			System.out.println("not logged in");
			throw new NotLoggedInException("Not logged in.");
		}
	}

	private User getUser() {
		UserService userService = UserServiceFactory.getUserService();
		return userService.getCurrentUser();
	}

	/**
	 * @return  associated persistence manager
	 */
	private PersistenceManager getPersistenceManager() {
		return PMF.getPersistenceManager();
	}
}
