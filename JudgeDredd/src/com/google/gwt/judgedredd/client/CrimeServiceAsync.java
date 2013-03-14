package com.google.gwt.judgedredd.client;

import com.google.gwt.judgedredd.client.ClientCrime;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CrimeServiceAsync {
	public void addReport(AsyncCallback<Void> async);
	public void getMonthlyCrimes(int targetMonth, AsyncCallback<ClientCrime[]> async);
	public void approveCrimes(int targetMonth, AsyncCallback<Void> async);
//	public void getMonthlyCrimesCount(AsyncCallback<Integer> async);
}
