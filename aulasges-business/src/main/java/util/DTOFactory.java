package util;

import java.lang.reflect.Method;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import business.domain.subscriptions.SubscriptionType;
import facade.dto.Class;
import facade.dto.Facility;
import facade.dto.Modality;
import facade.dto.Session;

/**
 * A generic DTO Factory. It can create a Modality, Facility, Session or
 * Class DTO calling methods at runtime using Java Reflection
 * 
 * @author fC51468
 * @version 1.1 (7/04/2020)
 * 
 */
public class DTOFactory {
	
	private static final String MODALITYMETHODTOKEN = "Modality";
	private static final String FACILITYMETHODTOKEN = "Facility";
	private static final String SESSIONMETHODTOKEN  = "Session";
	private static final String CLASSMETHODTOKEN    = "Class";

	private DTOFactory() {}

	/**
	 * Get the DTO modalities of a modality list
	 * 
	 * @param modalities The list of modalities to get the DTO
	 * @return A list of DTO modalities
	 */
	public static List<Modality> makeModalitiesDTO(List<business.domain.modalities.Modality> modalities) {
		return makeDTOs(MODALITYMETHODTOKEN, modalities);
	}
	
	/**
	 * Get the DTO facilities of a facility list
	 * 
	 * @param facilities The list of facilities to get the DTO
	 * @return A list of DTO facilities
	 */
	public static List<Facility> makeFacilitiesDTO(List<business.domain.facilities.Facility> facilities) {
		return makeDTOs(FACILITYMETHODTOKEN, facilities);
	}
	
	/**
	 * Get the DTO sessions of a session list
	 * 
	 * @param sessions The list of sessions to get the DTO
	 * @return A list of DTO sessions
	 */
	public static List<Session> makeSessionsDTO(List<business.domain.classes.Session> sessions) {
		return makeDTOs(SESSIONMETHODTOKEN, sessions);
	}
	
	/**
	 * Get the DTO classes of a class list and a type of subscription
	 * The subscription is important to be able to calculate the free spaces
	 * in a class because regular subscription have extra spaces
	 * 
	 * @param classes The list of classes to get the DTO
	 * @param subs The subscription type to get the free spaces of a class
	 * @return A list of DTO classes
	 */
	public static List<Class> makeClassesDTO(List<business.domain.classes.ActiveClass> classes, SubscriptionType subs) {
		return makeDTOs(CLASSMETHODTOKEN, classes, subs);
	}

	/**
	 * Get the list of DTOs of a DTO class given the type of DTO that needs to be
	 * created, the List of objects to creates the DTOs and its possible to add
	 * a optional parameter that represents the subscription type and give us a way
	 * to calculate the free spaces in a class
	 * 
	 * @param type The type of DTO to create
	 * @param list The list of objects to create the DTOs
	 * @param optionalSubsType The optional list of parameters. In reality it will only 
	 * be passed 1 object in the list that represents the type of subscription
	 * @return the list of DTOs based in the given list of objects
	 */
	@SuppressWarnings("unchecked")
	private static <T,E> List<T> makeDTOs(String type, List<E> list, SubscriptionType... optionalSubsType) {
		List<T> returnList = new ArrayList<>();

		String methodName = getMethodName(type);

		try {
			
			for (E e : list) {
				
				// the parametes of the method
				java.lang.Class<?>[] paramsToMethod1 = new java.lang.Class[2];
				paramsToMethod1[0] = e.getClass();
				paramsToMethod1[1] = optionalSubsType.getClass();
				
				// get the method to create the requested DTOs
				Method getParamsForDTOMethod = DTOFactory.class.getDeclaredMethod(methodName, paramsToMethod1);
				// necessary to be able to call a private method
				getParamsForDTOMethod.setAccessible(true);
				// invoke the method and gets the DTO
				Object dto = getParamsForDTOMethod.invoke(null, e, optionalSubsType);

				if (dto != null)
					returnList.add((T) dto);
				
			} // for end
			
		// if it gives an error is returned a empty list
		}catch (Exception e) {
			return returnList;
		}

		return returnList;
	}
	
	/**
	 * Get the name of the method given a string type which represents
	 * which DTO class do we want to build
	 * 
	 * @param type The type of DTO we want to build
	 * @return the name of the method given the type of DTO we want to build
	 */
	private static String getMethodName(String type) {
		
		if (type.equals(MODALITYMETHODTOKEN)) {
			return "getModalityDTO";
			
		} else if (type.equals(FACILITYMETHODTOKEN)) {
			return "getFacilityDTO";
			
		} else if (type.equals(SESSIONMETHODTOKEN)) {
			return "getSessionDTO";
			
		} else if (type.equals(CLASSMETHODTOKEN)) {
			return "getClassDTO";
		}

		return "";
	}
	
	////////////////////////////
	////// DTO's  GETTERS //////
	////////////////////////////

	/**
	 * Get the necessary parameters for the creation of the DTO Modality
	 * and creates it
	 * 
	 * @param m The modality from which we must create the DTO
	 * @param ignore Optional parameter never used. Necessary to be called by reflection
	 * @return The modality DTO of the given Modality
	 */
	@SuppressWarnings("unused")
	private static Modality getModalityDTO(business.domain.modalities.Modality m, SubscriptionType... ignore) {

		// get the parameters to create the DTO
		Object[] params = new Object[2];
		params[0] = m.getName();
		
		return createModalityDTO(params);

	}

	/**
	 * Get the necessary parameters for the creation of the DTO Facility
	 * and creates it
	 * 
	 * @param f The facility from which we must create the DTO
	 * @param ignore Optional parameter never used. Necessary to be called by reflection
	 * @return The facility DTO of the given Facility
	 */
	@SuppressWarnings("unused")
	private static Facility getFacilityDTO(business.domain.facilities.Facility f, SubscriptionType... ignore) {
		
		// get the parameters to create the DTO
		Object[] params = new Object[3];
		params[0] = f.getName();
		params[1] = f.getType().name();
		params[2] = f.getCapacity();

		return createFacilityDTO(params);
	}

	/**
	 * Get the necessary parameters for the creation of the DTO Session
	 * and creates it
	 * 
	 * @param s The session from which we must create the DTO
	 * @param ignore Optional parameter never used. Necessary to be called by reflection
	 * @return The session DTO of the given Session
	 */
	@SuppressWarnings("unused")
	private static Session getSessionDTO(business.domain.classes.Session s, SubscriptionType... ignore) {
		
		// get the parameters to create the DTO
		Object[] params = new Object[3];
		params[0] = s.getName();
		params[1] = s.getStartTime();
		params[2] = s.getEndTime();

		return createSessionDTO(params);
	}

	/**
	 * Get the necessary parameters for the creation of the DTO Class
	 * and creates it
	 * 
	 * @param c The class from which we must create the DTO
	 * @param subsType The type of subscription in the class
	 * @return The class DTO of the given Class or null if the class doesn't have
	 * free spaces
	 */
	@SuppressWarnings("unused")
	private static Class getClassDTO(business.domain.classes.ActiveClass c, SubscriptionType... subsType) {
		// if it isn't MONTHLYPAYMENT it can only be SEPARATEDPAYMENT
        int places = subsType[0].equals(SubscriptionType.REGULARSUBSCRIPTION) ?
            c.getNumberOfPlacesRegularSubscriptions() : c.getNumberOfPlacesFromNext24hSession();
        
        // if there isn't free spaces it will not be created the DTO
        if (places < 1)
        	return null;
		
        // get the parameters to create the DTO
		Object[] params = new Object[5];
		params[0] = c.getName();
		params[1] = c.getStartTime();
		params[2] = c.getDaysOfWeek();
		params[3] = c.getFacility().getName();
		params[4] = places;

		return createClassDTO(params);
	}
	
	////////////////////////////
	////// DTO's CREATORS //////
	////////////////////////////
	
	/**
	 * Creates a DTO Modality object given an array of objects
	 * It's important that the array have 2 elements and the following order:
	 * 1: modality name ; 2: the description of the modality
	 * 
	 * @param params The array of parameters for build the modality DTO
	 * @return a modality DTO with the parameters given in the array
	 */
	private static Modality createModalityDTO(Object[] params) {
		return new Modality((String) params[0]);
	}
	
	/**
	 * Creates a DTO Facility object given an array of objects
	 * It's important that the array have 3 elements and the following order:
	 * 1: facility name ; 2: the name of the type of facility ; 3: facility's capacity
	 * 
	 * @param params The array of parameters for build the facility DTO
	 * @return a facility DTO with the parameters given in the array
	 */
	private static Facility createFacilityDTO(Object[] params) {
		return new Facility((String) params[0], (String) params[1], (Integer) params[2]);
	}

	/**
	 * Creates a DTO Session object given an array of objects
	 * It's important that the array have 3 elements and the following order:
	 * 1: class name ; 2: start time of the session ; 3: end time of the session
	 * 
	 * @param params The array of parameters for build the session DTO
	 * @return a session DTO with the parameters given in the array
	 */
	private static Session createSessionDTO(Object[] params) {
		return new Session((String) params[0], (LocalTime) params[1], (LocalTime) params[2]);
	}

	/**
	 * Creates a DTO Class object given an array of objects
	 * It's important that the array have 5 elements and the following order:
	 * 1: class name ; 2: startTime of the class ; 3: daysOfWeek of the class ;
	 * 4: facility Name ; 5: number free spaces in the class
	 * 
	 * @param params The array of parameters for build the class DTO
	 * @return a class DTO with the parameters given in the array
	 */
	@SuppressWarnings("unchecked")
	private static Class createClassDTO(Object[] params) {	
		return new Class((String) params[0], (LocalTime) params[1], 
				(List<DayOfWeek>) params[2], (String) params[3], (int) params[4]);
	}

}
