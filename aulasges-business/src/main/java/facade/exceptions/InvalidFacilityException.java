package facade.exceptions;

/**
 * This class represents an exception that is thrown when
 * something in Facility goes wrong:
 * 		- the facility doesnt exists
 *  
 * @author fc51468
 * @version 1.1 (07/04/2020)
 *
 */
public class InvalidFacilityException extends ApplicationException{

	/**
	 * The serial version id (generated automatically by Eclipse)
	 */
	private static final long serialVersionUID = 879833593866321231L;

	public InvalidFacilityException(String name) {
		super("Facility "+ name + " doesnt exists");
	}

}
