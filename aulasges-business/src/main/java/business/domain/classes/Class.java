package business.domain.classes;

import static javax.persistence.CascadeType.ALL;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Version;

import business.domain.facilities.Facility;
import business.domain.modalities.Modality;
import business.domain.users.User;
import dbutils.converters.LocalTimeConverter;
import facade.exceptions.ActivateClassException;
import facade.exceptions.ApplicationException;
import util.Clock;

/**
 * A Class
 * 
 * @author fC51468
 * @version 1.1 (29/03/2020)
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name=Class.FIND_BY_NAME, query="SELECT c FROM Class c WHERE c.name = :" + 
			Class.FIND_BY_NAME_NAME),
	@NamedQuery(name=Class.FIND_BY_MODALITY, query="SELECT c FROM Class c WHERE c.modality =:" +
			Class.FIND_BY_MODALITY_MODALITY),
	@NamedQuery(name=Class.FIND_ACTIVE_BY_MODALITY, query = "SELECT c.currentactiveClass FROM Class c "
			+ "WHERE c.modality =:" + Class.FIND_BY_MODALITY_MODALITY_MODALITY)
})
public class Class {
	
	// Named query name constants
	public static final String FIND_BY_NAME = "Class.findByName";
	public static final String FIND_BY_NAME_NAME = "name";
	public static final String FIND_BY_MODALITY = "Class.findByModality";
	public static final String FIND_BY_MODALITY_MODALITY = "modality";
	public static final String FIND_ACTIVE_BY_MODALITY = "Class.findActiveByModality";
	public static final String FIND_BY_MODALITY_MODALITY_MODALITY = "modality";
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO) 
	private int id;
	
	@Version
    private int version;
	
	/**
	 * The Class name
	 */
	@Column(nullable = false, unique = true) private String name;

	/**
	 * The modality of this class
	 */
	@ManyToOne
	private Modality modality;

	/**
	 * The days of week for the sessions of this class
	 */
	@Column(name = "days", nullable = false)
	@ElementCollection 
	private List<String> daysOfWeek = new ArrayList<>();

	/**
	 * The sessios's startTime of this class
	 */
	@Column(nullable = false)
	@Convert(converter = LocalTimeConverter.class)
	private LocalTime startTime;
	
	/**
	 * The end time of class
	 */
	@Column(nullable = false)
	@Convert(converter = LocalTimeConverter.class)
	private LocalTime endTime;

	/**
	 * The duration of each session of this class
	 */
	@Column(nullable = false)
	private int duration;
	
	@OneToOne(cascade = ALL)
	private ActiveClass currentactiveClass;
	
	/**
	 * List of all past active classes to have a track of them
	 */
	// OrderColumn is needed because the last element of this list is the active class
	// so we need to have a way to track the order of the occurrences of activeClasses
	@OneToMany(cascade = ALL, mappedBy = "c")
	private List<ActiveClass> pastActiveClasses = new ArrayList<>();
	
	/**
	 * Constructor needed by JPA.
	 */
	Class () {}

	/**
	 * Creates a class with a modality, a name and will have
	 * sessions in some days of the week all with the same given start time
	 * and all with the same duration
	 * 
	 * @param mod The modality of the class
	 * @param name The class's name
	 * @param daysOfWeek The days of week of this class
	 * @param startTime The start time of the class
	 * @param duration The duration of the class
	 */
	public Class(Modality mod, String name, 
			List<DayOfWeek> daysOfWeek, LocalTime startTime, int duration) {
		this.modality = mod;
		this.name = name;
		this.startTime = startTime;
		this.duration = duration;
		this.endTime = this.startTime.plusMinutes(duration);
		
		//Convert Days of Week to String (easier to persist)
		this.daysOfWeek = daysOfWeek.stream()
									.map(s -> s.toString())
									.collect(Collectors.toList());
	}

	/**
	 * 
	 * @return the name of class
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * 
	 * @return the modality of class
	 */
	public Modality getModality() {
		return this.modality;
	}

	/**
	 * 
	 * @return the class start time
	 */
	public LocalTime getStartTime() {
		return this.startTime;
	}
	
	/**
	 * 
	 * @return the class end time
	 */
	public LocalTime getEndTime() {
		return this.endTime;
	}
	
	/**
	 * 
	 * @return the duration of the class
	 */
	public int getDuration() {
		return this.duration;
	}

	/**
	 * 
	 * @return the days of week of the class
	 */
	public List<DayOfWeek> getDaysOfWeek(){
		//converts to List of String to List of DaysOfWeek
		return this.daysOfWeek.stream().map(s -> DayOfWeek.valueOf(s)).collect(Collectors.toList());
	}
	
	/**
	 * 
	 * @return The current ActiveClass 
	 */
	public ActiveClass getCurrentActiveClass() {
		return this.currentactiveClass;
	}

	/**
	 * 
	 * @return the facility of the class or null if there 
	 * is no sessions (no sessions = no facility for the class)
	 */
	public Facility getFacility() {
		return this.currentactiveClass == null ? null :
			this.currentactiveClass.getFacility();
	}

	/**
	 * Get the price of a session of this class
	 * 
	 * @return the cost of a sessions of this class
	 */
	public double getPriceOfASession() {
		return this.modality.getHourPrice() * this.duration / 60;
	}

	/**
	 * Get the number of sessions per week
	 * 
	 * @return the number of session per week
	 */
	public int getSessionsNumberPerWeek() {
		return this.daysOfWeek.size();
	}

	/**
	 * Checks if a name is valid.
	 * 
	 * @param name The name to be checked.
	 * @return Whether the name is valid. 
	 */
	public static boolean isValidNameForAClass(String name) {
		// the name doesn't have 6 chars OR
		// the name doesn't have at least 3 alphanumeric chars OR
		// the name doesn't have at least one type of space
		return name.length() == 6 &&
				name.matches(".*[a-zA-Z0-9].*[a-zA-Z0-9].*[a-zA-Z0-9].*") &&
				!name.matches(".*\\s.*");
	}

	/**
	 * verifies if the class status is ACTIVE
	 * 
	 * @return true if is ACTIVE, false otherwise
	 */
	public boolean isActice() {
		return this.currentactiveClass != null && 
				this.currentactiveClass.isActive();
	}
	
	/**
	 * 
	 * @return true if there is a session in the next 24h
	 */
	public boolean hasSessionInNext24h() {
		return this.currentactiveClass != null &&
				this.currentactiveClass.hasSessionInNext24h();
	}
	
	/**
	 * 
	 * @return true if it is possible to add a regular person to the class
	 */
	public boolean isPossibleToSubscribeRegualPerson() {
		return this.currentactiveClass != null &&
				this.currentactiveClass.isPossibleToSubscribeRegualPerson();
	}

	/**
	 * Get the number of places from the next 24h of time Session of the 
	 * active class
	 * 
	 * @param time The time before the next session
	 * @return the number of free places in the next session or 0 if there is 
	 * no free space or there isn't a session in the next 24h
	 */
	public int getNumberOfPlacesFromNext24hSession() {
		return this.currentactiveClass == null ? 0 : 
			this.currentactiveClass.getNumberOfPlacesFromNext24hSession();
	}

	/**
	 * Get the number of places for the class in the monthly registration type
	 * 
	 * @return the number of free monthly places in the class
	 */
	public int getNumberOfPlacesRegularSubscriptions() {
		return this.currentactiveClass == null ? 0 :
			this.currentactiveClass.getNumberOfPlacesRegularSubscriptions();
	}
	
	/**
	 * Enroll a consumer in the active class in regular mode
	 * 
	 * @param u The consumer to subscribe
	 * @param date The date to enroll the subscription
	 * @throws ApplicationException 
	 */
	public void soloSubscriptionOfConsumer(User u) throws ApplicationException {
		this.currentactiveClass.soloSubscriptionOfConsumer(u);
	}
	
	/**
	 * Enroll a consumer in the active class in regular mode
	 * 
	 * @param u The consumer to subscribe
	 * @throws ApplicationException 
	 */
	public void regularSubscriptionOfConsumer(User u) throws ApplicationException {
		this.currentactiveClass.regularSubscriptionOfConsumer(u);
	}

	/**
	 * Activate a Class and creates its sessions
	 * 
	 * @param facility The facility to add the class
	 * @param from The start date
	 * @param to The end date
	 * @param max The max number of people in a class
	 * @throws ApplicationException When the dates from - to don't define a future period, 
	 * or facility doesn't support the modality of the class or it's not free in some 
	 * period of from - to date or doesn't support the amount of people given as max
	 */
	public void activateClassAndCreateSessions(Facility facility, 
			LocalDate from, LocalDate to, int max) throws ApplicationException {

		// make all verifications
		activateClassVerifications(facility, from, to, max);
		
		// adds the active class to the past ActiveClasses NOTE: needs to verify if it's != null
		// because the first time the method is called (to not put a null in the list)
		if (this.currentactiveClass != null)
			this.pastActiveClasses.add(this.currentactiveClass);
		
		// make the new current active class
		this.currentactiveClass = new ActiveClass(this, from, to, max, facility);
	}
	
	/**
	 * Verifies if all the parameters are valid to make this class active
	 * 
	 * @param facility The facility where will be the sessions
	 * @param from The start date
	 * @param to The end date
	 * @param max The max people for the class
	 * @throws ApplicationException When the dates from - to don't define a future period, 
	 * or facility doesn't support the modality of the class or it's not free in some 
	 * period of from - to date or doesn't support the amount of people given as max
	 */
	private void activateClassVerifications(Facility facility, 
			LocalDate from, LocalDate to, int max) throws ApplicationException{
		
		// check if from-to is a valid future period -> minus 1 to allow active today
		LocalDate now = Clock.getDate();
		if (!from.isAfter(now.minusDays(1)) || !to.isAfter(now) || !from.isBefore(to))
			throw new ActivateClassException("The date "+ from.toString() + 
					" to " + to.toString() + " is not valid future period");

		// checks if modality is allowed in the facility
		if (!facility.allowsModality(this.modality))
			throw new ActivateClassException("Facility "+ facility.getName() + 
					" doesnt support " + this.modality.getName());

		// check if the facility support the max number of people
		if (!facility.checkCapacity(max))
			throw new ActivateClassException("Facility "+ facility.getName() + 
					" doesnt support " + max + " number of people");

		// check if is free in from-to
		if (!facility.isFreeFromTo(from, to, this.startTime, this.duration, this.getDaysOfWeek()))
			throw new ActivateClassException("Facility "+ facility.getName() + 
					" isnt free at " + from.toString() + " to " + to.toString() );
		
	}
	
	@Override
	public String toString() {
		int numOfLessons = this.currentactiveClass == null ? 0 : this.getCurrentActiveClass().getNumOfSessions();
		
		return "Class: " + this.name + " of " + this.modality.getName() +
				" (" + this.daysOfWeek.toString() + " starting at " + this.startTime + ")" +
				"\nActive: " + isActice() + 
				"\nNumber of lessons: " + numOfLessons;
	}

}
