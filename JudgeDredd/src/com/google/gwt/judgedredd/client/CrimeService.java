package com.google.gwt.judgedredd.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("crimeservice")
public interface CrimeService extends RemoteService {
	public void addReport(String test) throws NotLoggedInException;
}
