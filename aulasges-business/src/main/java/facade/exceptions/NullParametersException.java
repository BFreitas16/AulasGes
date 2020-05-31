package facade.exceptions;

/**
 * This class represents an exception that is thrown when
 * something in the uses cases goes wrong:
 * 		- a parameter is null
 *  
 * @author fc51468
 * @version 1.1 (07/04/2020)
 *
 */
public class NullParametersException extends ApplicationException {

	/**
	 * The serial version id (generated automatically by Eclipse)
	 */
	private static final long serialVersionUID = 3168867040149906698L;

	public NullParametersException() {
		super("Invalid parameters. Parameters cant be null");
	}
	
	

}
