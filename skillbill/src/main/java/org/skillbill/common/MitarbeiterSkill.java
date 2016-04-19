package org.skillbill.common;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


@Entity
@NamedQueries({
    @NamedQuery(name="mitarbeiterskill.findAll", query="select ms from Mitarbeiterskill ms"),
}) 
public class MitarbeiterSkill {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private long mitarbeiterid;
	private long skillid;
	public long getMitarbeiterid() {
		return mitarbeiterid;
	}
	public void setMitarbeiterid(long mitarbeiterid) {
		this.mitarbeiterid = mitarbeiterid;
	}
	public long getSkillid() {
		return skillid;
	}
	public void setSkillid(long skillid) {
		this.skillid = skillid;
	}
	public Long getId() {
		return id;
	}

}
