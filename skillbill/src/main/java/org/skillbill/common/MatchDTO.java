package org.skillbill.common;

import java.util.ArrayList;
import java.util.List;

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
