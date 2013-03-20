package com.google.gwt.judgedredd.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.maps.gwt.client.GoogleMap;
import com.google.maps.gwt.client.LatLng;
import com.google.maps.gwt.client.MapOptions;
import com.google.maps.gwt.client.MapTypeId;

public class Map extends Composite
{
	public Map()
	{
		SimplePanel mapPanel = new SimplePanel();
		initWidget(mapPanel);
		mapPanel.setSize("1000px", "550px");
		
		MapOptions options  = MapOptions.create() ;
	    options.setCenter(LatLng.create( +49.2505, -123.1119 ));   
	    options.setZoom( 12 );
	    options.setMapTypeId( MapTypeId.ROADMAP );
	    options.setDraggable(true);
	    options.setMapTypeControl(true);
	    options.setScaleControl(true) ;
	    options.setScrollwheel(true) ;

	    final GoogleMap theMap = GoogleMap.create( mapPanel.getElement(), options );
	}
}
