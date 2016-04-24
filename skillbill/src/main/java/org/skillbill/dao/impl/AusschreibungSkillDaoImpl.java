package org.skillbill.dao.impl;



import java.util.List;

import javax.persistence.Query;

import org.skillbill.common.AusschreibungSkill;
import org.skillbill.dao.AusschreibungSkillDao;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class AusschreibungSkillDaoImpl extends EntityDaoImpl<AusschreibungSkill> implements AusschreibungSkillDao {

	@SuppressWarnings("unchecked")
	public List<AusschreibungSkill> findByAusschreibungID(long ausschreibungid) {
		try {
			final String queryString = "select ask from  AusschreibungSkill ask where ask.ausschreibungid = :value ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("value", ausschreibungid);
			return query.getResultList();
			
		} catch (RuntimeException e){
			throw e;
		}
	}

	
}

