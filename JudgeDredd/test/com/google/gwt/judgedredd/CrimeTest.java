package com.google.gwt.judgedredd;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import com.google.gwt.judgedredd.server.Crime;

public class CrimeTest {
	
	Crime crime1;
	
	@Before
	public void setUp() throws Exception {
		crime1 = new Crime("Assault", 2013, 3, "Surrey");
	}
		
	@Test
	public void testCrimeType() {
		assertEquals("Assualt", crime1.getType());
	}
	
	@Test
	public void testYear() {
		assertEquals(2013, crime1.getCrimeYear());
	}
	
	@Test
	public void testMonth() {
		assertEquals(3, crime1.getCrimeMonth());
	}
	
	@Test
	public void testLocation() {
		assertEquals("Surrey", crime1.getLocation());
	}
	
	@Test
	public void testApproved() {
		assertEquals(false, crime1.isApproved());
		crime1.setApproval();
		assertEquals(true, crime1.isApproved());
	}
}
