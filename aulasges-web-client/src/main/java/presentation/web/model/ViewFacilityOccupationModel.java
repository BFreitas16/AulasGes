package presentation.web.model;

import java.util.Collection;
import java.util.List;

import facade.handlers.IFacilityServiceRemote;

public class ViewFacilityOccupationModel extends Model {
	
	private String name;
	private String date;
	private List<String> sessions; // facility occupation
	@SuppressWarnings("unused")
	private IFacilityServiceRemote viewFacilityOccupationHandler;
	
	public void setViewFacilityOccupationHandler(IFacilityServiceRemote viewFacilityOccupationHandler) {
		this.viewFacilityOccupationHandler = viewFacilityOccupationHandler;
	}
	
	public void setName(String name) {
		this.name = name;	
	}
	
	public String getName() {
		return name;
    }
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getDate() {
		return date;
    }
	
	public void setSessions(List<String> sessions) {
		this.sessions = sessions;
	}
	
	public Collection<String> getSessions() {
		return sessions;
	}
	
	public void clearFields() {
		this.name = date = "";
//		this.sessions = null;
	}

}
