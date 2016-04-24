package org.skillbill.common;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


@Entity
@NamedQueries({
    @NamedQuery(name="ausschreibungskill.findAll", query="select ask from AusschreibungSkill ask"),
}) 
public class AusschreibungSkill {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private long ausschreibungid;
	private long skillid;
	
	
	
	
	public long getSkillid() {
		return skillid;
	}
	public void setSkillid(long skillid) {
		this.skillid = skillid;
	}
	public Long getId() {
		return id;
	}
	public long getAusschreibungid() {
		return ausschreibungid;
	}
	public void setAusschreibungid(long ausschreibungid) {
		this.ausschreibungid = ausschreibungid;
	}

}
