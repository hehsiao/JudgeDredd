package com.google.gwt.judgedredd.client;

import java.io.Serializable;

public class NotLoggedInException extends Exception implements Serializable {

  public NotLoggedInException() {
    super();
  }

  public NotLoggedInException(String message) {
    super(message);
  }

}