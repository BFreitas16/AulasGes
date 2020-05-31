package facade.handlers;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.ejb.Remote;

import facade.dto.Modality;
import facade.dto.Facility;
import facade.exceptions.ApplicationException;

@Remote
public interface IClassServiceRemote {
	
	// Create Class
	
	public List<Modality> createClassInit() throws ApplicationException;
	
	public void createClass(String mod, String name, 
			List<DayOfWeek> daysOfWeek, LocalTime startTime, int duration) 
			throws ApplicationException;
	
	// Activate Class
	
	public List<Facility> enableClassInit() throws ApplicationException;
	
	public void enableAClassInFacilityFromTo(String name, String facility, 
			LocalDate from, LocalDate to, int max) 
		    throws ApplicationException;

}
