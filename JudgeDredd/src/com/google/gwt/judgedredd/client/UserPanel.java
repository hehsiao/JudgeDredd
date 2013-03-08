package com.google.gwt.judgedredd.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Image;

public class UserPanel extends Composite {
	static UserPanel UPanel = new UserPanel();

	public UserPanel() {
		RootPanel rootPanel = RootPanel.get();
		
		FlexTable flexTable = new FlexTable();
		flexTable.setStyleName("user");
		rootPanel.add(flexTable, 525, 300);
		flexTable.setSize("300px", "300px");
		
		Label lblUserPanelIs = new Label("The Law? I am the Law!");
		flexTable.setWidget(2, 1, lblUserPanelIs);
		lblUserPanelIs.setStyleName("user");
		lblUserPanelIs.setSize("260px", "35px");
		
		Image image = new Image("Olympic_Henry.jpg");
		flexTable.setWidget(3, 1, image);
		image.setSize("140px", "188px");;  
									}

	public static UserPanel get() {
	    return UPanel;
	  }
}
