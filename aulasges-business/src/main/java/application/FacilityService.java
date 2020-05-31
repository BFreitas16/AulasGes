package application;

import java.time.LocalDate;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import business.handlers.ViewFacilityOccupationHandler;
import facade.dto.Session;
import facade.exceptions.ApplicationException;
import facade.handlers.IFacilityServiceRemote;

/**
 * A service the offers the view facility occupation. The purpose of this class is to provide access 
 * to the business layer, hiding its implementation from the clients. Clients should never interact 
 * directly with the business layer. 
 * 
 * @author fc51468
 * @version 1.1 (4/4/2020)
 */
@Stateless
public class FacilityService implements IFacilityServiceRemote {
	
	/**
	 * The business object facade that handles this use case request
	 */
	@EJB
	private ViewFacilityOccupationHandler viewFacilityOccupationHandler;
	
//	/**
//     * Constructs a facility service given the view facility occupation handler.
//     * 
//     * @param viewFacilityOccupationHandler The view facility occupation handler 
//     */
//	public FacilityService(ViewFacilityOccupationHandler viewFacilityOccupationHandler) {
//		this.viewFacilityOccupationHandler = viewFacilityOccupationHandler;
//	}
	
	/**
	 * Get the occupation (sessions) of a facility in a period of time
	 * It checks that exists a facility with the given name
	 * 
	 * @param name The name of the facility
	 * @param date The date to see the Occupation of the given facility
	 * @return a list of occupation of the facility in the specified date
	 * @throws ApplicationException When doesn't exist a facility with the given name
	 */
	public List<Session> getOccupationOfAFacility(String name, LocalDate date)
			throws ApplicationException {
		
		return this.viewFacilityOccupationHandler.getOccupationOfAFacility(name, date);
	}

}
