package presentation.web.model;

import java.util.LinkedList;

import facade.dto.Facility;
import facade.exceptions.ApplicationException;
import facade.handlers.IClassServiceRemote;

public class ActivateClassModel extends Model {
	
	private String name;
	private String beginDate;
	private String endDate;
	private String max;
	private String facility; // facility name
	private IClassServiceRemote enableClassHandler;
	
	public void setEnableClassHandler(IClassServiceRemote enableClassHandler) {
		this.enableClassHandler = enableClassHandler;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	
	public String getBeginDate() {
		return beginDate;
	}
	
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public String getEndDate() {
		return endDate;
	}
	
	public void setMax(String max) {
		this.max = max;
	}
	
	public String getMax() {
		return max;
	}
	
	public void setFacility(String facility) {
		this.facility = facility;
	}
	
	public String getFacility() {
		return facility;
	}
	
	public void clearFields() {
		name = beginDate = endDate = max = facility = "";
	}
	
	public Iterable<Facility> getFacilities() {
		try {
			return enableClassHandler.enableClassInit();
		} catch (ApplicationException e) {
			return new LinkedList<> ();		
		}
	}

}
