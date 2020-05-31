package business.domain.modalities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * A Modality
 * 
 * @author fC51468
 * @version 1.1 (29/03/2020)
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name=Modality.FIND_ALL, query="SELECT m FROM Modality m"),
	@NamedQuery(name=Modality.FIND_BY_NAME, query="SELECT m FROM Modality m WHERE m.name = :" + 
			Modality.FIND_BY_NAME_NAME)
})
public class Modality {
	
	// Named query name constants
	public static final String FIND_BY_NAME = "Modality.findByName";
	public static final String FIND_ALL = "Modality.findAll";
	public static final String FIND_BY_NAME_NAME = "name";
	
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	/**
	 * The modality name
	 */
	@Column(nullable = false, unique = true) private String name;
		
	/**
	 * The modality's minimum duration
	 */
	@Column(nullable = false)
	private int minDuration;
	
	/**
	 * The cost of an hour
	 */
	@Column(nullable = false)
	private double hourPrice;
	
	/**
	 * Constructor needed by JPA.
	 */
	Modality () {}
	
	/**
	 * Creates a Modality with a name, the minimum duration and a price per hour
	 * 
	 * @param name The modality name
	 * @param minDuration The minimum duration of the modality
	 * @param hourPrice The price per hour
	 */
	public Modality(String name, int minDuration, double hourPrice) {
		this.name = name;
		this.minDuration = minDuration;
		this.hourPrice = hourPrice;
	}

	/**
	 * Get the hour price of the modality
	 * 
	 * @return the cost of an hour
	 */
	public double getHourPrice() {
		return this.hourPrice;
	}
	
	/**
	 * Get the name of the modality
	 * 
	 * @return the modality's name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Get the minimum duration for the modality
	 * 
	 * @return the modality's minimum duration
	 */
	public int getMinimumDuration() {
		return this.minDuration;
	}
	
	@Override
	public String toString() {
		return "Modality: " + this.name + ": " + 
				"\nMinimum duration" + this.minDuration + " Price per hour " + this.hourPrice;
	}

}
