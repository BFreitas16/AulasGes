package facade.exceptions;

/**
 * This class represents an exception that is thrown when
 * something in activate Class use case goes wrong:
 * 		- class is already active
 * 		- is not given a valid future period
 * 		- the modality of the class is not allowed in the facility
 * 		- the facility doesnt support the number of people
 * 		- the facility is not free in the given period
 *  
 * @author fc51468
 * @version 1.1 (07/04/2020)
 *
 */
public class ActivateClassException extends ApplicationException{

	/**
	 * The serial version id (generated automatically by Eclipse)
	 */
	private static final long serialVersionUID = 8273078737649669722L;

	public ActivateClassException(String message) {
		super(message);
	}

}
