
package org.skillbill.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Diese Klasse dient dazu, Daten als Container vorzuhalten, um diese gebündelt auf der Oberflächen anzeigen zu können
 * @author Matthias van Dijk
 *
 */
public class MatchDTO {


	private Mitarbeiter mitarbeiter;
	
	private Ausschreibung ausschreibung;
	
	private Double matchProzent;
	
	private List<Skill> listGefundenerSkills;
	
	public MatchDTO(){};
	

	public MatchDTO(Mitarbeiter mitarbeiter, Double matchProzent) {
		super();
		this.mitarbeiter = mitarbeiter;
		this.matchProzent = matchProzent;
	}

	public Mitarbeiter getMitarbeiter() {
		if (mitarbeiter == null){
			mitarbeiter = new Mitarbeiter();
		}
		return mitarbeiter;
	}

	public void setMitarbeiter(Mitarbeiter mitarbeiter) {
		this.mitarbeiter = mitarbeiter;
	}

	public Double getMatchProzent() {
		return matchProzent;
	}

	public void setMatchProzent(Double matchProzent) {
		this.matchProzent = matchProzent;
	}


	public List<Skill> getListGefundenerSkills() {
		if (listGefundenerSkills == null){
			listGefundenerSkills = new ArrayList<Skill>();
		}
		return listGefundenerSkills;
	}


	public void setListGefundenerSkills(List<Skill> listGefundenerSkills) {
		this.listGefundenerSkills = listGefundenerSkills;
	}


	public Ausschreibung getAusschreibung() {
		return ausschreibung;
	}


	public void setAusschreibung(Ausschreibung ausschreibung) {
		this.ausschreibung = ausschreibung;
	}

}
