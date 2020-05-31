package facade.exceptions;

/**
 * This class represents an exception that is thrown when
 * something in Modality goes wrong:
 * 		- the modality doesnt exists
 *  
 * @author fc51468
 * @version 1.1 (07/04/2020)
 *
 */
public class InvalidModalityException extends ApplicationException {

	/**
	 * The serial version id (generated automatically by Eclipse)
	 */
	private static final long serialVersionUID = 6120037327401528163L;

	public InvalidModalityException(String name) {
		super("Modality with the name '" + name + "' doesn't exist.");
	}

}
