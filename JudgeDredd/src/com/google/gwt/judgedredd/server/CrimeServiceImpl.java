package com.google.gwt.judgedredd.server;

import java.util.ArrayList;

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


	public ClientCrime[] getMonthlyCrimes(int month) throws NotLoggedInException {

//	    checkLoggedIn();
	    PersistenceManager pm = getPersistenceManager();
	    List<ClientCrime> crimes = new ArrayList<ClientCrime>();
	    
	    try {
	    	System.out.println("zzz");
	    	Query q = pm.newQuery(Crime.class, "approved == f && crimeDate.getMonth() == m");
	    	 q.declareParameters("Boolean f, int m");
	    	 List<Crime> report = (List<Crime>) q.execute(false, month);
	    	 for (Crime c : report) {
	    		 	ClientCrime clientC = new ClientCrime();
	    		 	clientC.setId(c.getKey());
	    		 	clientC.setLocation(c.getLocation());
	    		 	clientC.setCrimeDate(c.getCrimeDate());
	    		 	clientC.setCrimeType(c.getType());
	    		 	clientC.setDateAdded(c.getDateAdded());
	    		 	clientC.setApproved(c.isApproved());
	    	        crimes.add(clientC);
	    	 }
	    } finally {
	        pm.close();
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
