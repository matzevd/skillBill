package org.skillbill.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.skillbill.common.Mitarbeiter;
import org.skillbill.common.MitarbeiterSkill;
import org.skillbill.common.Skill;
import org.skillbill.dao.MitarbeiterDao;
import org.skillbill.dao.MitarbeiterSkillDao;
import org.skillbill.dao.SkillDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
/**
 * Diese Klasse ist eine Serviceklasse. Sie unterstützt die Kommunikation zwischen Model und Controller.
 * Sie dient dazu, um Businesslogik nicht in die Daos packen zu müssen und somit in den Daos hauptsächlich die JPQLS erledigt werden könnnen.
 * Die weitere Verarbeitung der Ergebnisse findet in dieser Serviceklasse statt
 */
public class MitarbeiterService {

	@Autowired
	private MitarbeiterDao mitarbeiterdao;

	@Autowired
	private MitarbeiterSkillDao mitarbeiterskilldao;

	@Autowired
	private SkillDao skilldao;

	@Transactional
	public void speichernMitarbeiter(Mitarbeiter mitarbeiter,
			List<Skill> listSkill) throws Exception {

		mitarbeiter = mitarbeiterdao.persist(mitarbeiter);
		for (Skill skill : listSkill) {
			MitarbeiterSkill ms = new MitarbeiterSkill();
			ms.setMitarbeiterid(mitarbeiter.getId());
			ms.setSkillid(skill.getId());
			mitarbeiterskilldao.persist(ms);

		}

		Logger.getLogger(this.getClass().getName())
				.log(Level.INFO,
						"Class: "
								+ this.getClass()
								+ "/ Operation  SpeichernMitarbeiter  war erfolgreich ");

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

	public List<Skill> sucheSkillsZuMitarbeiter(Long mitarbeiterid)
			throws Exception {
		List<Skill> skillsVonMitarbeiter = new ArrayList<Skill>();

		List<MitarbeiterSkill> listMitarbeiterSkillsvonMitarbeiter = mitarbeiterskilldao
				.findByMitarbeiterId(mitarbeiterid);

		for (MitarbeiterSkill mitarbeiterSkill : listMitarbeiterSkillsvonMitarbeiter) {
			skillsVonMitarbeiter.add(skilldao.findById(mitarbeiterSkill
					.getSkillid()));
		}

		return skillsVonMitarbeiter;
	}

	@Transactional
	public void updateMitarbeiter(Mitarbeiter mitarbeiterSelected,
			List<Skill> listSelectedSkills) throws Exception {

		mitarbeiterdao.merge(mitarbeiterSelected);
		List<MitarbeiterSkill> listaktuelleSkills = mitarbeiterskilldao
				.findByMitarbeiterId(mitarbeiterSelected.getId());
		List<Skill> listFehltNochInDB = ermittleFehlendeSkills(listSelectedSkills, listaktuelleSkills);
		List<MitarbeiterSkill> listZuLoeschen = ermittleZuLoeschendeSkills(listSelectedSkills, listaktuelleSkills);

		for (Skill skill : listFehltNochInDB) {
			MitarbeiterSkill ms = new MitarbeiterSkill();
			ms.setMitarbeiterid(mitarbeiterSelected.getId());
			ms.setSkillid(skill.getId());
			mitarbeiterskilldao.persist(ms);

		}
		
		for  (MitarbeiterSkill mitSk : listZuLoeschen){
			mitarbeiterskilldao.remove(mitSk.getId());
		}

	}

	private List<Skill> ermittleFehlendeSkills(List<Skill> listSelectedSkills,
			List<MitarbeiterSkill> listaktuelleSkills) {
		List<Skill> listFehltNochInDB = new ArrayList<Skill>();

		for (Skill skill : listSelectedSkills) {
			boolean bereitsvorhanden = false;
			for (MitarbeiterSkill mitarbeiterSkill : listaktuelleSkills) {

				if (mitarbeiterSkill.getSkillid() == skill.getId()) {
					bereitsvorhanden = true;
				}

			}
			if (!bereitsvorhanden) {
				listFehltNochInDB.add(skill);
			}

		}
		return listFehltNochInDB;
	}
	
	
	private List<MitarbeiterSkill> ermittleZuLoeschendeSkills(List<Skill> listSelectedSkills,
			List<MitarbeiterSkill> listaktuelleSkills) {
		List<MitarbeiterSkill> listZuLoeschende = new ArrayList<MitarbeiterSkill>();

		
			for (MitarbeiterSkill mitarbeiterSkill : listaktuelleSkills) {
				boolean zuLoeschen = true;

				for (Skill skill : listSelectedSkills) {

				if (mitarbeiterSkill.getSkillid() == skill.getId()) {
					zuLoeschen = false;
				}

			}
			if (zuLoeschen) {
				listZuLoeschende.add(mitarbeiterSkill);
			}

		}
		return listZuLoeschende;
	}

}
