package org.skillbill.common;



import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;




@Entity
@NamedQueries({
    @NamedQuery(name="skill.findAll", query="select s from Skill s"),
}) 


public class Skill implements Serializable {
	

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -4970560320159884453L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String kurzbezeichnung;
	private String beschreibung;
	
	/*@ManyToMany(mappedBy="skilllist")
	private List<Mitarbeiter> mitarbeiterSkilllist = new ArrayList<Mitarbeiter>();*/
	
	public String getKurzbezeichnung() {
		return kurzbezeichnung;
	}
	public void setKurzbezeichnung(String kurzbezeichnung) {
		this.kurzbezeichnung = kurzbezeichnung;
	}
	public String getBeschreibung() {
		return beschreibung;
	}
	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}
	public Long getId() {
		return id;
	}
	

	

	
}
