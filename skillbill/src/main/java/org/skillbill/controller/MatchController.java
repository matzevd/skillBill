package org.skillbill.controller;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.skillbill.common.MatchDTO;
import org.skillbill.common.Mitarbeiter;
import org.skillbill.common.MitarbeiterSkill;
import org.skillbill.common.Skill;
import org.skillbill.converter.SkillConverter;
import org.skillbill.dao.MitarbeiterSkillDao;
import org.skillbill.dao.SkillDao;
import org.skillbill.enums.GeschlechtEnum;
import org.skillbill.enums.StandortEnum;
import org.skillbill.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "matchView")
@ViewScoped
public class MatchController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3883251696913908345L;
	private String suchobjekt = null;
	private String skillname = null;
	private boolean wurdeGesucht = false;

	private List<MatchDTO> listeGefundenerAuschreibungenZuSkill;
	private List<MatchDTO> listeGefundenerMitarbeiterZuSkill;

	private List<Skill> listAllSkills;
	private List<Skill> listSelectedSkills;
	private List<Skill> listSkillsZuMitarbeiter;

	private MatchDTO selectedMitarbeiterMatchDTO;

	private SkillConverter skillconverter;

	@Autowired
	private MitarbeiterSkillDao mitarbeiterSkillDao;

	@Autowired
	private SkillDao skillDao;

	@Autowired
	private MatchService matchService;

	public void preRenderView() {
		// Aufgrund eines Bugs bei Primefaces muss hier so eine Session erzeugt
		// werden
		@SuppressWarnings("unused")
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		if (!wurdeGesucht) {
			erzeugeSkillliste();
		}

	}

	@PostConstruct
	public void init() {

		erzeugeSkillliste();
	}

	private void erzeugeSkillliste() {
		if (this.listSelectedSkills == null) {
			this.listSelectedSkills = new ArrayList<Skill>();
		}
		this.listAllSkills = new ArrayList<Skill>();

		try {
			this.listAllSkills.addAll(skillDao.findAll());
			skillconverter = new SkillConverter(listAllSkills);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sucheStarten() {
		wurdeGesucht = true;

		switch (suchobjekt) {
		case "Skill":

			listeGefundenerMitarbeiterZuSkill = matchService
					.sucheMitarbeiterMatchesZuSkills(listSelectedSkills);
			listeGefundenerAuschreibungenZuSkill = matchService
					.sucheAusschreibungMatchesZuSkills(listSelectedSkills);

			break;

		case "Ausschreibung":

			break;

		case "Mitareiter":
			matchService.sucheMatchesZuMitarbeiter();

			break;

		default:
			break;

		}

		Collections.sort(listeGefundenerMitarbeiterZuSkill,
				new Comparator<MatchDTO>() {
					@Override
					public int compare(MatchDTO o1, MatchDTO o2) {
						return o2.getMatchProzent().compareTo(
								o1.getMatchProzent());
					}
				});
		Logger.getLogger(this.getClass().getName()).log(
				Level.INFO,
				"Class: " + this.getClass()
						+ "/ Operation  sucheStarten  war erfolgreich ");

	}

	public void postProcessXLS(Object document) {
		HSSFWorkbook wb = (HSSFWorkbook) document;
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow header = sheet.getRow(0);

		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
			HSSFCell cell = header.getCell(i);

			cell.setCellStyle(cellStyle);
		}
	}

	public String baueFilename() {
		String filename = null;
		switch (suchobjekt) {
		case "Skill":
			filename = "MatchZuSkill_" + skillname;
			break;

		case "Ausschreibung":

			break;

		case "Mitareiter":

			break;

		default:
			filename = "MatchOhneFilterung";
			break;

		}
		return filename;
	}



	public void reset() {
		suchobjekt = null;
		skillname = null;
		wurdeGesucht = false;
		listeGefundenerAuschreibungenZuSkill = new ArrayList<MatchDTO>();
		listeGefundenerMitarbeiterZuSkill = new ArrayList<MatchDTO>();
		listSelectedSkills = new ArrayList<Skill>();
	}

	public String gebeUrl() {
		reset();
		return "/views/match/match.xhtml?faces-redirect=true";

	}

	public String gebeNurZweiNachkommastellen(double matchprozent) {
		DecimalFormat f = new DecimalFormat("#0.00");
		return f.format(matchprozent);
	}

	public List<Skill> sucheSkillsZuMatchDTOMitarbeiter(Mitarbeiter mitarbeiter) {

		if (mitarbeiter != null && mitarbeiter.getId() != null && (getListSkillsZuMitarbeiter() == null || getListSkillsZuMitarbeiter().isEmpty())) {
			List<MitarbeiterSkill> listMS = mitarbeiterSkillDao
					.findByMitarbeiterId(mitarbeiter.getId());
			for (MitarbeiterSkill ms : listMS) {
				try {
					listSkillsZuMitarbeiter.add(skillDao.findById(ms
							.getSkillid()));
				} catch (Exception e) {
					Logger.getLogger(this.getClass().getName())
							.log(Level.ERROR,
									"Operation sucheSkillsZuMatchDTOMitarbeiter Error ",
									e);
					FacesMessage msg = null;
					msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Fehlermeldung", "Es ist ein Fehler aufgetreten: "
									+ e.getMessage()
									+ "; sucheSkillsZuMatchDTOMitarbeiter");
					FacesContext.getCurrentInstance().addMessage(null, msg);

				}
			}
		}

		return listSkillsZuMitarbeiter;
	}

	// -------------------------------------------GETTER und SETTER - MEthoden
	// ------------------------------------------------
	public boolean isWurdeGesucht() {
		return wurdeGesucht;
	}

	public void setWurdeGesucht(boolean wurdeGesucht) {
		this.wurdeGesucht = wurdeGesucht;
	}

	public String getSuchobjekt() {
		return suchobjekt;
	}

	public void setSuchobjekt(String suchobjekt) {
		this.suchobjekt = suchobjekt;
	}

	public String getSkillname() {
		return skillname;
	}

	public void setSkillname(String skillname) {
		this.skillname = skillname;
	}

	public List<MatchDTO> getListeGefundenerAuschreibungenZuSkill() {
		if (listeGefundenerAuschreibungenZuSkill == null) {
			listeGefundenerAuschreibungenZuSkill = new ArrayList<MatchDTO>();
		}
		return listeGefundenerAuschreibungenZuSkill;
	}

	public void setListeGefundenerAuschreibungenZuSkill(
			List<MatchDTO> listeGefundenerAuschreibungenZuSkill) {
		this.listeGefundenerAuschreibungenZuSkill = listeGefundenerAuschreibungenZuSkill;
	}

	public MatchService getMatchService() {
		return matchService;
	}

	public void setMatchService(MatchService matchService) {
		this.matchService = matchService;
	}

	public List<Skill> getListAllSkills() {
		if (listAllSkills == null) {
			listAllSkills = new ArrayList<Skill>();
		}
		return listAllSkills;
	}

	public void setListAllSkills(List<Skill> listAllSkills) {
		this.listAllSkills = listAllSkills;
	}

	public List<Skill> getListSelectedSkills() {
		if (listSelectedSkills == null) {
			listSelectedSkills = new ArrayList<Skill>();
		}
		return listSelectedSkills;
	}

	public void setListSelectedSkills(List<Skill> listSelectedSkills) {
		this.listSelectedSkills = listSelectedSkills;
	}

	public SkillConverter getSkillconverter() {
		return skillconverter;
	}

	public void setSkillconverter(SkillConverter skillconverter) {
		this.skillconverter = skillconverter;
	}

	public List<MatchDTO> getListeGefundenerMitarbeiterZuSkill() {
		return listeGefundenerMitarbeiterZuSkill;
	}

	public void setListeGefundenerMitarbeiterZuSkill(
			List<MatchDTO> listeGefundenerMitarbeiterZuSkill) {
		this.listeGefundenerMitarbeiterZuSkill = listeGefundenerMitarbeiterZuSkill;
	}

	public MatchDTO getSelectedMitarbeiterMatchDTO() {
		if (selectedMitarbeiterMatchDTO == null) {
			selectedMitarbeiterMatchDTO = new MatchDTO();
		}
		return selectedMitarbeiterMatchDTO;
	}

	public void setSelectedMitarbeiterMatchDTO(
			MatchDTO selectedMitarbeiterMatchDTO) {
		this.selectedMitarbeiterMatchDTO = selectedMitarbeiterMatchDTO;
	}

	public List<StandortEnum> getStandorte() {
		List<StandortEnum> somethingList = new ArrayList<StandortEnum>(
				EnumSet.allOf(StandortEnum.class));
		return somethingList;
	}

	public List<GeschlechtEnum> getGeschlechte() {
		List<GeschlechtEnum> geschlechtlist = new ArrayList<GeschlechtEnum>(
				EnumSet.allOf(GeschlechtEnum.class));
		return geschlechtlist;

	}

	public List<Skill> getListSkillsZuMitarbeiter() {
		if (listSkillsZuMitarbeiter == null){
			listSkillsZuMitarbeiter = new ArrayList<Skill>();
		}
		return listSkillsZuMitarbeiter;
	}

	public void setListSkillsZuMitarbeiter(List<Skill> listSkillsZuMitarbeiter) {
		this.listSkillsZuMitarbeiter = listSkillsZuMitarbeiter;
	}

}
