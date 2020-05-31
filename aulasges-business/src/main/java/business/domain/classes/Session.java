package business.domain.classes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Version;

import business.domain.facilities.Facility;
import business.domain.users.User;
import dbutils.converters.LocalDateConverter;
import facade.exceptions.ApplicationException;
import facade.exceptions.UserAlreadyEnrolledExeption;

/**
 * A Session - the true class for the consumer to participate
 * 
 * @author fC51468
 * @version 1.1 (29/03/2020)
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name=Session.FIND_BY_DATE, query="SELECT s FROM Session s WHERE s.date = :" + 
			Session.FIND_BY_DATE_DATE + " AND s.facility = :" + Session.FIND_BY_DATE_FACILITY_NAME)
})
public class Session {
	
	// Named query name constants
	public static final String FIND_BY_DATE = "Session.findByDate";
	public static final String FIND_BY_DATE_DATE = "date";
	public static final String FIND_BY_DATE_FACILITY_NAME = "facility";
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Version
    private int version;
	
	/**
	 * The activeClass that this sessions belongs to
	 */
	@ManyToOne @JoinColumn
	private ActiveClass activeClass;
	
	/**
	 * The date of this lesson
	 */
	@Column(name = "date", nullable = false)
	@Convert(converter = LocalDateConverter.class)
	private LocalDate date;
	
	/**
	 * Session facility
	 */
	@ManyToOne
	private Facility facility;
	
	/**
	 * The current free space, can be negative because
	 * the special case user in regular subscription
	 */
	@Column(nullable = false)
	private int freeSpace;
	
	/**
	 * 
	 */
	@ManyToMany
	private List<User> usersInTheSession = new ArrayList<>();
	
	

	/**
	 * Constructor needed by JPA.
	 */
	Session () {}
	
	/**
	 * Creates a new Session of a class with a start and end time
	 * 
	 * @param name The name of the class of this session
	 * @param startTime The start time of the session
	 * @param endTime The end time of the lesson
	 * @param max The initial free space in session
	 */
	public Session(ActiveClass activeClass, LocalDate date, int max) {
		this.activeClass = activeClass;
		this.facility = this.activeClass.getFacility();
		
		// schedule
		this.date = date;
		
		// places for people
		this.freeSpace = max;
	}
	
	/**
	 * Get the name of the class of this session
	 * 
	 * @return the name of the class of this session
	 */
	public String getName() {
		return this.activeClass.getName();
	}
	
	/**
	 * Get the start time of the session
	 * 
	 * @return the start time of the lesson
	 */
	public LocalTime getStartTime() {
		return this.activeClass.getStartTime();
	}
	
	/**
	 * Get the end time of the session
	 * 
	 * @return the end time of the lesson
	 */
	public LocalTime getEndTime() {
		return this.activeClass.getEndTime();
	}

	/**
	 * 
	 * @return the free places in the session
	 */
	public int getFreeSpace() {
		return this.freeSpace;
	}

	/**
	 * 
	 * @return the date of the lesson
	 */
	public LocalDate getDate() {
		return this.date;
	}

	/**
	 * Verifies if a session period collides with a specified period
	 * 
	 * @param startTime The start time of period
	 * @param endTime The end time of period
	 * @return true if collides, false otherwise
	 */
	public boolean collides(LocalTime startTime, LocalTime endTime) {
		// if it starts at the same time, the end time is not checked because 
		// a period is valid if it starts when this session ends
		if (this.getStartTime().equals(startTime))
			return true;
		
		// if period contains this session period
		if (this.getStartTime().isBefore(startTime) && this.getEndTime().isAfter(endTime))
			return true;
		
		// if period is between this session period
		if (this.getStartTime().isAfter(startTime) && this.getStartTime().isBefore(endTime))
			return true;
		
		// if end time is between the period of this session
		if (this.getEndTime().isAfter(startTime) && this.getEndTime().isBefore(endTime))
			return true;
		
		// if start time is between the period of this session
		if (this.getStartTime().isAfter(startTime) && this.getStartTime().isBefore(endTime))
			return true;
		
		return false;
	}

	/**
	 * Add a user to the session
	 * 
	 * @param u The user to add
	 */
	public void addUser(User u) throws ApplicationException {	
		if(this.usersInTheSession.contains(u))
			throw new UserAlreadyEnrolledExeption(this.getName(), u.getName());
		
		this.usersInTheSession.add(u);
		// it will be possible to be negative to calculate the
		// the excess of people in this session (Math.abs(0 - freeSpace))
		this.freeSpace--;
	}

	@Override
	public String toString() {
		return "Session of class: " + this.getName() + " (" + this.getStartTime().toString() +
				" - " + this.getEndTime().toString() + " in " + this.date.toString() + ")" +
				"\nFreeSpace: " + this.freeSpace;
	}
	
}
