package org.skillbill.dao;

import java.util.List;

import org.skillbill.common.Ausschreibung;




public interface AusschreibungDao extends EntityDao<Ausschreibung> {
	
	public List<Ausschreibung> findeAusschreibungMitSkillname(String skillname);

}
