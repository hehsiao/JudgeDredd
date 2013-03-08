package com.google.gwt.judgedredd.server;

import java.util.ArrayList;
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
import com.google.gwt.judgedredd.server.Crime;
import com.google.gwt.judgedredd.client.NotLoggedInException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class CrimeServiceImpl extends RemoteServiceServlet implements CrimeService {

	private static final PersistenceManagerFactory PMF =  
			JDOHelper.getPersistenceManagerFactory("transactions-optional");
	

	private PersistenceManager pm = getPersistenceManager();

	public void addReport(String test) throws NotLoggedInException{
		System.out.println(test);
		CrimeParser report = new CrimeParser(pm);
	}
	
	public Crime[] getMonthlyCrime(int specMonth) throws NotLoggedInException {
	    checkLoggedIn();
	    System.out.println("zzz");
	    List<Crime> result = new ArrayList<Crime>();
	    try {
	    	System.out.println("zzz2");
	      /*Query q = pm.newQuery(Crime.class, "approved == false && crimeDate.getMonth() == sMonth");
	      q.declareParameters("int sMonth");
	      //q.setOrdering("createDate");
	      List<Crime> report = (List<Crime>) q.execute(2);
	      for (Crime c : report) {
	        result.add(c);
	      }*/
	    } finally {
	      pm.close();
	    }
	    return (Crime[]) result.toArray(new Crime[0]);
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
