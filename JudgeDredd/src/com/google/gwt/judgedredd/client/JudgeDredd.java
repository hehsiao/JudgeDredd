package com.google.gwt.judgedredd.client;

import com.google.gwt.core.client.EntryPoint;
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
		TabPanel panel = new TabPanel();
		panel.setAnimationEnabled(true);
		FlowPanel flowpanel;
		
		flowpanel = new FlowPanel();
		panel.add(flowpanel, "Map");

		flowpanel = new FlowPanel();
		flowpanel.add((IsWidget) new Login());
		panel.add(flowpanel, "Login");

		panel.selectTab(0);

		panel.setSize("1300px", "1000px");
		panel.addStyleName("table-center");
		RootPanel.get("body").add(panel);
		//RootPanel.get("body").remove(panel);
	}
}
