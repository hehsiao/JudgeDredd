package com.google.gwt.judgedredd.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.maps.gwt.client.GoogleMap;
import com.google.maps.gwt.client.LatLng;
import com.google.maps.gwt.client.MapOptions;
import com.google.maps.gwt.client.MapTypeId;

public class Map extends Composite
{
	public Map()
	{
		final GoogleMap crimeMap;
		
		HorizontalPanel mapPanel = new HorizontalPanel();
		initWidget(mapPanel);
		mapPanel.setSize("1000px", "550px");
		
		SimplePanel filterPanel = new SimplePanel();
		filterPanel.add((IsWidget) new UserPanel());
		mapPanel.add(filterPanel);
		
		MapOptions options  = MapOptions.create() ;
	    options.setCenter(LatLng.create( +49.2505, -123.1119 ));   
	    options.setZoom( 12 );
	    options.setMapTypeId( MapTypeId.ROADMAP );
	    options.setDraggable(true);
	    options.setMapTypeControl(true);
	    options.setScaleControl(true) ;
	    options.setScrollwheel(true) ;

	    SimplePanel crimeMapPanel = new SimplePanel();
	    crimeMap = GoogleMap.create( crimeMapPanel.getElement(), options );
	}
}
