package com.detail.app.mydetail.controller;

import static org.mockito.Mockito.ignoreStubs;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static java.util.Arrays.asList;


import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.detail.app.mydetail.model.Surgery;
import com.detail.app.mydetail.repository.SurgeriesRepository;
import com.detail.app.mydetail.view.SurgeryView;

public class SurgeriesControllerTest{
	
	@Mock
	private SurgeriesRepository surgeriesRepository;
	
	@Mock
	private SurgeryView surgeryView;
	
	@InjectMocks
	private SurgeriesController surgeriesController;
	
	private AutoCloseable closeable;

	@Before
	public void setup() {
		closeable = MockitoAnnotations.openMocks(this);
	}
	
	@After
	public void releaseMocks() throws Exception{
		closeable.close();
	}
	
	@Test
	public void testAllSurgeries() {
		List <Surgery> surgeries = asList(new Surgery("1", "Paziente1"));
		when(surgeriesRepository.findAll()).thenReturn(surgeries);
		surgeriesController.allSurgeries();
		verify(surgeryView).showAllSurgeries(surgeries);
	}
	
	@Test
	public void TestNewSurgeryWhenSurgeryAdded() {
		Surgery surgery = new Surgery("1", "Paziente1");
		when(surgeriesRepository.findById("1")).thenReturn(null);
		surgeriesController.newSurgery(surgery);
		InOrder inOrder = Mockito.inOrder(surgeriesRepository, surgeryView);
		inOrder.verify(surgeriesRepository).save(surgery);
		inOrder.verify(surgeryView).surgeryAdded(surgery);
	}
	
	@Test
	public void TestDeleteSurgeryWhenSurgeryExists() {
		Surgery surgeryToDelete = new Surgery("1", "Paziente1");
		when(surgeriesRepository.findById("1")).thenReturn(surgeryToDelete);
		surgeriesController.deleteSurgery(surgeryToDelete);
		InOrder inOrder = Mockito.inOrder(surgeriesRepository, surgeryView);
		inOrder.verify(surgeriesRepository).delete("1");
		inOrder.verify(surgeryView).surgeryRemoved(surgeryToDelete);
		
	}
	@Test
	public void testDeleteSurgeryWhenSurgeryDoesNotExist() {
		Surgery surgery1 = new Surgery("1", "Paziente1");
		when(surgeriesRepository.findById("1")).
			thenReturn(null);
		surgeriesController.deleteSurgery(surgery1);
		verify(surgeryView)
			.showError("No existing surgery 1", surgery1);
		verifyNoMoreInteractions(ignoreStubs(surgeriesRepository));
	}

	
	
}