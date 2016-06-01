package org.skillbill.dao.impl;



import java.util.List;

import javax.persistence.Query;

import org.skillbill.common.MitarbeiterSkill;
import org.skillbill.dao.MitarbeiterSkillDao;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
/**
 * Implementierungsklasse für das Interface MitarbeiterSkillDao
 * Hier werden die spezifischen Methoden für das Dao implementiert
 * @author Matthias van Dijk
 *
 *Alle Methoden dieser Klasse werden in einer Transaktion ausgeführt.
 */
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
	
	@SuppressWarnings("unchecked")
	public List<MitarbeiterSkill> findByMitarbeiterIdandSkillids(long mitarbeiterid,List<Long> listskills) {
		try {
			final String queryString = "select ms from  MitarbeiterSkill ms where ms.mitarbeiterid = :value and ms.skillid in :skills";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("value", mitarbeiterid);
			query.setParameter("skills", listskills);

			return query.getResultList();
			
		} catch (RuntimeException e){
			throw e;
		}
	}

	
	@SuppressWarnings("unchecked")
	public List<MitarbeiterSkill> findBySkillIds(List<Long> skillids) {

		
		try {
			final String queryString = "select ms from  MitarbeiterSkill ms where ms.skillid in :value ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("value", skillids);
			return query.getResultList();
			
		} catch (RuntimeException e){
			throw e;
		}
	}

	
}

