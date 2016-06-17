package org.skillbill.controller;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.skillbill.common.Mitarbeiter;
import org.skillbill.common.Skill;
import org.skillbill.converter.SkillConverter;
import org.skillbill.dao.MitarbeiterDao;
import org.skillbill.dao.SkillDao;
import org.skillbill.enums.GeschlechtEnum;
import org.skillbill.enums.StandortEnum;
import org.skillbill.service.MitarbeiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;



@Component(value="mitarbeiterView")
@Scope(value="session")
public class MitarbeiterController implements Serializable {
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Autowired
	private  MitarbeiterDao mitarbeiterDao;
	
	@Autowired
	private MitarbeiterService mitarbeiterService;
	
	@Autowired
	private SkillDao skillDao;
	
	private SkillConverter skillconverter;
	
	private boolean isUsecaseNeuerMitarbeiter = false;
	private boolean neuAufSeite = true;
	

	private Mitarbeiter mitarbeiter;
	private Mitarbeiter mitarbeiterSelected;
	private List<Mitarbeiter> list;	
	private List<Mitarbeiter> listSelected;
	
	private List<Skill> listAllSkills;
	private List<Skill> listSelectedSkills;
	
	 public void preRenderView() {
		 //Aufgrund eines Bugs bei Primefaces muss hier so eine Session erzeugt werden
		 if (neuAufSeite){
	      @SuppressWarnings("unused")
		HttpSession session = ( HttpSession ) FacesContext.getCurrentInstance().getExternalContext().getSession( true );
	    erzeugeSkillliste();
	    neuAufSeite = false;
		 }
	  
	   }

	
	private void erzeugeSkillliste() {
			this.listAllSkills = new ArrayList<Skill>();
		try {
			this.listAllSkills.addAll(skillDao.findAll());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (!isUsecaseNeuerMitarbeiter){
		List<Skill> templistSelectedSkills = new ArrayList<Skill>();
		templistSelectedSkills.addAll(getListSelectedSkills());
		
		getListSelectedSkills().clear();
		for (Skill allskill : listAllSkills) {
			for (Skill tempskill : templistSelectedSkills) {
				if (allskill.getId() == tempskill.getId()){
					listSelectedSkills.add(allskill);
				}
			}
		}
		}
		else {
			if (neuAufSeite){
			listSelectedSkills = new ArrayList<Skill>();
			neuAufSeite = false;
			}
		}
		skillconverter = new SkillConverter(listAllSkills);	
	}


	@PostConstruct
    public void init() {
		refreshList();
    }



	public void refreshList() {
		this.mitarbeiter = new Mitarbeiter();
		this.mitarbeiterSelected = new Mitarbeiter();

		this.list = new ArrayList<Mitarbeiter>();
		this.listSelected = new ArrayList<Mitarbeiter>();
		
		erzeugeSkillliste();
		
		try {
			this.list.addAll(mitarbeiterDao.findAll());
			this.listSelected.addAll(this.list);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	public void save() {
		try {
		    
			mitarbeiterService.speichernMitarbeiter(this.mitarbeiter, this.listSelectedSkills);
			this.listSelectedSkills.clear();
			
			notificationSuccess("Mitarbeiter " + this.mitarbeiter.getVorname()+ " " + this.mitarbeiter.getNachname() + " erfolgreich gespeichert!");
			refreshList();
		} catch (Exception e) {
			notificationError(e,"Mitarbeiter " + this.mitarbeiter.getVorname()+ " " + this.mitarbeiter.getNachname() + "  nicht erfolgreich gespeichert! Fehler: " + e.getMessage());

			e.printStackTrace();
		}
	}

	public void update() {
		try {
			mitarbeiterService.updateMitarbeiter(this.mitarbeiterSelected,this.listSelectedSkills);
			
			notificationSuccess("Mitarbeiter " + this.mitarbeiterSelected.getVorname()+ " " + this.mitarbeiterSelected.getNachname() + " erfolgreich bearbeitet!");
			refreshList();
		} catch (Exception e) {
			notificationError(e,"Mitarbeiter " + this.mitarbeiterSelected.getVorname()+ " " + this.mitarbeiterSelected.getNachname() + "  nicht erfolgreich bearbeitet! Fehler: " + e.getMessage());
		}
	}

	public void delete() {
		try {
			list.remove(this.mitarbeiterSelected);
			mitarbeiterDao.remove(this.mitarbeiterSelected.getId());
		
			notificationSuccess("Mitarbeiter " + this.mitarbeiterSelected.getVorname()+ " " + this.mitarbeiterSelected.getNachname() + " erfolgreich gelöscht!");
			refreshList();
			
			 ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			    ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
		} catch (Exception e) {
			notificationError(e,"Mitarbeiter " + this.mitarbeiterSelected.getVorname()+ " " + this.mitarbeiterSelected.getNachname() + "  nicht erfolgreich gelöscht! Fehler: " + e.getMessage());
		}
	}

	public void onCancel(RowEditEvent event) {
		refreshList();
	}

	
	public void reset() {
		refreshList();
        RequestContext.getCurrentInstance().reset("form1:panel");  
	}

	
	public void notificationSuccess(String operation) {
		Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Operation "+operation+" success");
		FacesMessage msg = null;  
		msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Information:", operation); 
		FacesContext.getCurrentInstance().addMessage(null, msg);  
	}


	public void notificationError(Exception e, String operation) {
		Logger.getLogger(this.getClass().getName()).log(Level.ERROR, "Operation "+operation+" Error ",e);
		FacesMessage msg = null;  
		msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Fehlermeldung", "Es ist ein Fehler aufgetreten: " + e.getMessage() + "; " + operation);  
		FacesContext.getCurrentInstance().addMessage(null, msg);  
	}
	
	public void ermittleSkillsZuMitarbeiter(){
		try {
			
			getListSelectedSkills().clear();
			List<Skill> templistSelectedSkills = mitarbeiterService.sucheSkillsZuMitarbeiter(mitarbeiterSelected.getId());
			for (Skill allskill : listAllSkills) {
				for (Skill tempskill : templistSelectedSkills) {
					if (allskill.getId() == tempskill.getId()){
						listSelectedSkills.add(allskill);
					}
				}
			}
		
			skillconverter = new SkillConverter(listAllSkills);
			
		} catch (Exception e) {
			Logger.getLogger(this.getClass().getName()).log(Level.ERROR, "Operation ermittleSkillsZuMitarbeiter Error ",e);
			FacesMessage msg = null;  
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Fehlermeldung", "Es ist ein Fehler aufgetreten: " + e.getMessage() + "; ErmittleSkillsZuMitarbeiter");  
			FacesContext.getCurrentInstance().addMessage(null, msg);  

		}
	}
	
	/**
	 * ist nötig, damit das mit dem anzeigen der Skilllisten funktioniert
	 * das Problem ist, das der SkillConverter den Vergleich der Objektids
	 *vornimmt und es somit vorkommen kann, dass er bereits gespeicherte Skills beim Mitarbeiter
	 * in der SelectCheckbox nicht anzeigt.
	 * 
	 * Hier nötigt für neuen Mitarbeiter hinzufügen, damit doch alles zurückgesetzt wird
	 * @return URL für Mitarbeiter hinzufügen
	 */
	
	
	public String istNeu(){
		isUsecaseNeuerMitarbeiter = true;
		neuAufSeite = true;
		return "/views/mitarbeiter/mitarbeiter.xhtml?faces-redirect=true";
	}
	
	public String istUebersicht(){
		isUsecaseNeuerMitarbeiter = false;
		neuAufSeite = true;
		return "/views/mitarbeiter/mitarbeiteruebersicht.xhtml?faces-redirect=true";
	}

	public List<Mitarbeiter> getList() {
		if(list == null){
			list = new ArrayList<Mitarbeiter>();
		}
		return list;
	}

	public void setList(List<Mitarbeiter> list) {
		this.list = list;
	}







	public Mitarbeiter getMitarbeiter() {
		return mitarbeiter;
	}



	public void setMitarbeiter(Mitarbeiter mitarbeiter) {
		this.mitarbeiter = mitarbeiter;
	}





	public List<StandortEnum> getStandorte() {
		List<StandortEnum> somethingList =
                new ArrayList<StandortEnum>(EnumSet.allOf(StandortEnum.class));
		return somethingList;
	}

	public List<GeschlechtEnum> getGeschlechte() {
		List<GeschlechtEnum> geschlechtlist =
                new ArrayList<GeschlechtEnum>(EnumSet.allOf(GeschlechtEnum.class));
		return geschlechtlist;
	
	}



	public Mitarbeiter getMitarbeiterSelected() {
		return mitarbeiterSelected;
	}



	public void setMitarbeiterSelected(Mitarbeiter mitarbeiterSelected) {
		this.mitarbeiterSelected = mitarbeiterSelected;
	}



	public List<Mitarbeiter> getListSelected() {
	
		return listSelected;
	}



	public void setListSelected(List<Mitarbeiter> listSelected) {
		this.listSelected = listSelected;
	}



	public MitarbeiterDao getMitarbeiterService() {
		return mitarbeiterDao;
	}



	public void setMitarbeiterService(MitarbeiterDao mitarbeiterService) {
		this.mitarbeiterDao = mitarbeiterService;
	}



	public SkillDao getSkillDao() {
		return skillDao;
	}



	public void setSkillDao(SkillDao skillDao) {
		this.skillDao = skillDao;
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



	public List<Skill> getListAllSkills() {
		return listAllSkills;
	}



	public void setListAllSkills(List<Skill> listAllSkills) {
		this.listAllSkills = listAllSkills;
	}



	public SkillConverter getSkillconverter() {
		if (skillconverter == null){
			skillconverter = new SkillConverter(new ArrayList<Skill>());
		}
		return skillconverter;
	}



	public void setSkillconverter(SkillConverter skillconverter) {
		this.skillconverter = skillconverter;
	}


	public boolean isUsecaseNeuerMitarbeiter() {
		return isUsecaseNeuerMitarbeiter;
	}


	public void setUsecaseNeuerMitarbeiter(boolean isUsecaseNeuerMitarbeiter) {
		this.isUsecaseNeuerMitarbeiter = isUsecaseNeuerMitarbeiter;
	}






	




	
	
	
	
}
