package com.detail.app.mydetail;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import static java.util.Collections.emptyList;

import org.junit.Before;
import org.junit.Test;



public class SurgeryTest {
	private Surgery surgery1;
	private Surgery surgery2;
	private SurgeriesRepository surgeriesRepository;
	private SurgeryService surgeryService;
	
	@Before
	public void setup() {
		surgeriesRepository = mock(SurgeriesRepository.class);
		surgeryService = mock(SurgeryService.class);
		surgery1 = new Surgery(surgeriesRepository, surgeryService, "Paziente1");
		surgery2 = new Surgery(surgeriesRepository, surgeryService, "Paziente2");
	}
	@Test 
	public void testSpiedListOfSurgeries() {
		List<Surgery> surgeries = spy(new ArrayList<>());
		doReturn(surgery1).when(surgeries).get(0);
		assertThat(surgeries.get(0)).isEqualTo(surgery1);
	}
	@Test 
	public void testNUmberOfSurgeries() {
		List<Surgery> surgeries = new ArrayList<>();
		surgeries.add(surgery1);
		when(surgeriesRepository.findAll()).thenReturn(surgeries);
		assertThat(surgeriesRepository.findAll().size()).isEqualTo(1);
	}
	@Test 
	public void testSurgeriesListEmpty() {
		when(surgeriesRepository.findAll()).thenReturn(emptyList());
		assertThat(surgeriesRepository.findAll().size()).isZero();
	}
	@Test 
	public void testJouleRecorded() {
		List<Surgery> surgeries = new ArrayList<>();
		surgeries.add(surgery1);
		when(surgeriesRepository.findAll()).thenReturn(surgeries);
		surgery1.pressButton(3);
		verify(surgeryService).setJouleUsed(surgery1.getId(), 9);
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
