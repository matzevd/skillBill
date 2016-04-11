package org.skillbill.views;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.skillbill.common.Ausschreibung;
import org.skillbill.dao.AusschreibungDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component(value="ausschreibungView")
@ViewScoped
public class AusschreibungView implements Serializable {
	
	

	/**
	 * Das ist die Oberflächenklassen für ausschreibungView
	 */
	private static final long serialVersionUID = 1L;


	@Autowired
	private transient AusschreibungDao ausschreibungDao;
	

	private Ausschreibung ausschreibung;
	private Ausschreibung ausschreibungSelected;
	private List<Ausschreibung> list;	
	private List<Ausschreibung> listSelected;

	
	@PostConstruct
    public void init() {
		refreshList();
    }



	public void refreshList() {
		this.ausschreibung = new Ausschreibung();
		this.ausschreibungSelected = new Ausschreibung();

		this.list = new ArrayList<Ausschreibung>();
		this.listSelected = new ArrayList<Ausschreibung>();

		
		try {
			this.list.addAll(ausschreibungDao.findAll());
			this.listSelected.addAll(this.list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	public void save() {
		try {
		    
			ausschreibungDao.persist(this.ausschreibung);
			
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



	public AusschreibungDao getAusschreibungDao() {
		return ausschreibungDao;
	}



	public void setAusschreibungDao(AusschreibungDao ausschreibungDao) {
		this.ausschreibungDao = ausschreibungDao;
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






	




	
	
	
	
}
