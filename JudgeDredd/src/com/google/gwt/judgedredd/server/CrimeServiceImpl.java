package com.google.gwt.judgedredd.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.jdo.Extent;
import javax.jdo.JDOHelper;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.dev.js.rhino.ObjToIntMap.Iterator;
import com.google.gwt.judgedredd.client.ClientCrime;
import com.google.gwt.judgedredd.client.CrimeService;
import com.google.gwt.judgedredd.client.LoginInfo;
import com.google.gwt.judgedredd.client.NotLoggedInException;
import com.google.gwt.judgedredd.server.Crime;
import com.google.gwt.judgedredd.server.CrimeServiceImpl;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class CrimeServiceImpl extends RemoteServiceServlet implements CrimeService {

	private static final Logger LOG = Logger.getLogger(CrimeServiceImpl.class.getName());
	private static final PersistenceManagerFactory PMF =  
			JDOHelper.getPersistenceManagerFactory("transactions-optional");

	public int[] addReport(String url) throws NotLoggedInException{

		checkLoggedIn();

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

//		checkLoggedIn();
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
//		for (ClientCrime c: crimes){
//			System.out.println(c.getType() + ", " + c.getCrimeYear() + ", " + c.getCrimeMonth() + ", " + c.getLocation());
//		}

		return (ClientCrime[]) crimes.toArray(new ClientCrime[0]);
	}

	public void approveCrimes(int month) throws NotLoggedInException {

		checkLoggedIn();
		PersistenceManager pm = getPersistenceManager();
		List<Crime> crimes = new ArrayList<Crime>();
		Transaction tx = pm.currentTransaction();
		try {
			tx.begin();
			Extent e = pm.getExtent(Crime.class, true);
			java.util.Iterator iter=e.iterator();
			while (iter.hasNext()) {
				Crime c = (Crime) iter.next();
				if (c.isApproved() == false && c.getCrimeMonth() == month) {
					c.setApproval();
				}
				tx.commit();
			}
		}
		catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
	}	


	public ClientCrime[] getCertainCrimeType(String crimeType) throws NotLoggedInException {

//		checkLoggedIn();
		PersistenceManager pm = getPersistenceManager();
		List<ClientCrime> crimes = new ArrayList<ClientCrime>();

		try {
			Query q = pm.newQuery(Crime.class, "crimeType == ct");
			q.declareParameters("String ct");
			q.setOrdering("crimeType asc");
			System.out.println("set new Query");
			List<Crime> report = (List<Crime>) q.execute(crimeType);
			System.out.println("executed");
			for (Crime c : report) {
				ClientCrime clientC = new ClientCrime();
				clientC.setLocation(c.getLocation());
				clientC.setCrimeYear(c.getCrimeYear());
				clientC.setCrimeMonth(c.getCrimeMonth());
				clientC.setCrimeType(c.getType());
				//	clientC.setDateAdded(c.getDateAdded());
				clientC.setApproved(c.isApproved());
				crimes.add(clientC);
//				System.out.println(c.getType() + ", " + c.getCrimeYear() + ", " + c.getCrimeMonth() + ", " + c.getLocation());
			}
			System.out.println("done printing list");
		} finally {
			pm.close();
		}

		return (ClientCrime[]) crimes.toArray(new ClientCrime[0]);
	}


	private void checkLoggedIn() throws NotLoggedInException {
		System.out.println("Verify user");
		if (getUser() == null) {
			System.out.println("not logged in");
			throw new NotLoggedInException("Not logged in.");
		}
		if (!isJudge(getUser().getEmail())){
			System.out.println("Not Authorized");
			throw new NotLoggedInException("Not Authorized");
		}
	}

	private User getUser() {
		UserService userService = UserServiceFactory.getUserService();
		return userService.getCurrentUser();
	}

	/**
	 * @param loginInfo
	 * @return true - if loginInfo email matches one of the ones in the list
	 */
	private boolean isJudge(String email) {
		final Set<String> JUDGES = new HashSet<String>(Arrays.asList(
				new String[] {"me@henrychsiao.com","matthew.hsu@gmail.com","jywdominic@gmail.com","alvin.dong92@gmail.com"}
				));

		return JUDGES.contains(email);
	}

	/**
	 * @return  associated persistence manager
	 */
	private PersistenceManager getPersistenceManager() {
		return PMF.getPersistenceManager();
	}
}
