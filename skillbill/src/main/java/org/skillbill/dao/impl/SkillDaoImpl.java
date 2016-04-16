package org.skillbill.dao.service;


import org.skillbill.common.Skill;
import org.skillbill.dao.SkillDao;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Transactional
public class SkillService extends EntityService<Skill> implements SkillDao {

	
}

