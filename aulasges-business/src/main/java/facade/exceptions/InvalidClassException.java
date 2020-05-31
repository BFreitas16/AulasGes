package facade.exceptions;

/**
 * This class represents an exception that is thrown when
 * something in Class goes wrong:
 * 		- the class doesnt exists
 *  
 * @author fc51468
 * @version 1.1 (07/04/2020)
 *
 */
public class InvalidClassException extends ApplicationException{

	/**
	 * The serial version id (generated automatically by Eclipse)
	 */
	private static final long serialVersionUID = 6547205208798530934L;

	public InvalidClassException(String name) {
		super("Class "+ name + " doesnt exists");
	}

}
