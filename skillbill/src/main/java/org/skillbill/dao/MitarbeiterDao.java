package org.skillbill.dao;

import java.util.List;

import org.skillbill.common.Mitarbeiter;
import org.skillbill.common.Skill;



public interface MitarbeiterDao extends EntityDao<Mitarbeiter> {
	

	public List<Mitarbeiter> findeMitarbeiterMitSkillname(String skillname);
	
	public List<Mitarbeiter> findeMitarbeiterMitSkills(List<Long> skillliste);


	
}
