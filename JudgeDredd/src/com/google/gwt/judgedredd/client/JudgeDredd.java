package com.google.gwt.judgedredd.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class JudgeDredd implements EntryPoint {
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() 
	{
		System.out.println("I am here");
		String text1 = "Lorem ipsum dolor sit amet...";
		String text2 = "Sed egestas, arcu nec accumsan...";
		String text3 = "Proin tristique, elit at blandit...";

		TabPanel panel = new TabPanel();
		FlowPanel flowpanel;

		flowpanel = new FlowPanel();
		flowpanel.add((IsWidget) new Label());
		panel.add(flowpanel, "Home");

		flowpanel = new FlowPanel();
		flowpanel.add((IsWidget) new Label());
		panel.add(flowpanel, "Login");

		panel.selectTab(0);

		panel.setSize("500px", "250px");
		panel.addStyleName("table-center");
		RootPanel.get("body").add(panel);
		//RootPanel.get("body").remove(panel);
	}
}
