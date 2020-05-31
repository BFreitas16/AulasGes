package facade.exceptions;

/**
 * This class represents an exception that is thrown when
 * something in create Class use case goes wrong:
 * 		- the name is not valid for a class
 * 		- the class already exists
 * 		- the duration of the class is not valid
 *  
 * @author fc51468
 * @version 1.1 (07/04/2020)
 *
 */
public class CreateClassException extends ApplicationException{

	/**
	 * The serial version id (generated automatically by Eclipse)
	 */
	private static final long serialVersionUID = -6305592031720283194L;

	public CreateClassException(String message) {
		super(message);	
	}

}
