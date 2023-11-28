package com.detail.app.mydetail;

import static org.assertj.core.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;


public class SurgeryTest {
	private Surgery surgery1 = new Surgery("Paziente1");
	private Surgery surgery2 = new Surgery("Paziente2");
	
	@Before
	public void setup() {
		surgery1 = new Surgery("Paziente1");
		surgery2 = new Surgery("Paziente2");
	}

	@Test
	public void testIsAutomaticallyAssignPositiveId() {
		assertThat(surgery1.getId()).isPositive();
	}
	
	@Test
	public void testIdAreIncremental() {
		assertThat(surgery2.getId()).isGreaterThan(surgery1.getId());
	}
	
	@Test
	public void testNameIsCorrectlyAssign() {
		assertThat(surgery1.getPatientName()).isEqualTo("Paziente1");
	}
	
	@Test
	public void testJouleIsCorrectlyCalculated() {
		surgery1.pressButton(3);

		assertThat(surgery1.getJouleUsed()).isEqualTo(9);
	}
	
	@Test
	public void testIfSecondsNegativeShouldThrow() {
		assertThatThrownBy(()->
		surgery1.pressButton(-3))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("Negative seconds passed: -3");
	}
	

}
