package org.skillbill.common;

public class MatchDTO {
	
	
	private Mitarbeiter mitarbeiter;
	
	private Double matchProzent;
	
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

}
