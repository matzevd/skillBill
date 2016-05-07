package org.skillbill.dao.impl;



import java.util.List;

import javax.persistence.Query;

import org.skillbill.common.AusschreibungSkill;
import org.skillbill.common.MitarbeiterSkill;
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
	
	@SuppressWarnings("unchecked")
	public List<AusschreibungSkill> findByAusschreibungIdandSkillids(long ausschreibungid,List<Long> listskills) {
		try {
			final String queryString = "select ask from  AusschreibungSkill ask where ask.ausschreibungid = :value and ask.skillid in :skills";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("value", ausschreibungid);
			query.setParameter("skills", listskills);

			return query.getResultList();
			
		} catch (RuntimeException e){
			throw e;
		}
	}

	
	@SuppressWarnings("unchecked")
	public List<AusschreibungSkill> findBySkillID(long skillId) {
		try {
			final String queryString = "select ask from  AusschreibungSkill ask where ask.skillid = :value ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("value", skillId);
			return query.getResultList();
			
		} catch (RuntimeException e){
			throw e;
		}	}

	
}

