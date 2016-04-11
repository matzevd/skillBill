package org.skillbill.views;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.skillbill.common.Skill;
import org.skillbill.dao.SkillDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.tagcloud.TagCloudModel;
import org.primefaces.model.tagcloud.DefaultTagCloudItem;
import org.primefaces.model.tagcloud.DefaultTagCloudModel;
import org.primefaces.model.tagcloud.TagCloudItem;


@Component(value="homeView")
@Scope(value="session")
@ManagedBean
public class HomeView implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5954810205596251172L;
	
	
	  private TagCloudModel model;
	  
	  @Autowired
	  private SkillDao skilldao;
	     
	    @PostConstruct
	    public void init() {
	    	
	    	List<Skill> skilllist = new ArrayList<Skill>();
	    	
	    	try {
	    		skilllist = skilldao.findAll();
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	
	    	
	        model = new DefaultTagCloudModel();
	        for (Skill skill : skilllist) {
	        	model.addTag(new DefaultTagCloudItem(skill.getKurzbezeichnung(),1));
				
			}
	        
	     
	    }
	 
	    public TagCloudModel getModel() {
	        return model;
	    }
	     
	    public void onSelect(SelectEvent event) {
	        TagCloudItem item = (TagCloudItem) event.getObject();
	        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Selected", item.getLabel());
	        FacesContext.getCurrentInstance().addMessage(null, msg);
	    }

}
