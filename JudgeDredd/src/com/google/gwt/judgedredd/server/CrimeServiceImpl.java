package com.google.gwt.judgedredd.server;

import java.util.Date;

import javax.jdo.JDOHelper;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import java.util.List;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.judgedredd.client.CrimeService;
import com.google.gwt.judgedredd.client.NotLoggedInException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class CrimeServiceImpl extends RemoteServiceServlet implements CrimeService {

	private static final PersistenceManagerFactory PMF =  
			JDOHelper.getPersistenceManagerFactory("transactions-optional");
	

	private PersistenceManager pm = getPersistenceManager();

	public void addReport(String test) throws NotLoggedInException{
		System.out.println(test);
		CrimeParser report = new CrimeParser(pm, getUser());
	
	}
	
	public List<Crime> getMonthlyCrime(String specMonth) {
		Query toBeApproved = pm.newQuery(Crime.class);
		toBeApproved.setFilter("approved == false && month == sMonth");
		toBeApproved.declareParameters("String sMonth");
		
		List<Crime> results = (List<Crime>) toBeApproved.execute(specMonth);
		
		return results;
		// call with adminService using String specMonth parameter
		
		// iterate through results, pull fields for crimes out, cast into string, put each into flextable using setText("string")
		// from HTMLTabl, the parent class for flexTable
		
	}



	
	
	
	private void checkLoggedIn() throws NotLoggedInException {
	    if (getUser() == null) {
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
