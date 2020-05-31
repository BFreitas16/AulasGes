package business.handlers;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import business.domain.facilities.Facility;
import business.domain.facilities.FacilityCatalog;
import facade.exceptions.ApplicationException;
import facade.exceptions.NullParametersException;
import util.DTOFactory;

/**
 * Handles the view occupancy of a facility use case. This represents
 * a very simplified use case with just 1 operation:
 *           - getOccupationOfAFacility
 * 
 * @author fc51468
 * @version 1.1 (28/03/2020)
 *
 */
@Stateless
public class ViewFacilityOccupationHandler {

	@EJB
	private FacilityCatalog facilityCatalog;

	/**
	 * Get the occupation (sessions) of a facility in a period of time
	 * It checks that exists a facility with the given name
	 * 
	 * @param name The name of the facility
	 * @param date The date to see the Occupation of the given facility
	 * @return a list of occupation of the facility in the specified date
	 * @throws ApplicationException When doesn't exist a facility with the given name
	 */
	public List<facade.dto.Session> getOccupationOfAFacility(String name, LocalDate date)
			throws ApplicationException {

		if (name == null || date == null)
			throw new NullParametersException();
		
		// get the indicated facility
		Facility f = facilityCatalog.getFacility(name);
		
		// get the sessions/occupation of that facility
		List<business.domain.classes.Session> sessions = facilityCatalog.getSessionIn(f, date);
		
		// builds the DTO sessions from session
		List<facade.dto.Session> returnSessions = DTOFactory.makeSessionsDTO(sessions);
		
		// Sort the sessions by start time
		Collections.sort(returnSessions, 
				(s1, s2) -> s1.getStartTime().compareTo(s2.getStartTime()));
		
		return returnSessions;

	}

}
