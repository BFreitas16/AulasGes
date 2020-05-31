package business.domain.facilities;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import business.domain.classes.Session;
import business.domain.modalities.Modality;

/**
 * The Facility
 * 
 * @author fC51468
 * @version 1.1 (29/03/2020)
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name=Facility.FIND_ALL, query="SELECT f FROM Facility f"),
	@NamedQuery(name=Facility.FIND_BY_NAME, query="SELECT f FROM Facility f WHERE f.name = :" + 
			Facility.FIND_BY_NAME_NAME)
})
public class Facility {
	
	// Named query name constants
	public static final String FIND_BY_NAME = "Facility.findByName";
	public static final String FIND_ALL = "Facility.findAll";
	public static final String FIND_BY_NAME_NAME = "name";
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	/**
	 * Facility name
	 */
	@Column(nullable = false, unique = true)
	private String name;

	/**
	 * Facility type
	 */
	@Enumerated(EnumType.STRING) 
	@Column(nullable = false)
	private FacilityType type;

	/**
	 * The facility's capacity
	 */
	@Column(nullable = false)
	private int capacity;

	/**
	 * Group of sessions organized by date
	 */
	@OneToMany(mappedBy="facility")
	private List<Session> sessions = new ArrayList<>();

	/**
	 * Valid Modalities in this Facility
	 */
	@ManyToMany
	private List<Modality> validModalities = new ArrayList<>();
	
	/**
	 * Constructor needed by JPA.
	 */
	Facility() {}

	/**
	 * Creates a new Facility with a name, a type and a capacity
	 * 
	 * @param name The name of facility
	 * @param type The type of facility
	 * @param capacity The max capacity of facility
	 */
	public Facility(String name, FacilityType type, int capacity) {
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
	public FacilityType getType() {
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

	/**
	 * Adds a new valid modality to facility
	 * 
	 * @param modalidade The new modality 
	 * @return true if added, false otherwise
	 */
	public boolean addModalidade(Modality modality) {
		if (validModalities.contains(modality))
			return false;

		validModalities.add(modality);
		return true;
	}

	/**
	 * Adds a new session to this facility
	 * 
	 * @param date The date to add the session
	 * @param s The session to be added
	 */
	public void addSession(Session s) {
		this.sessions.add(s);
	}

	/**
	 * Get the occupation in a specific date
	 * 
	 * @param date The date to see the occupation
	 * @return a list of sessions in that date
	 */
	public List<Session> getOccupation(LocalDate date) {
		
		List<Session> ss = new ArrayList<>();

		for (Session s : this.sessions) {
			if (s.getDate().isEqual(date))
				ss.add(s);
		}

		return ss;
	}

	/**
	 * Checks if modality is supported in this facility
	 * 
	 * @param modality The modality to check
	 * @return true if allowed, false otherwise
	 */
	public boolean allowsModality(Modality modality) {
		return validModalities.contains(modality);
	}

	/**
	 * Checks in a period of time if the facility is free for a part
	 * of the day to do new regular session (sessions in certain days of
	 * the week all with the same start and end time)
	 * 
	 * @param from The start date
	 * @param to The end date
	 * @param startTime The start time
	 * @param duration The duration of the sessions
	 * @param daysOfWeek The sessios's days of the week
	 * @return true if it free for ALL sessions in the period, false otherwise
	 */
	public boolean isFreeFromTo(LocalDate from, LocalDate to, 
			LocalTime startTime, int duration, List<DayOfWeek> daysOfWeek) {

		// the endTime of all session
		LocalTime endTime = startTime.plusMinutes(duration);		

		// Iterates all days between FROM to TO and for each
		for (LocalDate date = from; date.isBefore(to.plusDays(1)); date = date.plusDays(1)) {

			// if it a valid day of week
			if (daysOfWeek.contains(date.getDayOfWeek())) {
				
				// get the sessions of that day
				List<Session> ss = this.getOccupation(date);

				// NOTE: if there is no session its free to add the session in that day
				if (ss != null) {
					// there is a collision
					if ( sessionCollideWithAPeriod(ss, startTime, endTime) )
						return false;
				}

			}

		}

		// there is no collision so its free to add
		return true;
	}

	/**
	 * Checks if there is a collision in a list of sessions with a given period
	 * 
	 * @param sessions The list of sessions
	 * @param startTime The start time of the period
	 * @param endTime The end time of the period
	 * @return true if there is a collision, false otherwise
	 */
	private boolean sessionCollideWithAPeriod(List<Session> sessions, 
			LocalTime startTime, LocalTime endTime) {

		for (Session s: sessions) {
			if (s.collides(startTime, endTime))
				return true;
		}

		return false;
	}

	/**
	 * Verifies if it is a valid capacity
	 * 
	 * @param c The capacity to verify 
	 * @return true if the capacity is greater or equal than a capacity
	 */
	public boolean checkCapacity(int c) {
		return this.capacity >= c;
	}
	
	@Override
	public String toString() {
		return "Facility: " + this.name + " with type: " + this.type.name() + 
				"\nCapacity: " + this.capacity;
	}

}
