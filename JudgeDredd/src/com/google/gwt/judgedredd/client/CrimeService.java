package com.google.gwt.judgedredd.client;


import com.google.gwt.judgedredd.client.NotLoggedInException;
import com.google.gwt.judgedredd.client.ClientCrime;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("crimeservice")
public interface CrimeService extends RemoteService {
	public int[] addReport(String url) throws NotLoggedInException;
	public ClientCrime[] getCrimesByMonth(int[] targetMonths, int targetYear) throws NotLoggedInException;
	public void removeCrimes(int[] targetMonths, int targetYear);
	public ClientCrime[] getAllCrimes() throws NotLoggedInException;
	public void approveCrimes(int month) throws NotLoggedInException;
	public ClientCrime[] getCertainCrimeType(String crimeType) throws NotLoggedInException;
	// TODO: getMonthlyCrimesCount()
	
}
