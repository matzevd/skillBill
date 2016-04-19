package org.skillbill.service;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.skillbill.common.Mitarbeiter;
import org.skillbill.common.MitarbeiterSkill;
import org.skillbill.common.Skill;
import org.skillbill.dao.MitarbeiterDao;
import org.skillbill.dao.MitarbeiterSkillDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MitarbeiterService {
	
	
	
	@Autowired
	private MitarbeiterDao mitarbeiterdao;
	
	@Autowired
	private MitarbeiterSkillDao mitarbeiterskilldao;
	
	
	@Transactional
	public void speichernMitarbeiter(Mitarbeiter mitarbeiter, List<Skill> listSkill) throws Exception{
		
		mitarbeiter = mitarbeiterdao.persist(mitarbeiter);
		for (Skill skill : listSkill) {
			MitarbeiterSkill ms = new MitarbeiterSkill();
			ms.setMitarbeiterid(mitarbeiter.getId());
			ms.setSkillid(skill.getId());
			mitarbeiterskilldao.persist(ms);
			
			
		}
		
		Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Class: " + this.getClass() +"/ Operation  SpeichernMitarbeiter  war erfolgreich ");
		
	}


	public MitarbeiterDao getMitarbeiterdao() {
		return mitarbeiterdao;
	}


	public void setMitarbeiterdao(MitarbeiterDao mitarbeiterdao) {
		this.mitarbeiterdao = mitarbeiterdao;
	}


	public MitarbeiterSkillDao getMitarbeiterskilldao() {
		return mitarbeiterskilldao;
	}


	public void setMitarbeiterskilldao(MitarbeiterSkillDao mitarbeiterskilldao) {
		this.mitarbeiterskilldao = mitarbeiterskilldao;
	}
	
	

}