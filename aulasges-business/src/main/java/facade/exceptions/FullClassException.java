package facade.exceptions;

/**
 * This class represents an exception that is thrown when
 * something in enroll Class use case goes wrong:
 * 		- the class is full 

 *  
 * @author fc51468
 * @version 1.1 (07/04/2020)
 *
 */
public class FullClassException extends ApplicationException {

	/**
	 * The serial version id (generated automatically by Eclipse)
	 */
	private static final long serialVersionUID = 2002868257062418046L;

	public FullClassException(String name) {
		super("The class " + name + " you are trying to enroll is full");
	}

}
