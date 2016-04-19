package org.skillbill.dao.impl;


import org.skillbill.common.Skill;
import org.skillbill.dao.SkillDao;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Transactional
public class SkillDaoImpl extends EntityDaoImpl<Skill> implements SkillDao {

	
}

