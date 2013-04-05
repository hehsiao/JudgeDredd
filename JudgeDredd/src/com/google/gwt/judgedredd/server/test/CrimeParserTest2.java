package com.google.gwt.judgedredd.server.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import com.google.gwt.judgedredd.server.CrimeParser;

public class CrimeParserTest2 {
	
	CrimeParser crimeParser1;
	String addr1;
	String addr2;

	@Before
	public void setUp() throws Exception {
		crimeParser1 = new CrimeParser();
		addr1 = "18XX SPYGLASS PL";
		addr2 = "E BROADWAY AVE / VICTORIA DR";
	}
	
	@Test
	public void parseAddress() {
		assertEquals("1800 SPYGLASS PL, Vancouver, BC", crimeParser1.parseAddress(addr1));
		assertEquals("E BROADWAY AVE and VICTORIA DR, Vancouver, BC", crimeParser1.parseAddress(addr2));
	}

}
