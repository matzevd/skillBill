package org.skillbill.dao;

import java.util.List;

import org.skillbill.common.Mitarbeiter;



public interface MitarbeiterDao extends EntityDao<Mitarbeiter> {
	

	public List<Mitarbeiter> findeMitarbeiterMitSkillname(String skillname);
	
	public List<Mitarbeiter> findeMitarbeiterMitSkills(List<Long> skillliste);


	
}
