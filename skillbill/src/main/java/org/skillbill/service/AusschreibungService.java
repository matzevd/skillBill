package org.skillbill.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.skillbill.common.Ausschreibung;
import org.skillbill.common.AusschreibungSkill;
import org.skillbill.common.Skill;
import org.skillbill.dao.AusschreibungDao;
import org.skillbill.dao.AusschreibungSkillDao;
import org.skillbill.dao.SkillDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
/**
 * Diese Klasse ist eine Serviceklasse. Sie unterstützt die Kommunikation zwischen Model und Controller.
 * Sie dient dazu, um Businesslogik nicht in die Daos packen zu müssen und somit in den Daos hauptsächlich die JPQLS erledigt werden könnnen.
 * Die weitere Verarbeitung der Ergebnisse findet in dieser Serviceklasse statt
 */
public class AusschreibungService {
	
	
	
	@Autowired
	private AusschreibungDao ausschreibungDao;
	
	@Autowired
	private AusschreibungSkillDao ausschreibungSkillDao;
	
	@Autowired
	private SkillDao skilldao;
	
	
	/**
	 * Diese Methode speichert die erstellte Ausschreibung auf der Oberfläche bzw. gibt die Daten an das Dao weiter
	 * Weiterhin werden die Skills zu dieser Ausschreibung gespeichert
	 * @param ausschreibung
	 * @param listSkill
	 * @throws Exception
	 */
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





/**
 * Diese Methode ermittelt zu einer Ausschreibung anhand der AusschreibungID die dazugehörigen Skills
 * @param ausschreibungID
 * @return gibt eine Liste mit Skill-Objektenzurück
 * @throws Exception
 */
	@Transactional
	public List<Skill> sucheSkillsZuAusschreibung(Long ausschreibungID) throws Exception {
		List<Skill>	skillsVonAusschreibung = new ArrayList<Skill>(); 
		
		List<AusschreibungSkill> listAusschreibungSkillsvonAusschreibung = ausschreibungSkillDao.findByAusschreibungID(ausschreibungID);
		
		
		for (AusschreibungSkill ausschreibungSkill : listAusschreibungSkillsvonAusschreibung) {
			skillsVonAusschreibung.add(skilldao.findById(ausschreibungSkill.getSkillid()));
		}
		
		
		return skillsVonAusschreibung;
	}
	
	

}
