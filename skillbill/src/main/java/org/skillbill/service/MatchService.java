package org.skillbill.service;

import java.util.List;

import org.skillbill.common.Ausschreibung;
import org.skillbill.common.Mitarbeiter;
import org.skillbill.dao.AusschreibungDao;
import org.skillbill.dao.MitarbeiterDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchService {
	

	
	@Autowired
	private AusschreibungDao ausschreibungDao;
	
	@Autowired
	private MitarbeiterDao mitarbeiterDao;

	public void sucheMitarbeiterMatchesZuSkill() {

	}

	public void sucheMatchesZuAusschreibung() {

	}

	public void sucheMatchesZuMitarbeiter() {

	}

	public List<Ausschreibung> sucheAusschreibungMatchesZuSkill(String skillname) {
		
		List<Ausschreibung> listAusschreibungen = ausschreibungDao.findeAusschreibungMitSkillname(skillname);
		return listAusschreibungen;
		
	}

	
	public List<Mitarbeiter> sucheMitarbeiterMatchesZuSkill(String skillname) {
		List<Mitarbeiter> listMitarbeiterZuSkill = mitarbeiterDao.findeMitarbeiterMitSkillname(skillname);
		
		return listMitarbeiterZuSkill;
		
	}


	public MitarbeiterDao getMitarbeiterDao() {
		return mitarbeiterDao;
	}

	public void setMitarbeiterDao(MitarbeiterDao mitarbeiterDao) {
		this.mitarbeiterDao = mitarbeiterDao;
	}

}
