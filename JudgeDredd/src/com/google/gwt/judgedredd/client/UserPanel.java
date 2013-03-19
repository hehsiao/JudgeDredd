package com.google.gwt.judgedredd.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TabPanel;

public class UserPanel extends Composite
{
	static UserPanel UPanel = new UserPanel();

	public UserPanel()
	{
		FlowPanel flowPanel = new FlowPanel();
		initWidget(flowPanel);
		flowPanel.setSize("850px", "450px");
		
		TabPanel tabPanel = new TabPanel();
		tabPanel.setAnimationEnabled(true);
		flowPanel.add(tabPanel);
		tabPanel.setSize("250px", "450px");
		
		flowPanel = new FlowPanel();
		tabPanel.add(flowPanel, "Yearly");
		
		flowPanel = new FlowPanel();
		tabPanel.add(flowPanel, "Monthly");
		
		flowPanel = new FlowPanel();
		tabPanel.add(flowPanel, "Type");
		
		
	}	// end UserPanel()

	public static UserPanel get()
	{
		return UPanel;
	}	// end UserPanel get()
}	// end UserPanel
