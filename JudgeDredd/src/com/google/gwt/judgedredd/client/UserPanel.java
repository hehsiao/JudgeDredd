package com.google.gwt.judgedredd.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.maps.gwt.client.GoogleMap;
import com.google.maps.gwt.client.LatLng;
import com.google.maps.gwt.client.MapOptions;
import com.google.maps.gwt.client.MapTypeId;

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
		
		MapOptions options  = MapOptions.create() ;

	    options.setCenter(LatLng.create( +49.2505, -123.1119 ));   
	    options.setZoom( 12 );
	    options.setMapTypeId( MapTypeId.ROADMAP );
	    options.setDraggable(true);
	    options.setMapTypeControl(true);
	    options.setScaleControl(true) ;
	    options.setScrollwheel(true) ;

	    SimplePanel mapPanel = new SimplePanel() ;

	    mapPanel.setSize("500px","500px");

	    final GoogleMap theMap = GoogleMap.create( mapPanel.getElement(), options ) ;
	    RootLayoutPanel.get().add( mapPanel ) ;
	    
	    tabPanel.add(flowPanel, "Map");
		
		
	}	// end UserPanel()

	public static UserPanel get()
	{
		return UPanel;
	}	// end UserPanel get()
	
	
}	// end UserPanel
