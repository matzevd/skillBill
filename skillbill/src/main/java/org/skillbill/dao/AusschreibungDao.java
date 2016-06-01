package org.skillbill.dao;

import java.util.List;

import org.skillbill.common.Ausschreibung;



/**
 * Interface für das Ausschreibungsdao
 * Erweitert das EntityDao
 * @author Matthias van Dijk
 *
 */
public interface AusschreibungDao extends EntityDao<Ausschreibung> {
	
	public List<Ausschreibung> findeAusschreibungMitSkillname(String skillname);
	
	public List<Ausschreibung> findeAusschreibungMitSkills(List<Long> skillliste) ;


}
