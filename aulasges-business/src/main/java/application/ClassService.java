package application;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import business.handlers.CreateClassHandler;
import business.handlers.EnableClassHandler;
import facade.dto.Modality;
import facade.exceptions.ApplicationException;
import facade.handlers.IClassServiceRemote;

/**
 * A service the offers the create and activate a class. The purpose of this class is
 * to provide access to the business layer, hiding its implementation from the clients.
 * Clients should never interact directly with the business layer. 
 * 
 * @author fc51468
 * @version 1.1 (4/4/2020)
 */
@Stateless
public class ClassService implements IClassServiceRemote {
	
	/**
	 * The business object facade that handles the 1st use case request: create
	 */
	@EJB private CreateClassHandler createClassHandler;
	
	/**
	 * The business object facade that handles the 2nd use case request: activate
	 */
	@EJB private EnableClassHandler enableClassHandler;
	
//	/**
//     * Constructs a class service given the create and activate class handler.
//     * 
//     * @param createClassHandler The create class handler 
//     * @param enableClassHandler The activate class handler
//     */
//	public ClassService(CreateClassHandler createClassHandler, EnableClassHandler enableClassHandler) {
//		this.createClassHandler = createClassHandler;
//		this.enableClassHandler = enableClassHandler;
//	}
	
	/**
	 * Starts the process of creating a class. 
	 * 
	 * @return a list of existing modalities
	 * @throws ApplicationException 
	 */
	public List<Modality> createClassInit() throws ApplicationException {
		return this.createClassHandler.createClassInit();
	}
	
	/**
	 * Creates a new class with a valid modality.
	 * It checks that there is no other class with the same name and it has 6 characters,
	 * at least 3 alphanumeric characters and has no spaces.
	 * It also checks if the duration is a positive number and it is not less than the 
	 * minimum defined for the classes of the indicated modality.
	 * 
	 * @param mod The modality of the class
	 * @param name The class's name
	 * @param daysOfWeek The class's days of week
	 * @param startTime The class's start time
	 * @param duration The class's duration in minutes
	 * @throws ApplicationException When the modality is not valid, or the name doesn't follow
	 * the rules (has 6 characters, at least 3 alphanumeric characters and has no spaces), 
	 * or name already exists, or the duration is not positive or is not the minimum defined
	 * for the classes of the indicated modality
	 */
	public void createClass(String mod, String name, 
			List<DayOfWeek> daysOfWeek, LocalTime startTime, int duration) 
			throws ApplicationException {
		
		this.createClassHandler.createClass(mod, name, daysOfWeek, startTime, duration);
	}
	
	/**
	 * Starts the process of enabling a class. 
	 * 
	 * @return a list of existing facilities
	 * @throws ApplicationException 
	 */
	public List<facade.dto.Facility> enableClassInit() throws ApplicationException {
		return this.enableClassHandler.enableClassInit();
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
		
		this.enableClassHandler.enableAClassInFacilityFromTo(name, facility, from, to, max);
	}

}
