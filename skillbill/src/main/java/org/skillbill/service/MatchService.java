package org.skillbill.service;

import java.util.ArrayList;
import java.util.List;

import org.skillbill.common.Ausschreibung;
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
public class MatchService {

	@Autowired
	private AusschreibungDao ausschreibungDao;

	@Autowired
	private MitarbeiterDao mitarbeiterDao;

	@Autowired
	private SkillDao skilldao;

	@Autowired
	private MitarbeiterSkillDao mitarbeiterSkillDao;

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
		List<MatchDTO> listMatch = new ArrayList<MatchDTO>();
		List<Mitarbeiter> listMitarbeiterZuSkill = new ArrayList<Mitarbeiter>();
		List<Long> listeSkillids = new ArrayList<Long>();

		for (Skill sk : skilliste) {
			listeSkillids.add(sk.getId());
		}

		 listMitarbeiterZuSkill = mitarbeiterDao
				.findeMitarbeiterMitSkills(listeSkillids);
		 
		 for (Mitarbeiter mitarbeiter : listMitarbeiterZuSkill) {
			 MatchDTO matchDto = new MatchDTO();
			 matchDto.setMitarbeiter(mitarbeiter);
			 List<MitarbeiterSkill> listfuerAnzahl = mitarbeiterSkillDao.findByMitarbeiterIdandSkillids(mitarbeiter.getId(),listeSkillids);
			 double anzahlGesuchterSkills = listeSkillids.size();
			 double anzahlGefunderSkills = listfuerAnzahl.size();
			 double prozentsatz = (double) (anzahlGefunderSkills / anzahlGesuchterSkills);
			 matchDto.setMatchProzent(prozentsatz*100);
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
