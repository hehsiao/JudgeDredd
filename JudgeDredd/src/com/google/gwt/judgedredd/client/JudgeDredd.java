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
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class JudgeDredd implements EntryPoint {
	
	private LoginInfo loginInfo = null;
	private VerticalPanel loginPanel = new VerticalPanel();
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
				if(loginInfo.isLoggedIn() && isJudge(loginInfo)) {
					loadJudgeDredd();
				} else {
					loadCivilians();
				}
			}
		});

	}


	/**
	 * @param loginInfo
	 * @return true - if loginInfo email matches one of the ones in the list
	 */
	protected boolean isJudge(LoginInfo loginInfo) {
		final Set<String> JUDGES = new HashSet<String>(Arrays.asList(
			     new String[] {"me@henrychsiao.com","matthew.hsu@gmail.com","jywdominic@gmail.com","alvin.dong92@gmail.com"}
			));
		
		return JUDGES.contains(loginInfo.getEmailAddress());
	}

	/**
	 * Loads User Interface
	 */
	private void loadCivilians() {
		
		if(loginInfo.isLoggedIn()){
			signOutLink.setHref(loginInfo.getLogoutUrl());
			loginPanel.add(signOutLink);
		} else {
			signInLink.setHref(loginInfo.getLoginUrl());
			loginPanel.add(signInLink);
		}
		
		RootPanel.get("login").add(loginPanel);
		
		panel = new TabPanel();
		panel.setAnimationEnabled(true);

		flowpanel = new FlowPanel();
		flowpanel.add((IsWidget) new UserPanel());
		panel.add(flowpanel, "Home");
		
		panel.selectTab(0);

		panel.setSize("100%", "100%");
		panel.addStyleName("table-center");
		RootPanel.get("body").add(panel);
	}

	/**
	 * Loads Administrator Interface
	 */
	private void loadJudgeDredd() {
		
		signOutLink.setHref(loginInfo.getLogoutUrl());
		loginPanel.add(signOutLink);
		
		panel = new TabPanel();
		panel.setAnimationEnabled(true);
		
		flowpanel = new FlowPanel();
		flowpanel.add((IsWidget) new UserPanel());
		panel.add(flowpanel, "Home");
		
		flowpanel = new FlowPanel();
		flowpanel.add((IsWidget) new AdminPanel());
		panel.add(flowpanel, "Import Data");
		
		panel.selectTab(0);

		panel.setSize("100%", "100%");
		panel.addStyleName("table-center");

		RootPanel.get("login").add(loginPanel);
		RootPanel.get("body").add(panel);
	}
	
	/**
	 * handle Error with a GUI interface
	 * @param error
	 */
	private void handleError(Throwable error) {
		Window.alert(error.getMessage());
		if (error instanceof NotLoggedInException) {
			Window.Location.replace(loginInfo.getLogoutUrl());
		}
	}

}
