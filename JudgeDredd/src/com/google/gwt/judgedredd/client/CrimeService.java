package com.google.gwt.judgedredd.client;


import java.util.List;
import com.google.gwt.judgedredd.server.Crime;
import com.google.gwt.judgedredd.client.NotLoggedInException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("crimeservice")
public interface CrimeService extends RemoteService {
	public void addReport(String test) throws NotLoggedInException;
	public Crime[] getMonthlyCrime(int specMonth) throws NotLoggedInException;
}
