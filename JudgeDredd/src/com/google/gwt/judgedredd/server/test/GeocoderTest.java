package com.google.gwt.judgedredd.server.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import com.google.gwt.judgedredd.server.Geocoder;

public class GeocoderTest {

	Geocoder geocoder1;
	double[] latlng1;
	double lat1, lng1, lat2, lng2;
	private double DELTA = 1e-15;
	
	@Before
	public void setUp() throws Exception {
		geocoder1 = new Geocoder("1800 SPYGLASS PL, Vancouver, BC");
	}	
		
	
	@Test
	public void testGetLatlong() {
		latlng1 = geocoder1.getLatlng();
		lat1 = latlng1[0];
		lng1 = latlng1[1];
		lat2 = 49.26921740;
		lng2 = -123.11521910;
		
		assertEquals(lat1, lat2, DELTA);
	}

}
