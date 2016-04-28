package org.skillbill.dao.impl;


import javax.persistence.Query;

import org.skillbill.common.Skill;
import org.skillbill.dao.SkillDao;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Transactional
public class SkillDaoImpl extends EntityDaoImpl<Skill> implements SkillDao {

	public Skill findByName(String skillname){
		try {
			final String queryString = "select sk from  Skill sk where UPPER(sk.kurzbezeichnung) like :value ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("value", "%" + skillname.toUpperCase() + "%");
			return (Skill) query.getSingleResult();
			
		} catch (RuntimeException e){
			throw e;
		}
		
	}
}

