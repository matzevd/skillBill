package org.skillbill.controller;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.skillbill.common.Skill;
import org.skillbill.dao.SkillDao;
import org.skillbill.enums.GeschlechtEnum;
import org.skillbill.enums.StandortEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;



@Component(value="skillView")
@Scope(value="session")
public class SkillController implements Serializable {
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Autowired
	private org.skillbill.dao.SkillDao SkillDao;
	

	private Skill skill;
	private Skill skillSelected;
	private List<Skill> list;
	private List<Skill> listSelected;
	
	@PostConstruct
    public void init() {
		refreshList();
    }



	public void refreshList() {
		this.skill = new Skill();
		this.skillSelected = new Skill();
		this.list = new ArrayList<Skill>();
		this.listSelected = new ArrayList<Skill>();
		try {
			this.list.addAll(SkillDao.findAll());
			this.listSelected.addAll(this.list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	public void save() {
		try {
		    
			SkillDao.persist(this.skill);
			
			notificationSuccess("Skill " + this.skill.getKurzbezeichnung()+  " erfolgreich gespeichert!");
			refreshList();
		} catch (Exception e) {
			notificationError(e,"Skill " + this.skill.getKurzbezeichnung()+"  nicht erfolgreich gespeichert! Fehler: " + e.getMessage());

			e.printStackTrace();
		}
	}

	public void update() {
		try {
			SkillDao.merge(this.skillSelected);
			
			notificationSuccess("Skill " + this.skillSelected.getKurzbezeichnung()+ " erfolgreich bearbeitet!");
			refreshList();
		} catch (Exception e) {
			notificationError(e,"Skill " +  this.skillSelected.getKurzbezeichnung() + "  nicht erfolgreich bearbeitet! Fehler: " + e.getMessage());
		}
	}

	public void delete() {
		try {
			SkillDao.remove(this.skillSelected.getId());
		
			notificationSuccess("Skill " + this.skillSelected.getKurzbezeichnung()+  " erfolgreich gelöscht!");
			refreshList();
		} catch (Exception e) {
			notificationError(e,"Skill "  + this.skillSelected.getKurzbezeichnung() + "  nicht erfolgreich gelöscht! Fehler: " + e.getMessage());
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
	
	


	public List<Skill> getList() {
		if(list == null){
			list = new ArrayList<Skill>();
		}
		return list;
	}

	public void setList(List<Skill> list) {
		this.list = list;
	}




	public SkillDao getSkillDao() {
		return SkillDao;
	}



	public void setSkillDao(SkillDao SkillDao) {
		this.SkillDao = SkillDao;
	}



	public Skill getSkill() {
		return skill;
	}



	public void setSkill(Skill skill) {
		this.skill = skill;
	}


	public StandortEnum[] getStandorte() {
		return StandortEnum.values();
	}

	public GeschlechtEnum[] getGeschlechte() {
		return GeschlechtEnum.values();
	}



	public Skill getSkillSelected() {
		return skillSelected;
	}



	public void setSkillSelected(Skill skillSelected) {
		this.skillSelected = skillSelected;
	}



	public List<Skill> getListSelected() {
		return listSelected;
	}



	public void setListSelected(List<Skill> listSelected) {
		this.listSelected = listSelected;
	}

	
	
	
}
