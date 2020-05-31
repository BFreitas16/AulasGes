package facade.dto;

import java.io.Serializable;

/**
 * A Modality DTO
 * 
 * @author fC51468
 * @version 1.1 (29/03/2020)
 * 
 */
public class Modality implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1800283285403216489L;
	
	/**
	 * The modality name
	 */
	private String name;
	
	/**
	 * Creates a new Modality with a name and a description
	 * 
	 * @param name The modality's name
	 * @param description The modality's description
	 */
	public Modality(String name) {
		this.name = name;
	}
	
	/**
	 * Get the name of the modality
	 * 
	 * @return the modality's name
	 */
	public String getName() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return "Modality " + this.name;
	}
}
