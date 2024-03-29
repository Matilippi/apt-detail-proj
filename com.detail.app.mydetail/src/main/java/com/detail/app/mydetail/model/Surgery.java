package com.detail.app.mydetail.model;

import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Surgery
 *
 */
public class Surgery 
{
	private String id;
	private String patientName;
	private int jouleUsed = 0;
	
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
	
	public Surgery(String id, String patientName) {
		this.id = id;
		this.patientName = patientName;
	}
	
	public String getId() {
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
		/**this.setSurgeryJoule(jouleUsed);**/
		LOGGER.info("Joule used: " + jouleUsed);
		//if (LOGGER.isDebugEnabled())
		LOGGER.debug(String.format("Success: seconds of pressing(%d), joule used (%d)", secondsPressed, jouleUsed));
	}
	@Override
	public int hashCode() {
		return Objects.hash(id, patientName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Surgery other = (Surgery) obj;
		return Objects.equals(id, other.id) && Objects.equals(patientName, other.patientName);
	}

	@Override
	public String toString() {
		return "Surgery [id=" + id + ", name=" + patientName + "]";
	}
}
