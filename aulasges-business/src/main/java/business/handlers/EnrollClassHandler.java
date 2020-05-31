package business.handlers;

import java.util.List;
import java.util.stream.Stream;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import business.domain.classes.ClassCatalog;
import business.domain.modalities.ModalityCatalog;
import business.domain.subscriptions.RegularSubscription;
import business.domain.subscriptions.SoloSubscription;
import business.domain.subscriptions.Subscription;
import business.domain.subscriptions.SubscriptionCatalog;
import business.domain.subscriptions.SubscriptionType;
import business.domain.users.User;
import business.domain.users.UserCatalog;
import facade.dto.Class;
import facade.dto.Modality;
import facade.exceptions.ApplicationException;
import facade.exceptions.NullParametersException;
import util.DTOFactory;

/**
 * Handles the enroll class use case. This represents a very 
 * simplified use case with just three operation:
 *           - enrollClassInit
 *           - indicatesModalityAndTypeOfRegistration
 *           - enrollClass
 * 
 * @author fc51468
 * @version 1.1 (29/03/2020)
 *
 */
@Stateless
public class EnrollClassHandler {
	
	@EJB private ModalityCatalog modalityCatalog;
	
	@EJB private ClassCatalog classCatalog;
	
	@EJB private UserCatalog userCatalog;
	
	@EJB private SubscriptionCatalog subscriptionCatalog;

	/**
	 * Starts the process of enrolling a class. 
	 * 
	 * @return a list of existing modalities
	 * @throws ApplicationException 
	 */
	public List<Modality> enrollClassInit() throws ApplicationException {
		List<business.domain.modalities.Modality>  modalities = modalityCatalog.getAllModalities();
		
		// builds the DTO modalities from modalities
		return DTOFactory.makeModalitiesDTO(modalities);	
	}

	/**
	 * Starts a the process of enrolling a class. 
	 * 
	 * @param mod The modality for the registration
	 * @param registration The registration type
	 * @return a list of active classes
	 * @throws ApplicationException When the modality or the registration 
	 * type are not valid
	 */
	public List<Class> indicatesModalityAndTypeOfRegistration(String mod, 
			String registration) throws ApplicationException{

		if(mod == null || registration == null )
			throw new NullParametersException();
		
		// get the modality specified by the manager
		business.domain.modalities.Modality m = modalityCatalog.getModality(mod);
		
		//check if the subscription is valid and if not gives an error
		if (!isValidRegistrationType(registration))
			throw new ApplicationException("Subscription +'" + registration + "' doesn't exist");

		// passed all validations gets empty currentRegistration
		Subscription currentRegistration = getRegistrationType(registration);
				
		// get the classes of the modality asked
		List<business.domain.classes.ActiveClass> classesOfModality = classCatalog.getActiveClassesOfModality(m);

		// get active classes
		List<business.domain.classes.ActiveClass> activeClasses = currentRegistration.getActiveClasses(classesOfModality);
		
		// make the DTO from active classes
		return DTOFactory.makeClassesDTO(activeClasses, currentRegistration.getType());
	}


	/**
	 * Enrolls a class with a consumerNumer.
	 * It checks that exists a class with the Class given and that exists 
	 * a user with the number given.
	 * 
	 * @param theClass The class to enroll
	 * @param consumerNumber The id of the consumer
	 * @return The cost of the registration
	 * @throws ApplicationException When doesn't exist a class with the 
	 * given class, or the number don't exists
	 */
	public double enrollClass(String theClass, int consumerNumber, String registration) 
			throws ApplicationException {

		if (theClass == null || registration == null)
			throw new NullParametersException();
		
		//check if the subscription is valid and if not gives an error
		if (!isValidRegistrationType(registration))
			throw new ApplicationException("Subscription +'" + registration + "' doesn't exist");

		// passed all validations gets empty currentRegistration
		Subscription currentRegistration = getRegistrationType(registration);
		
		//get the class specified by the manager
		business.domain.classes.Class c = classCatalog.getClass(theClass);

		// get the consumer specified by number
		User u = userCatalog.getUserByID(consumerNumber);
		
		return subscriptionCatalog.subscribeUserToClass(currentRegistration, c, u);
	}
	
	private boolean isValidRegistrationType(String subs) {
		return Stream.of(SubscriptionType.values()).anyMatch(v -> v.name().equals(subs));
	}
	
	private Subscription getRegistrationType(String subs) {
		return subs.equals(SubscriptionType.REGULARSUBSCRIPTION.name()) ?
				new RegularSubscription() : new SoloSubscription();
	}
}
