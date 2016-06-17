package org.skillbill.controller;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.skillbill.common.Ausschreibung;
import org.skillbill.common.Skill;
import org.skillbill.converter.SkillConverter;
import org.skillbill.dao.AusschreibungDao;
import org.skillbill.dao.SkillDao;
import org.skillbill.service.AusschreibungService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component(value="ausschreibungView")
@ViewScoped
public class AusschreibungController implements Serializable {
	


	/**
	 * Das ist die Oberflächenklassen für ausschreibungView
	 */
	private static final long serialVersionUID = 1L;


	@Autowired
	private transient AusschreibungDao ausschreibungDao;
	
	@Autowired
	private AusschreibungService ausschreibungService;
	
	@Autowired
	private SkillDao skillDao;
	
	private SkillConverter skillconverter;
	

	private Ausschreibung ausschreibung;
	private Ausschreibung ausschreibungSelected;
	private List<Ausschreibung> list;	
	private List<Ausschreibung> listSelected;
	
	private List<Skill> listAllSkills;
	private List<Skill> listSelectedSkills;

	
	@PostConstruct
    public void init() {
		refreshList();
    }
	
	
	
	 public void preRenderView() {
		 //Aufgrund eines Bugs bei Primefaces muss hier so eine Session erzeugt werden
	      @SuppressWarnings("unused")
		HttpSession session = ( HttpSession ) FacesContext.getCurrentInstance().getExternalContext().getSession( true );
	    erzeugeSkillliste();
	  
	   }
	 
		private void erzeugeSkillliste() {
			
			this.listSelectedSkills = new ArrayList<Skill>();
			this.listAllSkills = new ArrayList<Skill>();
			try {
				this.listAllSkills.addAll(skillDao.findAll());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			skillconverter = new SkillConverter(listAllSkills);	
		}




	public void refreshList() {
		this.ausschreibung = new Ausschreibung();
		this.ausschreibungSelected = new Ausschreibung();

		this.list = new ArrayList<Ausschreibung>();
		this.listSelected = new ArrayList<Ausschreibung>();
		
		erzeugeSkillliste();
		
		try {
			this.list.addAll(ausschreibungDao.findAll());
			this.listSelected.addAll(this.list);
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	public void save() {
		try {
		    
			ausschreibungService.speichernAusschreibung(ausschreibung, listSelectedSkills);
			
			notificationSuccess("Ausschreibung (Nummer:" + this.ausschreibung.getId()+ ") " + this.ausschreibung.getKurzbezeichnung() + " erfolgreich gespeichert!");
			refreshList();
		} catch (Exception e) {
			notificationError(e,"Ausschreibung (Nummer:" + this.ausschreibung.getId()+ ") " + this.ausschreibung.getKurzbezeichnung() + "  nicht erfolgreich gespeichert! Fehler: " + e.getMessage());

			e.printStackTrace();
		}
	}

	public void update() {
		try {
			ausschreibungDao.merge(this.ausschreibungSelected);
			
			notificationSuccess("Ausschreibung (Nummer:" + this.ausschreibungSelected.getId()+ ") " + this.ausschreibungSelected.getKurzbezeichnung() + " erfolgreich bearbeitet!");
			refreshList();
		} catch (Exception e) {
			notificationError(e,"Ausschreibung (Nummer:" + this.ausschreibungSelected.getId()+ ") " + this.ausschreibungSelected.getKurzbezeichnung() + "  nicht erfolgreich bearbeitet! Fehler: " + e.getMessage());
		}
	}

	public void delete() {
		try {
			ausschreibungDao.remove(this.ausschreibungSelected.getId());
		
			notificationSuccess("Ausschreibung (Nummer:" + this.ausschreibungSelected.getId()+ ") " + this.ausschreibungSelected.getKurzbezeichnung() + " erfolgreich gelöscht!");
			refreshList();
		} catch (Exception e) {
			notificationError(e,"Ausschreibung (Nummer:" + this.ausschreibungSelected.getId()+ ") " + this.ausschreibungSelected.getKurzbezeichnung()  + "  nicht erfolgreich gelöscht! Fehler: " + e.getMessage());
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
	
	public void ermittleSkillsZuAusschreibung(){
		try {
			listSelectedSkills = ausschreibungService.sucheSkillsZuAusschreibung(ausschreibungSelected.getId());
		} catch (Exception e) {
			Logger.getLogger(this.getClass().getName()).log(Level.ERROR, "Operation ermittleSkillsZuAusschreibung Error ",e);
			FacesMessage msg = null;  
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Fehlermeldung", "Es ist ein Fehler aufgetreten: " + e.getMessage() + "; ermittleSkillsZuAusschreibung");  
			FacesContext.getCurrentInstance().addMessage(null, msg);  

		}
	}
	
	


	public List<Ausschreibung> getList() {
		if(list == null){
			list = new ArrayList<Ausschreibung>();
		}
		return list;
	}

	public void setList(List<Ausschreibung> list) {
		this.list = list;
	}


	public List<Ausschreibung> getListSelected() {
	
		return listSelected;
	}



	public void setListSelected(List<Ausschreibung> listSelected) {
		this.listSelected = listSelected;
	}



	public Ausschreibung getAusschreibung() {
		return ausschreibung;
	}



	public void setAusschreibung(Ausschreibung ausschreibung) {
		this.ausschreibung = ausschreibung;
	}



	public Ausschreibung getAusschreibungSelected() {
		return ausschreibungSelected;
	}



	public void setAusschreibungSelected(Ausschreibung ausschreibungSelected) {
		this.ausschreibungSelected = ausschreibungSelected;
	}



	public List<Skill> getListAllSkills() {
		return listAllSkills;
	}



	public void setListAllSkills(List<Skill> listAllSkills) {
		this.listAllSkills = listAllSkills;
	}



	public List<Skill> getListSelectedSkills() {
		return listSelectedSkills;
	}



	public void setListSelectedSkills(List<Skill> listSelectedSkills) {
		this.listSelectedSkills = listSelectedSkills;
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




	




	
	
	
	
}
