package com.google.gwt.judgedredd.client;

import com.google.gwt.judgedredd.client.ClientCrime;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CrimeServiceAsync {
	public void addReport(String url, AsyncCallback<int[]> async);
	public void getCrimesByMonth(int[] targetMonths, int targetYear, AsyncCallback<ClientCrime[]> async); 
	public void getAllCrimes(AsyncCallback<ClientCrime[]> async); 
	public void removeCrimes(int month, int targetYear, AsyncCallback<Boolean> async);
	public void approveCrimes(int month, AsyncCallback<Void> async);
	public void getCertainCrimeType(String crimeType, AsyncCallback<ClientCrime[]> async);
//	public void getMonthlyCrimesCount(AsyncCallback<Integer> async);
}
