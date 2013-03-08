package com.google.gwt.judgedredd.client;

import com.google.gwt.judgedredd.server.Crime;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CrimeServiceAsync {
	public void addReport(AsyncCallback<Void> async);
//	public void getMonthlyCrimes(int month, AsyncCallback<Crime[]> async); 
}
