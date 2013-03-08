package com.google.gwt.judgedredd.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class AdminService implements EntryPoint {

	@Override
	public void onModuleLoad() {
		// TODO Auto-generated method stub
		RootPanel rootPanel = RootPanel.get();
				
		Login login_1 = new Login();
		rootPanel.add(login_1, 525, 125);
	}
}
