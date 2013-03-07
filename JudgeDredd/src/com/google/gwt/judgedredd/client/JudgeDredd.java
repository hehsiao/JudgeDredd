package com.google.gwt.judgedredd.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class JudgeDredd implements EntryPoint {
	
	private final CrimeServiceAsync crimeService = GWT.create(CrimeService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		Button btnParse = new Button("Parse Data");
		btnParse.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				System.out.println("BUTTON PRESSED");
				crimeService.addReport("Test", new AsyncCallback<Void>() {
				      public void onFailure(Throwable error) {
				         System.out.println("Failed");
				      }
				      public void onSuccess(Void ignore) {
				    	  System.out.println("Success");
				      }
				    });
			}
		});
	}
}
