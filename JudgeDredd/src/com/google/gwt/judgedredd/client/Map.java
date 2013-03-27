package com.google.gwt.judgedredd.client;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.geolocation.client.Geolocation;
import com.google.gwt.geolocation.client.Position;
import com.google.gwt.geolocation.client.PositionError;
import com.google.gwt.geolocation.client.Position.Coordinates;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.maps.gwt.client.Geocoder;
import com.google.maps.gwt.client.GeocoderRequest;
import com.google.maps.gwt.client.GeocoderResult;
import com.google.maps.gwt.client.GeocoderStatus;
import com.google.maps.gwt.client.GoogleMap;
import com.google.maps.gwt.client.InfoWindow;
import com.google.maps.gwt.client.InfoWindowOptions;
import com.google.maps.gwt.client.LatLng;
import com.google.maps.gwt.client.MapOptions;
import com.google.maps.gwt.client.MapTypeId;
import com.google.maps.gwt.client.Marker;
import com.google.maps.gwt.client.MarkerOptions;
import com.google.maps.gwt.client.MouseEvent;

public class Map extends Composite
{

	private Geocoder geocoder;
	private GoogleMap theMap;
	private LatLng userLocation;
	private boolean browserSupportFlag;
	private MapOptions options;

	public Map()
	{
		geocoder = Geocoder.create();
		SimplePanel mapPanel = new SimplePanel();
		initWidget(mapPanel);
		mapPanel.setSize("100%", "100%");
		options = MapOptions.create() ;
		options.setCenter(LatLng.create( +49.2505, -123.1119 ));   
		options.setZoom( 13 );
		options.setMapTypeId( MapTypeId.ROADMAP );
		options.setDraggable(true);
		options.setMapTypeControl(true);
		options.setScaleControl(true) ;
		options.setScrollwheel(true) ;

		theMap = GoogleMap.create( mapPanel.getElement(), options );
		geocodeAddress("Oakridge Center, Vancouver");

		findUserLocation();
	}

	
	/**
	 * 
	 */
	private void findUserLocation() {
		if (Geolocation.isSupported()) {
			browserSupportFlag = true;
			Geolocation.getIfSupported().getCurrentPosition(
					new Callback<Position, PositionError>() {

						@Override
						public void onSuccess(Position result) {
							Coordinates coords = result
									.getCoordinates();
							userLocation = LatLng.create(
									coords.getLatitude(),
									coords.getLongitude());
							MarkerOptions markerOpts2 = MarkerOptions.create();
							options.setCenter(userLocation);   
							markerOpts2.setPosition(userLocation);
							markerOpts2.setMap(theMap);
							markerOpts2.setTitle("User Location");
							final Marker marker2 = Marker.create(markerOpts2);
							InfoWindowOptions infowindowOpts = InfoWindowOptions.create();
							infowindowOpts.setContent("You are here!");

							final InfoWindow infowindow = InfoWindow.create(infowindowOpts);
							marker2.addClickListener(new Marker.ClickHandler() {
								@Override
								public void handle(MouseEvent event) {
									infowindow.open(theMap, marker2);
								}
							});
						}

						@Override
						public void onFailure(PositionError reason) {
							Map.this
							.handleNoGeolocation(browserSupportFlag);
						}
					});
		} else {
			browserSupportFlag = false;
			handleNoGeolocation(browserSupportFlag);
		}
	}

	public void addCrimePoint(String crimeType, double lat, double lng){
		final MarkerOptions markerOpts = MarkerOptions.create();
		markerOpts.setMap(theMap);
		markerOpts.setPosition(LatLng.create(lat, lng));
		
		final Marker marker = Marker.create(markerOpts);
		InfoWindowOptions infowindowOpts = InfoWindowOptions.create();
		infowindowOpts.setContent(crimeType);
		
		final InfoWindow infowindow = InfoWindow.create(infowindowOpts);
		marker.addClickListener(new Marker.ClickHandler() {
			@Override
			public void handle(MouseEvent event) {
				infowindow.open(theMap, marker);
			}
		});
		
	}
	
	
	/**
	 * Handles the geo location errors
	 * @param errorFlag - geolocation errors
	 */
	protected void handleNoGeolocation(Boolean errorFlag) {
		if (errorFlag == true) {
			Window.alert("Geolocation service denied.");
		} else {
			Window.alert("Your browser doesn't support geolocation.");
		}
	}
	
	private void geocodeAddress(final String address){
		System.out.println("Send GeoCode Request");
		GeocoderRequest request = GeocoderRequest.create();
		request.setAddress(address);
		geocoder.geocode(request, new Geocoder.Callback() {
			public void handle(JsArray<GeocoderResult> results, GeocoderStatus status) {
				if (status == GeocoderStatus.OK) {
					GeocoderResult location = results.get(0);
					theMap.setCenter(location.getGeometry().getLocation());
					MarkerOptions markerOpts = MarkerOptions.create();
					markerOpts.setMap(theMap);
					markerOpts.setPosition(location.getGeometry().getLocation());
					final Marker marker = Marker.create(markerOpts);
					InfoWindowOptions infowindowOpts = InfoWindowOptions.create();
					infowindowOpts.setContent(address);

					final InfoWindow infowindow = InfoWindow.create(infowindowOpts);
					marker.addClickListener(new Marker.ClickHandler() {
						@Override
						public void handle(MouseEvent event) {
							infowindow.open(theMap, marker);
						}
					});
				} else {
					Window.alert("Geocode was not successful for the following reason: "
							+ status);
				}
			}
		});
	}

}
