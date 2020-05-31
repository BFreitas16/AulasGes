package facade.exceptions;

/**
 * This class represents an exception that is thrown when
 * something in enroll Class use goes wrong:
 * 		- the user already enrolled the class session
 *  
 * @author fc51468
 * @version 1.1 (07/04/2020)
 *
 */
public class UserAlreadyEnrolledExeption extends ApplicationException{

	/**
	 * The serial version id (generated automatically by Eclipse)
	 */
	private static final long serialVersionUID = -7740589361682413113L;

	public UserAlreadyEnrolledExeption(String session, String u) {
		super("The session " + session + " already has the user " + u);
	}

}
