package com.google.gwt.judgedredd.client;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.google.gwt.core.client.GWT;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;

import com.google.gwt.judgedredd.client.LoginInfo;
import com.google.gwt.judgedredd.client.LoginService;
import com.google.gwt.judgedredd.client.LoginServiceAsync;
import com.google.gwt.judgedredd.client.NotLoggedInException;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class JudgeDredd implements EntryPoint {
	
	private LoginInfo loginInfo = null;
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label welcomeLabel = new Label("Citizens of Vancouver, this is Judge Dredd.");
	private Label loginLabel = new Label("You are required, by law, to sign in using your Google Account to access the Grand Hall of Justice of Vancouver.");
	private Anchor signInLink = new Anchor("Sign In");
	private Anchor signOutLink = new Anchor("Sign Out");
	private TabPanel panel;
	private FlowPanel flowpanel;
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() 
	{		
		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL(), new AsyncCallback<LoginInfo>() {
			public void onFailure(Throwable error) {
				handleError(error);
			}

			public void onSuccess(LoginInfo result) {
				loginInfo = result;
				if(loginInfo.isLoggedIn()) {
					if(isJudge(loginInfo)){
						loadJudgeDredd();
					}
					else{
						loadCivilians();
					}
				} else {
					loadLogin();
				}
			}
		});

	}


	protected boolean isJudge(LoginInfo loginInfo) {
		// TODO Auto-generated method stub
		final Set<String> JUDGES = new HashSet<String>(Arrays.asList(
			     new String[] {"me@henrychsiao.com","matthew.hsu@gmail.com","jywdominic@gmail.com","alvin.dong92@gmail.com"}
			));
		
		return JUDGES.contains(loginInfo.getEmailAddress());
	}


	/**
	 * Login Interface
	 */
	private void loadLogin() {
		// Assemble login panel.
		panel = new TabPanel();
		panel.setAnimationEnabled(true);

		flowpanel = new FlowPanel();
		flowpanel.add((IsWidget) new Map());
		panel.add(flowpanel, "Map");
		
		signInLink.setHref(loginInfo.getLoginUrl());
		loginPanel.add(welcomeLabel);
		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);
		flowpanel = new FlowPanel();
		flowpanel.add(loginPanel);
		panel.add(flowpanel, "Sign In");

		panel.selectTab(0);

		panel.setSize("180%", "100%");
		panel.addStyleName("table-center");
		RootPanel.get("body").add(panel);
	}

	/**
	 * Loads User Interface
	 */
	private void loadCivilians() {
		// TODO Auto-generated method stub	
		panel = new TabPanel();
		panel.setAnimationEnabled(true);

		flowpanel = new FlowPanel();
		flowpanel.add((IsWidget) new Map());
		panel.add(flowpanel, "Map");

		signOutLink.setHref(loginInfo.getLogoutUrl());
		welcomeLabel = new Label("Hello " + loginInfo.getNickname() + ", you are logged in as a Civilian.");
		loginPanel.add(welcomeLabel);
		loginPanel.add(signOutLink);
		flowpanel = new FlowPanel();
		flowpanel.add(loginPanel);
		panel.add(flowpanel, "Sign Out");

		panel.selectTab(0);

		panel.setSize("180%", "100%");
		panel.addStyleName("table-center");
		RootPanel.get("body").add(panel);
	}

	/**
	 * Loads Administrator Interface
	 */
	private void loadJudgeDredd() {
		// TODO Auto-generated method stub
		panel = new TabPanel();
		panel.setAnimationEnabled(true);

		flowpanel = new FlowPanel();
		flowpanel.add((IsWidget) new Map());
		panel.add(flowpanel, "Map");
		
		flowpanel = new FlowPanel();
		flowpanel.add((IsWidget) new AdminPanel());
		panel.add(flowpanel, "Administration");
		
		signOutLink.setHref(loginInfo.getLogoutUrl());
		welcomeLabel = new Label("Hello " + loginInfo.getNickname() + ", you are logged in as a Judge.");
		loginPanel.add(welcomeLabel);
		loginPanel.add(signOutLink);
		flowpanel = new FlowPanel();
		flowpanel.add(loginPanel);
		panel.add(flowpanel, "Sign Out");
		
		panel.selectTab(0);

		panel.setSize("180%", "100%");
		panel.addStyleName("table-center");
		RootPanel.get("body").add(panel);	
	}
	
	private void handleError(Throwable error) {
		Window.alert(error.getMessage());
		if (error instanceof NotLoggedInException) {
			Window.Location.replace(loginInfo.getLogoutUrl());
		}
	}

}
