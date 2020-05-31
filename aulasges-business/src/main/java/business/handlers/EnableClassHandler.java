package business.handlers;

import java.time.LocalDate;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import business.domain.classes.Class;
import business.domain.classes.ClassCatalog;
import business.domain.facilities.Facility;
import business.domain.facilities.FacilityCatalog;
import facade.exceptions.ActivateClassException;
import facade.exceptions.ApplicationException;
import facade.exceptions.NullParametersException;
import util.DTOFactory;

/**
 * Handles the enable class use case. This represents a very 
 * simplified use case with just two operation:
 *           - enableClassInit
 *           - enableAClassInFacilityFromTo
 * 
 * @author fc51468
 * @version 1.1 (28/03/2020)
 *
 */
@Stateless
public class EnableClassHandler {
	
	@EJB private FacilityCatalog facilityCatalog;
	
	@EJB private ClassCatalog classCatalog;

	/**
	 * Starts the process of enabling a class. 
	 * 
	 * @return a list of existing facilities
	 * @throws ApplicationException 
	 */
	public List<facade.dto.Facility> enableClassInit() throws ApplicationException {		
		List<Facility> facilities = facilityCatalog.getAllFacilities();
		// builds the DTO facilities from session
		return DTOFactory.makeFacilitiesDTO(facilities);
	}

	/**
	 * Enables a class with a name.
	 * It checks that exists a class with the name given, the dates from - to are valid and define
	 * a future period, also checks if the facility exists, supports the modality of the class and
	 * it's free on time/days of the week of the class throughout the active period of the class
	 * 
	 * @param name The name's class
	 * @param facility The facility to add the class
	 * @param from The start date
	 * @param to The end date
	 * @param max The max number of people in a class
	 * @throws ApplicationException When doesn't exist a class with the given name, or the dates
	 * from - to don't define a future period, or facility isn't valid or doesn't support the
	 * modality of the class or it's not free in some period of from - to date or doesn't
	 * support the amount of people given as max
	 */
	public void enableAClassInFacilityFromTo(String name, String facility, 
			LocalDate from, LocalDate to, int max) 
					throws ApplicationException {

		if(name == null || facility == null 
				|| from == null || to == null)
			throw new NullParametersException();
		
		// get the class specified by the manager
		Class c = classCatalog.getClass(name);
					
		// verifies if class is active
		if (c.isActice()) 
			throw new ActivateClassException("Class "+ name + " is already active");

		// get the facility specified by the manager
		Facility f = facilityCatalog.getFacility(facility);
					
		classCatalog.activateClass(c, f, from, to, max);
	}

}
