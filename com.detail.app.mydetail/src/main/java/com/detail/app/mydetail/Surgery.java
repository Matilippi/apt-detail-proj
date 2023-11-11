package com.detail.app.mydetail;

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
	
	/**TODO: 
	 * aggiungi nel costruttore l'area della zona d'intervento
	 * metodo che calcola il limite di joule utilizzati oltre al quale si rischia in base all'area
	 * metodo che scatta se il limite è stato superato
	 * 
	 * private int secureLimit;
	   private int areaSize;
	
	*/
	
	
	public Surgery(String patient) {
		this.id = ++lastId;
		this.patientName = patient;
	}
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
		if (secondsPressed < 0) {
			throw new IllegalArgumentException("Negative seconds passed: " + secondsPressed);
		}
		jouleUsed = jouleUsed + (secondsPressed * 3);
	}
	
}
