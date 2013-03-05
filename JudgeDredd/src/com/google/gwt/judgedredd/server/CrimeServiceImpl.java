package com.google.gwt.judgedredd.server;

import java.util.Date;

import javax.jdo.JDOHelper;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.judgedredd.client.CrimeService;
import com.google.gwt.judgedredd.client.NotLoggedInException;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class CrimeServiceImpl extends RemoteServiceServlet implements CrimeService {

	private static final PersistenceManagerFactory PMF =  
			JDOHelper.getPersistenceManagerFactory("transactions-optional");

	public void addReport() throws NotLoggedInException{
		checkLoggedIn();
		PersistenceManager pm = getPersistenceManager();
		CrimeParser report = new CrimeParser(pm, getUser());
	
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
