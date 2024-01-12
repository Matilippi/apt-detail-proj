package com.detail.app.mydetail;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import static java.util.Collections.emptyList;

import org.junit.Before;
import org.junit.Test;

import com.detail.app.mydetail.model.Surgery;
import com.detail.app.mydetail.repository.SurgeriesRepository;



public class SurgeryTest {
	private Surgery surgery1;
	private Surgery surgery2;
	
	@Before
	public void setup() {
		surgery1 = new Surgery("1", "Paziente1");
		surgery2 = new Surgery("2", "Paziente2");
	}
	
	@Test
	public void testIsNameAssignedCorrectly() {
		assertThat(surgery1.getPatientName()).isEqualTo("Paziente1");
	}

	/**@Test
	public void testIsAutomaticallyAssignPositiveId() {
		assertThat(surgery1.getId()).isPositive();
	}**/
	
	/**@Test
	public void testIdAreIncremental() {
		assertThat(surgery2.getId()).isGreaterThan(surgery1.getId());
	}**/
	
	@Test
	public void testJouleIsCorrectlyCalculated() {
		surgery1.pressButton(3);

		assertThat(surgery1.getJouleUsed()).isEqualTo(9);
	}
	@Test
	public void testJouleIsCorrectlyCalculatedwithZero() {
		surgery1.pressButton(0);

		assertThat(surgery1.getJouleUsed()).isEqualTo(0);
	}
	
	@Test
	public void testIfSecondsNegativeShouldThrow() {
		assertThatThrownBy(()->
		surgery1.pressButton(-3))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("Negative seconds passed: -3");
	}
	

}
