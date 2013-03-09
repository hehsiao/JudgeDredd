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

	public void addReport() throws NotLoggedInException{

//		checkLoggedIn();
		PersistenceManager pm = getPersistenceManager();
		CrimeParser report = new CrimeParser(pm);

	}


	public ClientCrime[] getMonthlyCrimes(int targetMonth) throws NotLoggedInException {

//	    checkLoggedIn();
	    PersistenceManager pm = getPersistenceManager();
	    List<ClientCrime> crimes = new ArrayList<ClientCrime>();

	    try {
	    	System.out.println("zzz");
	    	Query q = pm.newQuery(Crime.class, "month == targetMonth && approved == f");
	    	q.declareParameters("Integer targetMonth, Boolean f");
	    	q.setOrdering("crimeType asc");
	    	System.out.println("set new Query");
	    	List<Crime> report = (List<Crime>) q.execute(targetMonth, false);
	    	System.out.println("executed");
	    	for (Crime c : report) {
	    		ClientCrime clientC = new ClientCrime();
	    		clientC.setLocation(c.getLocation());
	    		clientC.setCrimeYear(c.getCrimeYear());
	    		clientC.setCrimeMonth(c.getCrimeMonth());
	    		clientC.setCrimeType(c.getType());
	    		//	    		 	clientC.setDateAdded(c.getDateAdded());
	    		clientC.setApproved(c.isApproved());
	    		crimes.add(clientC);
	    		System.out.println(c.getType() + ", " + c.getCrimeYear() + ", " + c.getCrimeMonth() + ", " + c.getLocation());
	    	}
	    	System.out.println("done printing list");
	    } finally {
	        pm.close();
	    }

		return (ClientCrime[]) crimes.toArray(new ClientCrime[0]);
	}

//	public int getMonthlyCrimes(){
//		
//	}
	
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
