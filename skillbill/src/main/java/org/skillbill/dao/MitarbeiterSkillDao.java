package org.skillbill.dao;

import java.util.List;

import org.skillbill.common.MitarbeiterSkill;


/**
 * Interface für das MitarbeiterSkillDao
 * Erweitert das EntityDao
 * @author Matthias van Dijk
 *
 */
public interface MitarbeiterSkillDao extends EntityDao<MitarbeiterSkill> {
	
	public List<MitarbeiterSkill> findByMitarbeiterId(final long mitarbeiterid);
	
	public List<MitarbeiterSkill> findBySkillIds (List<Long> skillids);
	
	public List<MitarbeiterSkill> findByMitarbeiterIdandSkillids(long mitarbeiterid,List<Long> listskills) ;

	
}
