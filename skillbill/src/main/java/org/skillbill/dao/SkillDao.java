package org.skillbill.dao;

import java.util.List;

import org.skillbill.common.Skill;



public interface SkillDao extends EntityDao<Skill> {
	
	public Skill findByName(String skillname);
	
	public List<Skill>  findByNames(List<String> skilllist);

	
}
