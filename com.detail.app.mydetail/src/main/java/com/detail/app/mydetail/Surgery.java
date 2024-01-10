package com.detail.app.mydetail;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Surgery
 *
 */
public class Surgery 
{
	private int id;
	private static int lastId = 0;
	private String patientName;
	private int jouleUsed = 0;
	private SurgeriesRepository surgeriesRepository;
	private SurgeryService surgeryService;
	
	/**TODO: 
	 * aggiungi nel costruttore l'area della zona d'intervento
	 * metodo che calcola il limite di joule utilizzati oltre al quale si rischia in base all'area
	 * metodo che scatta se il limite è stato superato
	 * 
	 * private int secureLimit;
	   private int areaSize;
	
	*/
	
	public static void main(String[] args) {
		System.out.print(args);
	}
	
	private static final Logger LOGGER = LogManager.getLogger(Surgery.class);
	
	public Surgery(SurgeriesRepository surgeriesRepository,SurgeryService surgeryService, String patient) {
		this.id = ++lastId;
		this.patientName = patient;
		this.surgeriesRepository = surgeriesRepository;
		this.surgeryService = surgeryService;
	}
	
	public void setSurgeryJoule(int joule) {
		List<Surgery> surgeries = surgeriesRepository.findAll();
		if(!surgeries.isEmpty()) {
			surgeryService.setJouleUsed(this.id, joule);
		}
		
	};
	
	public int getId() {
		return id;
	}
	public String getPatientName() {
		return patientName;
	}
	public int getJouleUsed() {
		return jouleUsed;
	}
	public void pressButton(int secondsPressed) {
		LOGGER.info("Seconds pressed: " + secondsPressed);
		if (secondsPressed < 0) {
			throw new IllegalArgumentException("Negative seconds passed: " + secondsPressed);
		}
		jouleUsed = jouleUsed + (secondsPressed * 3);
		this.setSurgeryJoule(jouleUsed);
		LOGGER.info("Joule used: " + jouleUsed);
		//if (LOGGER.isDebugEnabled())
		LOGGER.debug(String.format("Success: seconds of pressing(%d), joule used (%d)", secondsPressed, jouleUsed));
	}
	
}
