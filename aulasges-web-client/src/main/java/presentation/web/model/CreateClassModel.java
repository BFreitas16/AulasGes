package presentation.web.model;

import java.util.LinkedList;

import facade.dto.Modality;
import facade.exceptions.ApplicationException;
import facade.handlers.IClassServiceRemote;

public class CreateClassModel extends Model {
	
	private String name;
	private String beginTime;
	private String duration;
	private String[] daysOfWeek;
	private String modality; // modality name
	private IClassServiceRemote createClassHandler;
	
	public void setCreateClassHandler(IClassServiceRemote createClassHandler) {
		this.createClassHandler = createClassHandler;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	
	public String getBeginTime() {
		return beginTime;
	}
	
	public void setDuration(String duration) {
		this.duration = duration;
	}
	
	public String getDuration() {
		return duration;
	}
	
	public void setDaysOfWeek(String[] daysOfWeek) {
		this.daysOfWeek = daysOfWeek;
	}
	
	public String[] getDaysOfWeek() {
		return daysOfWeek;
	}
	
	public void setModality(String modality) {
		this.modality = modality;
	}
	
	public String getModality() {
		return modality;
	}
	
	public Iterable<Modality> getModalities() {
		try {
			return createClassHandler.createClassInit();
		} catch (ApplicationException e) {
			return new LinkedList<> ();		
		}
	}

	public void clearFields() {
		name = beginTime = duration = modality = "";
		daysOfWeek = new String[0];
	}

}
