package com.google.gwt.judgedredd.server;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.xml.sax.SAXException;

public class Geocoder {

	// URL prefix to the geocoder
	private static final String GEOCODER_REQUEST_PREFIX_FOR_XML = "http://maps.google.com/maps/api/geocode/xml";
	private double[] latlng = new double[2];

	public Geocoder (){
	}

	public Geocoder (String address) throws IOException, XPathExpressionException, ParserConfigurationException, SAXException {

		// prepare a URL to the geocoder
		URL url = new URL(GEOCODER_REQUEST_PREFIX_FOR_XML + "?address=" + URLEncoder.encode(address, "UTF-8") + "&sensor=false");

		// prepare an HTTP connection to the geocoder
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		Document geocoderResultDocument = null;
		try {
			// open the connection and get results as InputSource.
			conn.connect();
			InputSource geocoderResultInputSource = new InputSource(conn.getInputStream());

			// read result and parse into XML Document
			geocoderResultDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(geocoderResultInputSource);
		} finally {
			conn.disconnect();
		}

		// prepare XPath
		XPath xpath = XPathFactory.newInstance().newXPath();

		// extract the result
		NodeList resultNodeList = null;

		resultNodeList = (NodeList) xpath.evaluate("/GeocodeResponse/result[1]/geometry/location/*", geocoderResultDocument, XPathConstants.NODESET);
		double lat = Double.NaN;
		double lng = Double.NaN;
		for(int i=0; i<resultNodeList.getLength(); ++i) {
			Node node = resultNodeList.item(i);
			if("lat".equals(node.getNodeName())) lat = Double.parseDouble(node.getTextContent());
			if("lng".equals(node.getNodeName())) lng = Double.parseDouble(node.getTextContent()); 
		}
		
		System.out.println("lat/lng=" + lat + "," + lng);
		
		latlng[0] = lat;
		latlng[1] = lng;

	}

	/**
	 * @return the latlng
	 */
	public double[] getLatlng() {
		return latlng;
	}
	

}