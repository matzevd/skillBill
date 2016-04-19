package org.skillbill.dao.impl;



import java.util.List;

import javax.persistence.Query;

import org.skillbill.common.MitarbeiterSkill;
import org.skillbill.dao.MitarbeiterSkillDao;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class MitarbeiterSkillDaoImpl extends EntityDaoImpl<MitarbeiterSkill> implements MitarbeiterSkillDao {

	@SuppressWarnings("unchecked")
	public List<MitarbeiterSkill> findByMitarbeiterId(long mitarbeiterid) {
		try {
			final String queryString = "select ms from  MitarbeiterSkill ms where ms.mitarbeiterid = :value ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("value", mitarbeiterid);
			return query.getResultList();
			
		} catch (RuntimeException e){
			throw e;
		}
	}

	
}

