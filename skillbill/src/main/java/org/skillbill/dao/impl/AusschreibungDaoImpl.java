package org.skillbill.dao.impl;


import java.util.List;

import javax.persistence.Query;

import org.skillbill.common.Ausschreibung;
import org.skillbill.dao.AusschreibungDao;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Transactional
public class AusschreibungDaoImpl extends EntityDaoImpl<Ausschreibung> implements AusschreibungDao {


	@SuppressWarnings("unchecked")
		public List<Ausschreibung> findeAusschreibungMitSkillname(String skillname){
			
				final String queryString = "select aus from Ausschreibung as aus, AusschreibungSkill as ask, Skill as sk "
						+ "where LOWER(sk.kurzbezeichnung) = :value "
						+ "and sk.id = ask.skillid "
						+ "and ask.ausschreibungid = aus.id";
				Query query = getEntityManager().createQuery(queryString);
				query.setParameter("value", skillname.toLowerCase());
				return query.getResultList();
				
			
		}	
	
	@SuppressWarnings("unchecked")
	public List<Ausschreibung> findeAusschreibungMitSkills(List<Long> skillliste) {


		final String queryString = "select distinct aus from Ausschreibung as aus, AusschreibungSkill as ask "
				+ "where ask.skillid in :value "
				+ "and ask.ausschreibungid = aus.id";
		Query query = getEntityManager().createQuery(queryString);
		query.setParameter("value", skillliste);
		return query.getResultList();		
	}
}

