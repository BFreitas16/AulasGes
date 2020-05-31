package facade.dto;

import java.io.Serializable;

public class Facility implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3807887137482919668L;

	/**
	 * Facility name
	 */
	private String name;
	
	/**
	 * Facility type
	 */
	private String type;
	
	/**
	 * The facility's capacity
	 */
	private int capacity;
	
	/**
	 * Creates a new Facility with a name, a type and a capacity
	 * 
	 * @param name The name of facility
	 * @param type The type of facility
	 * @param capacity The max capacity of facility
	 */
	public Facility(String name, String type, int capacity) {
		this.name= name;
		this.type= type;
		this.capacity= capacity;
	}
	
	/**
	 * Gets the name of facility
	 * 
	 * @return name of facility
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Gets the type of facility
	 * 
	 * @return type of facility
	 */
	public String getType() {
		return this.type;
	}
	
	/**
	 * Get the capacity of the facility
	 * 
	 * @return capacity 
	 */
	public int getCapacity() {
		return this.capacity;
	}
	
	@Override
	public String toString() {
		return "Facility: " + this.name + " with type: " + this.type +
				"\nCapacity: " + this.capacity;
	}

}
