package com.detail.app.mydetail.controller;

import com.detail.app.mydetail.model.Surgery;
import com.detail.app.mydetail.repository.SurgeriesRepository;
import com.detail.app.mydetail.view.SurgeryView;

public class SurgeriesController {
	private SurgeriesRepository surgeriesRepository;
	private SurgeryView surgeryView;
	
	
	public SurgeriesController(SurgeriesRepository repo, SurgeryView view) {
		this.surgeriesRepository = repo;
		this.surgeryView = view;
	}
	
	public void allSurgeries() {
		surgeryView.showAllSurgeries(surgeriesRepository.findAll());
	}
	
	public void newSurgery(Surgery surgery) {
			surgeriesRepository.save(surgery);
			surgeryView.surgeryAdded(surgery);	
	}
	
	public void deleteSurgery(Surgery surgery) {
		Surgery existingSurgery = surgeriesRepository.findById(surgery.getId());
		if (existingSurgery == null) {
			surgeryView.showError("No existing surgery " + surgery.getId(), existingSurgery);
			return;
		} else {
			surgeriesRepository.delete(surgery.getId());
			surgeryView.surgeryRemoved(surgery);
		}	
	}
}
