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
		
		Image image = new Image("Olympic_Henry.jpg");
		rootPanel.add(image, 22, 235);
		image.setSize("185px", "273px");
		
		Label lblUserPanelIs = new Label("User Interface Coming Soon!");
		lblUserPanelIs.setStyleName("user");
		rootPanel.add(lblUserPanelIs, 22, 208);
		lblUserPanelIs.setSize("261px", "34px");;  
									}

	public static UserPanel get() {
	    return UPanel;
	  }
	}

