package org.skillbill.dao;

import java.util.List;

import org.skillbill.common.AusschreibungSkill;



public interface AusschreibungSkillDao extends EntityDao<AusschreibungSkill> {
	
	public List<AusschreibungSkill> findByAusschreibungID(final long ausschreibungID);
	
	public List<AusschreibungSkill> findBySkillID(final long skillId);
	
	public List<AusschreibungSkill> findByAusschreibungIdandSkillids(long ausschreibungid,List<Long> listskills) ;

	
	
}
