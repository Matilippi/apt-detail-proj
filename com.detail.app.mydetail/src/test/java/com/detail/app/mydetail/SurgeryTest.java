package com.detail.app.mydetail;

import static org.assertj.core.api.Assertions.*;
import org.junit.Test;


public class SurgeryTest {

	@Test
	public void testIsAutomaticallyAssignPositiveId() {
		Surgery surgery = new Surgery("Paziente1");
		assertThat(surgery.getId()).isPositive();
	}
	
	@Test
	public void testIdAreIncremental() {
		Surgery surgery1 = new Surgery("Paziente1");
		Surgery surgery2 = new Surgery("Paziente2");

		assertThat(surgery2.getId()).isGreaterThan(surgery1.getId());
	}
	
	@Test
	public void testNameIsCorrectlyAssign() {
		Surgery surgery1 = new Surgery("Paziente1");

		assertThat(surgery1.getPatientName()).isEqualTo("Paziente1");
	}
	
	@Test
	public void testJouleIsCorrectlyCalculated() {
		Surgery surgery = new Surgery("Paziente1");
		surgery.pressButton(3);

		assertThat(surgery.getJouleUsed()).isEqualTo(9);
	}
	
	@Test
	public void testIfSecondsNegativeShouldThrow() {
		
		Surgery surgery = new Surgery("Paziente1");
		assertThatThrownBy(()->
		surgery.pressButton(-3))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("Negative seconds passed: -3");
	}
	

}
