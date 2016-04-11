package org.skillbill.common;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.skillbill.enums.GeschlechtEnum;
import org.skillbill.enums.StandortEnum;



@Entity
@NamedQueries({
    @NamedQuery(name="mitarbeiter.findAll", query="select m from Mitarbeiter m"),
}) 


public class Mitarbeiter {
	

	

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String vorname;
	private String nachname;
	private java.util.Date  geburtsdatum;
	private Integer personalnummer;
	private String email;
	private Integer freipt = 0;
	private Long telefonnummer;
	private StandortEnum standort;
	private GeschlechtEnum geschlecht;
	
	public String getVorname() {
		return vorname;
	}
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	public String getNachname() {
		return nachname;
	}
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	public java.util.Date  getGeburtsdatum() {
		return geburtsdatum;
	}
	public void setGeburtsdatum(java.util.Date geburtsdatum) {
		 
		this.geburtsdatum = geburtsdatum;
	}
	public Integer getPersonalnummer() {
		return personalnummer;
	}
	public void setPersonalnummer(Integer personalnummer) {
		this.personalnummer = personalnummer;
	}
	public GeschlechtEnum getGeschlecht() {
		return geschlecht;
	}
	public void setGeschlecht(GeschlechtEnum geschlecht) {
		this.geschlecht = geschlecht;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getFreipt() {
		return freipt;
	}
	public void setFreipt(Integer freipt) {
		this.freipt = freipt;
	}
	public Long getId() {
		return id;
	}
	public Long getTelefonnummer() {
		return telefonnummer;
	}
	public void setTelefonnummer(Long telefonnummer) {
		this.telefonnummer = telefonnummer;
	}
	public StandortEnum getStandort() {
		return standort;
	}
	public void setStandort(StandortEnum standort) {
		this.standort = standort;
	}
}
