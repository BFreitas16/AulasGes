package business.handlers;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import business.domain.classes.Class;
import business.domain.classes.ClassCatalog;
import business.domain.modalities.Modality;
import business.domain.modalities.ModalityCatalog;

import java.time.DayOfWeek;
import java.time.LocalTime;

import facade.exceptions.ApplicationException;
import facade.exceptions.CreateClassException;
import facade.exceptions.NullParametersException;
import util.DTOFactory;

/**
 * Handles the create class use case. This represents a very 
 * simplified use case with just two operation:
 *           - createClassInit
 *           - createClass
 * 
 * @author fc51468
 * @version 1.1 (28/03/2020)
 *
 */
@Stateless
public class CreateClassHandler {
	
	@EJB private ModalityCatalog modalityCatalog;
	
	@EJB private ClassCatalog classCatalog;

//	/**
//	 * Creates a handler for the create class use case given
//	 * the modality and the class catalogs.
//	 *  
//	 * @param modalityCatalog A modality's catalog
//	 * @param classCatalog A class's catalog
//	 */
//	public CreateClassHandler(EntityManagerFactory emf) {
//		this.emf = emf;
//	}

	/**
	 * Starts the process of creating a class. 
	 * 
	 * @return a list of existing modalities
	 * @throws ApplicationException 
	 */
	public List<facade.dto.Modality> createClassInit() throws ApplicationException {	
		List<business.domain.modalities.Modality> modalities = modalityCatalog.getAllModalities();
		// builds the DTO modalities from modalities
		return  DTOFactory.makeModalitiesDTO(modalities);
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
					throws ApplicationException{

		// checks if it have all the parameters
		if (mod == null || name == null ||
				daysOfWeek == null || startTime == null)
			throw new NullParametersException();

		// checks if the name is valid
		if ( !Class.isValidNameForAClass(name) )
			throw new CreateClassException("The name '" + name + "' doesn't follow the rules.");
					
		// get the modality specified by the manager
		Modality m = modalityCatalog.getModality(mod);
					
		// checks if the duration is positive and greater than the minimum required
		if (duration < 1 || m.getMinimumDuration() > duration)
			throw new CreateClassException("The duration '" + duration + "' doesn't follow the rules." + 
					"Have to be POSITIVE and greater than the minimum required by the modality.");
					
		// creates the class and adds it to the modality
		classCatalog.addClass(name, m, daysOfWeek, startTime, duration);

	
	}

}
