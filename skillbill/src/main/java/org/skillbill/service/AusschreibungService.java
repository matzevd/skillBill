package org.skillbill.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.skillbill.common.Ausschreibung;
import org.skillbill.common.AusschreibungSkill;
import org.skillbill.common.Mitarbeiter;
import org.skillbill.common.MitarbeiterSkill;
import org.skillbill.common.Skill;
import org.skillbill.dao.AusschreibungDao;
import org.skillbill.dao.AusschreibungSkillDao;
import org.skillbill.dao.MitarbeiterDao;
import org.skillbill.dao.MitarbeiterSkillDao;
import org.skillbill.dao.SkillDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AusschreibungService {
	
	
	
	@Autowired
	private AusschreibungDao ausschreibungDao;
	
	@Autowired
	private AusschreibungSkillDao ausschreibungSkillDao;
	
	@Autowired
	private SkillDao skilldao;
	
	
	@Transactional
	public void speichernAusschreibung(Ausschreibung ausschreibung, List<Skill> listSkill) throws Exception{
		
		ausschreibung = ausschreibungDao.persist(ausschreibung);
		for (Skill skill : listSkill) {
			AusschreibungSkill as = new AusschreibungSkill();
			as.setAusschreibungid(ausschreibung.getId());
			as.setSkillid(skill.getId());
			ausschreibungSkillDao.persist(as);
			
			
		}
		
		Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Class: " + this.getClass() +"/ Operation  SpeichernMitarbeiter  war erfolgreich ");
		
	}






	public List<Skill> sucheSkillsZuAusschreibung(Long ausschreibungID) throws Exception {
		List<Skill>	skillsVonAusschreibung = new ArrayList<Skill>(); 
		
		List<AusschreibungSkill> listAusschreibungSkillsvonAusschreibung = ausschreibungSkillDao.findByAusschreibungID(ausschreibungID);
		
		
		for (AusschreibungSkill ausschreibungSkill : listAusschreibungSkillsvonAusschreibung) {
			skillsVonAusschreibung.add(skilldao.findById(ausschreibungSkill.getSkillid()));
		}
		
		
		return skillsVonAusschreibung;
	}
	
	

}
