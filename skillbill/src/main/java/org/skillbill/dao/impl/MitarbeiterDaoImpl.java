package org.skillbill.dao.impl;



import java.util.List;

import javax.persistence.Query;

import org.skillbill.common.Mitarbeiter;
import org.skillbill.dao.MitarbeiterDao;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class MitarbeiterDaoImpl extends EntityDaoImpl<Mitarbeiter> implements MitarbeiterDao {
	
	@SuppressWarnings("unchecked")
		public List<Mitarbeiter> findeMitarbeiterMitSkillname(String skillname){
			
				final String queryString = "select mit from Mitarbeiter as mit, MitarbeiterSkill as ms, Skill as sk "
						+ "where LOWER(sk.kurzbezeichnung) = :value "
						+ "and sk.id = ms.skillid "
						+ "and ms.mitarbeiterid = mit.id";
				Query query = getEntityManager().createQuery(queryString);
				query.setParameter("value", skillname.toLowerCase());
				return query.getResultList();
				
			
		}

	
	@SuppressWarnings("unchecked")
	public List<Mitarbeiter> findeMitarbeiterMitSkills(List<Long> skillliste) {


		final String queryString = "select distinct mit from Mitarbeiter as mit, MitarbeiterSkill as ms "
				+ "where ms.skillid in :value "
				+ "and ms.mitarbeiterid = mit.id";
		Query query = getEntityManager().createQuery(queryString);
		query.setParameter("value", skillliste);
		return query.getResultList();		
	}		
	
}

