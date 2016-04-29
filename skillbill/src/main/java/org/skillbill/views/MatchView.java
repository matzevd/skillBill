package org.skillbill.views;


import java.util.ArrayList;
import java.util.List;

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
import org.skillbill.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;




@Component(value = "matchView")
@ViewScoped
public class MatchView {

	private String suchobjekt = null;
	private String skillname = null;

	private List<Ausschreibung> listeGefundenerAuschreibungenZuSkill;

	@Autowired
	private MatchService matchService;

	public void sucheStarten() {

		switch (suchobjekt) {
		case "Skill":
			listeGefundenerAuschreibungenZuSkill = matchService
					.sucheAusschreibungMatchesZuSkill(skillname);
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
		Logger.getLogger(this.getClass().getName())
				.log(Level.INFO,
						"Class: "
								+ this.getClass()
								+ "/ Operation  sucheStarten  war erfolgreich ");

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

}
