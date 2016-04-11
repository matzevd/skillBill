package org.skillbill.common;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;



@Entity
@NamedQueries({
    @NamedQuery(name="ausschreibung.findAll", query="select a from Ausschreibung a"),
}) 


public class Ausschreibung {
	

	

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String beschreibung;
	private String kurzbezeichnung;
	private String herausgeber;
	private Integer benoetigtePT = 0;


	public Long getId() {
		return id;
	}


	public String getBeschreibung() {
		return beschreibung;
	}


	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}


	public String getHerausgeber() {
		return herausgeber;
	}


	public void setHerausgeber(String herausgeber) {
		this.herausgeber = herausgeber;
	}



	public String getKurzbezeichnung() {
		return kurzbezeichnung;
	}


	public void setKurzbezeichnung(String kurzbezeichnung) {
		this.kurzbezeichnung = kurzbezeichnung;
	}


	public Integer getBenoetigtePT() {
		return benoetigtePT;
	}


	public void setBenoetigtePT(Integer benoetigtePT) {
		this.benoetigtePT = benoetigtePT;
	}
	
}
