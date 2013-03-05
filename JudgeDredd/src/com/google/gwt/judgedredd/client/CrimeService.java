package com.google.gwt.judgedredd.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("crime")
public interface CrimeService extends RemoteService {
	public void addReport() throws NotLoggedInException;
}
