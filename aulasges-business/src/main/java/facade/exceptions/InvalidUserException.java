package facade.exceptions;

/**
 * This class represents an exception that is thrown when
 * something in enroll Class use case goes wrong:
 * 		- the user number is invalid
 *  
 * @author fc51468
 * @version 1.1 (07/04/2020)
 *
 */
public class InvalidUserException extends ApplicationException{

	/**
	 * The serial version id (generated automatically by Eclipse)
	 */
	private static final long serialVersionUID = -8166039337131340287L;

	public InvalidUserException(int consumerNumber) {
		super("Consumer with the consumer number '" + 
				consumerNumber + "' doesn't exist.");
	}

}
