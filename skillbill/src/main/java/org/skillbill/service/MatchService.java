package org.skillbill.service;

import java.util.ArrayList;
import java.util.List;

import org.skillbill.common.Ausschreibung;
import org.skillbill.common.AusschreibungSkill;
import org.skillbill.common.Skill;
import org.skillbill.dao.AusschreibungDao;
import org.skillbill.dao.AusschreibungSkillDao;
import org.skillbill.dao.SkillDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchService {
	
	@Autowired
	private SkillDao skillDao;
	
	@Autowired
	private AusschreibungSkillDao ausschreibungSkillDao;
	
	@Autowired
	private AusschreibungDao ausschreibungDao;

	public void sucheMitarbeiterMatchesZuSkill() {

	}

	public void sucheMatchesZuAusschreibung() {

	}

	public void sucheMatchesZuMitarbeiter() {

	}

	public List<Ausschreibung> sucheAusschreibungMatchesZuSkill(String skillname) {
		Skill skill = skillDao.findByName(skillname);
		List<AusschreibungSkill> listAusschreibungSkill = ausschreibungSkillDao.findBySkillID(skill.getId());
		
		List<Ausschreibung> listAusschreibungen = new ArrayList<Ausschreibung>();
		
		for (AusschreibungSkill aussk : listAusschreibungSkill) {
			try {
				listAusschreibungen.add(ausschreibungDao.findById(aussk.getAusschreibungid()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return listAusschreibungen;
		
	}

	public SkillDao getSkillDao() {
		return skillDao;
	}

	public void setSkillDao(SkillDao skillDao) {
		this.skillDao = skillDao;
	}

	public AusschreibungSkillDao getAusschreibungSkillDao() {
		return ausschreibungSkillDao;
	}

	public void setAusschreibungSkillDao(AusschreibungSkillDao ausschreibungSkillDao) {
		this.ausschreibungSkillDao = ausschreibungSkillDao;
	}

}
