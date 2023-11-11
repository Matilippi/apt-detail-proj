package com.detail.app.mydetail;

import static org.junit.Assert.*;
import org.junit.Test;


public class SurgeryTest {

	@Test
	public void testIsAutomaticallyAssignPositiveId() {
		Surgery surgery = new Surgery("Paziente1");
		assertTrue("Id should be positive",surgery.getId()>0);
	}
	
	@Test
	public void testIdAreIncremental() {
		Surgery surgery1 = new Surgery("Paziente1");
		Surgery surgery2 = new Surgery("Paziente2");

		assertTrue("Id are incremental", surgery1.getId()<surgery2.getId());
	}
	
	@Test
	public void testNameIsCorrectlyAssign() {
		Surgery surgery1 = new Surgery("Paziente1");

		assertEquals("Paziente1",surgery1.getPatientName());
	}
	
	@Test
	public void testJouleIsCorrectlyCalculated() {
		Surgery surgery = new Surgery("Paziente1");
		surgery.pressButton(3);

		assertEquals(9,surgery.getJouleUsed());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testIfSecondsNegativeShouldThrow() {
		
		Surgery surgery = new Surgery("Paziente1");
		surgery.pressButton(-3);
	}
	

}
