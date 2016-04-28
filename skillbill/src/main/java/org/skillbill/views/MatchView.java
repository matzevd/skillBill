package org.skillbill.views;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.skillbill.common.Ausschreibung;
import org.skillbill.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;


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
	
	public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        pdf.open();
        pdf.setPageSize(PageSize.A4);
 
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String logo = externalContext.getRealPath("") + File.separator + "resources" + File.separator + "demo" + File.separator + "images" + File.separator + "prime_logo.png";
         
        pdf.add(Image.getInstance(logo));
    }

}
