package org.skillbill.dao;

import org.skillbill.common.Skill;



public interface SkillDao extends EntityDao<Skill> {
	
	public Skill findByName(String skillname);
	
}
