package com.google.gwt.judgedredd.client;

import com.google.gwt.judgedredd.client.ClientCrime;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CrimeServiceAsync {
	public void addReport(String url, AsyncCallback<int[]> async);
	public void getCrimesByMonth(int[] targetMonths, AsyncCallback<ClientCrime[]> async); 
//	public void getMonthlyCrimesCount(AsyncCallback<Integer> async);
}
