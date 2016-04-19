package org.skillbill.dao;

import java.util.List;

import org.skillbill.common.MitarbeiterSkill;



public interface MitarbeiterSkillDao extends EntityDao<MitarbeiterSkill> {
	
	public List<MitarbeiterSkill> findByMitarbeiterId(final long mitarbeiterid);
	
	
}
