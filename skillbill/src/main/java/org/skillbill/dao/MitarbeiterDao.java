package org.skillbill.dao;

import java.util.List;

import org.skillbill.common.Mitarbeiter;


/**
 * Interface für das MitarbeiterDao
 * Erweitert das EntityDao
 * @author Matthias van Dijk
 *
 */
public interface MitarbeiterDao extends EntityDao<Mitarbeiter> {
	

	public List<Mitarbeiter> findeMitarbeiterMitSkillname(String skillname);
	
	public List<Mitarbeiter> findeMitarbeiterMitSkills(List<Long> skillliste);


	
}
