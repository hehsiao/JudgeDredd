package com.google.gwt.judgedredd.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Image;

public class UserPanel extends Composite {
	static UserPanel UPanel = new UserPanel();

	public UserPanel() {
		RootPanel rootPanel = RootPanel.get();
		
		FlexTable flexTable = new FlexTable();
		flexTable.setStyleName("user");
		rootPanel.add(flexTable, 10, 140);
		flexTable.setSize("428px", "442px");
		
		Label lblUserPanelIs = new Label("User Interface Coming Soon!");
		flexTable.setWidget(2, 1, lblUserPanelIs);
		lblUserPanelIs.setStyleName("user");
		lblUserPanelIs.setSize("261px", "35px");
		
		Image image = new Image("Olympic_Henry.jpg");
		flexTable.setWidget(3, 1, image);
		image.setSize("199px", "288px");;  
									}

	public static UserPanel get() {
	    return UPanel;
	  }
	}

