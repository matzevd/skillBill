package org.skillbill.dao.impl;


import java.util.List;

import javax.persistence.Query;

import org.skillbill.common.Skill;
import org.skillbill.dao.SkillDao;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@Transactional
/**
 * Implementierungsklasse für das Interface SkillDao
 * Hier werden die spezifischen Methoden für das Dao implementiert
 * @author Matthias van Dijk
 *
 *Alle Methoden dieser Klasse werden in einer Transaktion ausgeführt.
 */
public class SkillDaoImpl extends EntityDaoImpl<Skill> implements SkillDao{



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
	
	@SuppressWarnings("unchecked")
	public List<Skill> findByNames(List<String> skilllist){
		try {
			final String queryString = "select sk from  Skill sk where LOWER(sk.kurzbezeichnung) in :value ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("value",  skilllist);
			return query.getResultList();
			
		} catch (RuntimeException e){
			throw e;
		}
		
	}
}

