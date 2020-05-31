package application;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import business.domain.subscriptions.SubscriptionType;
import business.handlers.EnrollClassHandler;
import facade.dto.Class;
import facade.dto.Modality;
import facade.exceptions.ApplicationException;
import facade.handlers.IConsumerServiceRemote;

/**
 * A service the offers the enroll of consumer in a class. The purpose of this class is to provide access 
 * to the business layer, hiding its implementation from the clients. Clients should never interact 
 * directly with the business layer. 
 * 
 * @author fc51468
 * @version 1.1 (4/4/2020)
 */
@Stateless
public class ConsumerService implements IConsumerServiceRemote {
	
	/**
	 * The business object facade that handles this use case request
	 */
	@EJB private EnrollClassHandler enrollClassHandler;

//    /**
//     * Constructs a consumer service given the enroll in a class handler.
//     * 
//     * @param enrollClassHandler The enroll in a class handler 
//     */
//	public ConsumerService(EnrollClassHandler enrollClassHandler) {
//		this.enrollClassHandler = enrollClassHandler;
//	}
	
	/**
	 * Starts the process of enrolling a class. 
	 * 
	 * @return a list of existing modalities
	 * @throws ApplicationException 
	 */
	public List<Modality> enrollClassInit() throws ApplicationException {
		return this.enrollClassHandler.enrollClassInit();
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
		
		return this.enrollClassHandler.indicatesModalityAndTypeOfRegistration(mod, registration);
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
		
		return this.enrollClassHandler.enrollClass(theClass, consumerNumber, registration);
	}


	@Override
	public List<String> getSubscriptions() {
		return Stream.of(SubscriptionType.values()).map(x->x.toString()).collect(Collectors.toList());
	}

}
