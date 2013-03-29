package com.google.gwt.judgedredd.client;

import com.google.gwt.judgedredd.client.LoginInfo;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginServiceAsync {
  public void login(String requestUri, AsyncCallback<LoginInfo> async);
}