package org.skillbill.views;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.skillbill.common.Ausschreibung;
import org.skillbill.common.MatchDTO;
import org.skillbill.common.Mitarbeiter;
import org.skillbill.common.Skill;
import org.skillbill.converter.SkillConverter;
import org.skillbill.dao.SkillDao;
import org.skillbill.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;




@Component(value = "matchView")
@ViewScoped
public class MatchView {

	private String suchobjekt = null;
	private String skillname = null;
	private  boolean wurdeGesucht = false;

	private List<Ausschreibung> listeGefundenerAuschreibungenZuSkill;
	private List<MatchDTO> listeGefundenerMitarbeiterZuSkill;
	
	private List<Skill> listAllSkills;
	private List<Skill> listSelectedSkills;
	
	private SkillConverter skillconverter;
	
	@Autowired
	private SkillDao skillDao;
	



	@Autowired
	private MatchService matchService;
	
	
	
	@PostConstruct
    public void init() {
		this.listSelectedSkills = new ArrayList<Skill>();
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
			
			listeGefundenerMitarbeiterZuSkill = matchService.sucheMitarbeiterMatchesZuSkills(listSelectedSkills);
			break;

		case "Ausschreibung":
			matchService.sucheMatchesZuAusschreibung();

			break;

		case "Mitareiter":
			matchService.sucheMatchesZuMitarbeiter();

			break;

		default:
			break;

		}
		
		
		Collections.sort(listeGefundenerMitarbeiterZuSkill, new Comparator<MatchDTO>() {
		    @Override
		    public int compare(MatchDTO o1, MatchDTO o2) {
		        return o2.getMatchProzent().compareTo(o1.getMatchProzent());
		    }
		});
		Logger.getLogger(this.getClass().getName())
				.log(Level.INFO,
						"Class: "
								+ this.getClass()
								+ "/ Operation  sucheStarten  war erfolgreich ");

	}


	

	
	  public void postProcessXLS(Object document) {
	        HSSFWorkbook wb = (HSSFWorkbook) document;
	        HSSFSheet sheet = wb.getSheetAt(0);
	        HSSFRow header = sheet.getRow(0);
	         
	        HSSFCellStyle cellStyle = wb.createCellStyle();  
	        cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
	        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	         
	        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
	            HSSFCell cell = header.getCell(i);
	             
	            cell.setCellStyle(cellStyle);
	        }
	    }
	  
	  public String baueFilename(){
		  String filename = null;
			switch (suchobjekt) {
			case "Skill":
				filename = "MatchZuSkill_" +skillname;
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

	  
	//-------------------------------------------GETTER und SETTER - MEthoden ------------------------------------------------
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

	public List<Ausschreibung> getListeGefundenerAuschreibungenZuSkill() {
		if (listeGefundenerAuschreibungenZuSkill == null) {
			listeGefundenerAuschreibungenZuSkill = new ArrayList<Ausschreibung>();
		}
		return listeGefundenerAuschreibungenZuSkill;
	}

	public void setListeGefundenerAuschreibungenZuSkill(
			List<Ausschreibung> listeGefundenerAuschreibungenZuSkill) {
		this.listeGefundenerAuschreibungenZuSkill = listeGefundenerAuschreibungenZuSkill;
	}

	public MatchService getMatchService() {
		return matchService;
	}

	public void setMatchService(MatchService matchService) {
		this.matchService = matchService;
	}

	public List<Skill> getListAllSkills() {
		if (listAllSkills == null){
			listAllSkills = new ArrayList<Skill>();
		}
		return listAllSkills;
	}

	public void setListAllSkills(List<Skill> listAllSkills) {
		this.listAllSkills = listAllSkills;
	}

	public List<Skill> getListSelectedSkills() {
		if (listSelectedSkills == null){
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



}
