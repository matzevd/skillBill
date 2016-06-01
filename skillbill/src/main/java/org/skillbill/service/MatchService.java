package org.skillbill.service;

import java.util.ArrayList;
import java.util.List;

import org.skillbill.common.Ausschreibung;
import org.skillbill.common.AusschreibungSkill;
import org.skillbill.common.MatchDTO;
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
/**
 * Diese Klasse ist eine Serviceklasse. Sie unterstützt die Kommunikation zwischen Model und Controller.
 * Sie dient dazu, um Businesslogik nicht in die Daos packen zu müssen und somit in den Daos hauptsächlich die JPQLS erledigt werden könnnen.
 * Die weitere Verarbeitung der Ergebnisse findet in dieser Serviceklasse statt
 */
public class MatchService {

	@Autowired
	private AusschreibungDao ausschreibungDao;

	@Autowired
	private MitarbeiterDao mitarbeiterDao;

	@Autowired
	private SkillDao skilldao;

	@Autowired
	private MitarbeiterSkillDao mitarbeiterSkillDao;
	
	@Autowired
	private AusschreibungSkillDao ausschreibungSkillDao;

	public void sucheMitarbeiterMatchesZuSkill() {

	}

	public void sucheMatchesZuAusschreibung() {

	}

	public void sucheMatchesZuMitarbeiter() {

	}

	public List<Ausschreibung> sucheAusschreibungMatchesZuSkill(String skillname) {

		List<Ausschreibung> listAusschreibungen = ausschreibungDao
				.findeAusschreibungMitSkillname(skillname);
		return listAusschreibungen;

	}

	public List<MatchDTO> sucheMitarbeiterMatchesZuSkills(
			List<Skill> skilliste) {
		List<Mitarbeiter> listMitarbeiterZuSkill = new ArrayList<Mitarbeiter>();
		List<Long> listeSkillids = new ArrayList<Long>();

		for (Skill sk : skilliste) {
			listeSkillids.add(sk.getId());
		}

		 listMitarbeiterZuSkill = mitarbeiterDao
				.findeMitarbeiterMitSkills(listeSkillids);
		 
		return erzeugeOberflaecheDatenSucheMitarbeiterVonSkill(listMitarbeiterZuSkill,
				listeSkillids, skilliste);
		 

	}
	
	public List<MatchDTO> sucheAusschreibungMatchesZuSkills(
			List<Skill> skilliste) {
		List<Ausschreibung> listAusschreibungZuSkill = new ArrayList<Ausschreibung>();
		List<Long> listeSkillids = new ArrayList<Long>();

		for (Skill sk : skilliste) {
			listeSkillids.add(sk.getId());
		}

		listAusschreibungZuSkill = ausschreibungDao.findeAusschreibungMitSkills(listeSkillids);
		 
		return erzeugeOberflaecheDatenSucheAusschreibungVonSkill(listAusschreibungZuSkill,
				listeSkillids, skilliste);
		 

	}

	private 	List<MatchDTO>  erzeugeOberflaecheDatenSucheMitarbeiterVonSkill(
			List<Mitarbeiter> listMitarbeiterZuSkill, List<Long> listeSkillids, List<Skill> skilliste) {
		List<MatchDTO> listMatch = new ArrayList<MatchDTO>();

		for (Mitarbeiter mitarbeiter : listMitarbeiterZuSkill) {
			 MatchDTO matchDto = new MatchDTO();
			 matchDto.setMitarbeiter(mitarbeiter);
			 List<MitarbeiterSkill> listfuerAnzahl = mitarbeiterSkillDao.findByMitarbeiterIdandSkillids(mitarbeiter.getId(),listeSkillids);
			 double anzahlGesuchterSkills = listeSkillids.size();
			 double anzahlGefunderSkills = listfuerAnzahl.size();
			 double prozentsatz = (double) (anzahlGefunderSkills / anzahlGesuchterSkills);
			 matchDto.setMatchProzent(prozentsatz*100);
			 
			 for (MitarbeiterSkill msk : listfuerAnzahl) {
				 for (Skill sk : skilliste) {
					 if (msk.getSkillid() == sk.getId()){
						 matchDto.getListGefundenerSkills().add(sk);
					 }
					
				}
				
			}
			 listMatch.add(matchDto);
				 }
		return listMatch;
	}
	
	
	private 	List<MatchDTO>  erzeugeOberflaecheDatenSucheAusschreibungVonSkill(
			List<Ausschreibung> listAusschreibungZuSkill, List<Long> listeSkillids, List<Skill> skilliste) {
		List<MatchDTO> listMatch = new ArrayList<MatchDTO>();

		for (Ausschreibung aus : listAusschreibungZuSkill) {
			 MatchDTO matchDto = new MatchDTO();
			 matchDto.setAusschreibung(aus);
			 List<AusschreibungSkill> listfuerAnzahl = ausschreibungSkillDao.findByAusschreibungIdandSkillids(aus.getId(),listeSkillids);
			 double anzahlGesuchterSkills = listeSkillids.size();
			 double anzahlGefunderSkills = listfuerAnzahl.size();
			 double prozentsatz = (double) (anzahlGefunderSkills / anzahlGesuchterSkills);
			 matchDto.setMatchProzent(prozentsatz*100);
			 
			 for (AusschreibungSkill ask : listfuerAnzahl) {
				 for (Skill sk : skilliste) {
					 if (ask.getSkillid() == sk.getId()){
						 matchDto.getListGefundenerSkills().add(sk);
					 }
					
				}
				
			}
			 listMatch.add(matchDto);
				 }
		return listMatch;
	}

	public MitarbeiterDao getMitarbeiterDao() {
		return mitarbeiterDao;
	}

	public void setMitarbeiterDao(MitarbeiterDao mitarbeiterDao) {
		this.mitarbeiterDao = mitarbeiterDao;
	}

}
