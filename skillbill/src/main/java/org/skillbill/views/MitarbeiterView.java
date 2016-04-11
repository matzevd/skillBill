package org.skillbill.views;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.skillbill.common.Mitarbeiter;
import org.skillbill.dao.MitarbeiterDao;
import org.skillbill.dao.service.MitarbeiterService;
import org.skillbill.enums.GeschlechtEnum;
import org.skillbill.enums.StandortEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;



@Component(value="mitarbeiterView")
@ViewScoped
public class MitarbeiterView implements Serializable {
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Autowired
	private  MitarbeiterDao mitarbeiterService;
	

	private Mitarbeiter mitarbeiter;
	private Mitarbeiter mitarbeiterSelected;
	private List<Mitarbeiter> list;	
	private List<Mitarbeiter> listSelected;

	
	@PostConstruct
    public void init() {
		refreshList();
    }



	public void refreshList() {
		this.mitarbeiter = new Mitarbeiter();
		this.mitarbeiterSelected = new Mitarbeiter();

		this.list = new ArrayList<Mitarbeiter>();
		this.listSelected = new ArrayList<Mitarbeiter>();

		
		try {
			this.list.addAll(mitarbeiterService.findAll());
			this.listSelected.addAll(this.list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	public void save() {
		try {
		    
			mitarbeiterService.persist(this.mitarbeiter);
			
			notificationSuccess("Mitarbeiter " + this.mitarbeiter.getVorname()+ " " + this.mitarbeiter.getNachname() + " erfolgreich gespeichert!");
			refreshList();
		} catch (Exception e) {
			notificationError(e,"Mitarbeiter " + this.mitarbeiter.getVorname()+ " " + this.mitarbeiter.getNachname() + "  nicht erfolgreich gespeichert! Fehler: " + e.getMessage());

			e.printStackTrace();
		}
	}

	public void update() {
		try {
			mitarbeiterService.merge(this.mitarbeiterSelected);
			
			notificationSuccess("Mitarbeiter " + this.mitarbeiterSelected.getVorname()+ " " + this.mitarbeiterSelected.getNachname() + " erfolgreich bearbeitet!");
			refreshList();
		} catch (Exception e) {
			notificationError(e,"Mitarbeiter " + this.mitarbeiterSelected.getVorname()+ " " + this.mitarbeiterSelected.getNachname() + "  nicht erfolgreich bearbeitet! Fehler: " + e.getMessage());
		}
	}

	public void delete() {
		try {
			mitarbeiterService.remove(this.mitarbeiterSelected.getId());
		
			notificationSuccess("Mitarbeiter " + this.mitarbeiterSelected.getVorname()+ " " + this.mitarbeiterSelected.getNachname() + " erfolgreich gelöscht!");
			refreshList();
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





	public StandortEnum[] getStandorte() {
		return StandortEnum.values();
	}

	public GeschlechtEnum[] getGeschlechte() {
		return GeschlechtEnum.values();
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
		return mitarbeiterService;
	}



	public void setMitarbeiterService(MitarbeiterService mitarbeiterService) {
		this.mitarbeiterService = mitarbeiterService;
	}






	




	
	
	
	
}
