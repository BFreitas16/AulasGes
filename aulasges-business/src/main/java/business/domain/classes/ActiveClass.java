package business.domain.classes;

import static javax.persistence.CascadeType.ALL;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import business.domain.facilities.Facility;
import business.domain.users.User;
import dbutils.converters.LocalDateConverter;
import facade.exceptions.ApplicationException;
import facade.exceptions.FullClassException;
import util.Clock;

@Entity
public class ActiveClass {
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Version
    private int version;
	
	/**
	 * The class of this active class
	 */
	@ManyToOne @JoinColumn
	private Class c;
	
	/**
	 * The start date
	 */
	@Column(nullable = false)
	@Convert(converter = LocalDateConverter.class)
	private LocalDate initialDate;
	
	/**
	 * The end date
	 */
	@Column(nullable = false)
	@Convert(converter = LocalDateConverter.class)
	private LocalDate endDate;
	
	/**
	 * The max people in a class
	 */
	@Column(nullable = false)
	private int max;
	
	/**
	 * The facility of the active class
	 */
	@ManyToOne @JoinColumn
	private Facility facility;
	
	/**
	 * The sessions of this class
	 */
	@OneToMany(cascade = ALL, mappedBy = "activeClass")
	private List<Session> sessions = new ArrayList<>();
	
	/**
	 * The number of free places for monthly people
	 */
	@Column(nullable = false)
	private int numRegularUsers = 0;
	
	/**
	 * Constructor needed by JPA.
	 */
	ActiveClass () {}
	
	public ActiveClass(Class c, LocalDate from, LocalDate to, int max, Facility f) {
		this.c = c;
		this.max = max;
		this.facility = f;
		this.initialDate = from;
		this.endDate = to;
		
		createSessionsAndAddToFacility();
	}
	
	/**
	 * 
	 * @return the name of class
	 */
	public String getName() {	
		return this.c.getName();
	}
	
	/**
	 * 
	 * @return the start time of class
	 */
	public LocalTime getStartTime() {
		return this.c.getStartTime();
	}
	
	/**
	 * 
	 * @return the end time of class
	 */
	public LocalTime getEndTime() {
		return this.c.getEndTime();
	}
	
	/**
	 * 
	 * @return the facility of the lesson
	 */
	public Facility getFacility() {
		return this.facility;
	}
	
	/**
	 * 
	 * @return The days of week of this activeClass
	 */
	public Object getDaysOfWeek() {
		return this.c.getDaysOfWeek();
	}
	
	/**
	 * 
	 * @return the number of sessions of this active class
	 */
	public int getNumOfSessions() {
		return this.sessions.size();
	}
	
	/**
	 * verifies if the class status is ACTIVE
	 * 
	 * @return true if is ACTIVE, false otherwise
	 */
	public boolean isActive() {
		LocalDate now = Clock.getDate();
	
		// class is active if now is before or equal to LocalDate To (the end of period)
		// this makes the ActiveClass active when the manager activates the class
		return (this.endDate.isEqual(now) || this.endDate.isAfter(now));
	}
	
	/**
	 * Get the price of a session of this class
	 * 
	 * @return the cost of a sessions of this class
	 */
	public double getPriceOfASession() {
		return this.c.getPriceOfASession();
	}
	
	/**
	 * Get the number of sessions per week
	 * 
	 * @return the number of session per week
	 */
	public int getSessionsNumberPerWeek() {
		return this.c.getSessionsNumberPerWeek();
	}
	
	/**
	 * 
	 * @return true if there is a session in the next 24h
	 */
	public boolean hasSessionInNext24h() {
		Session nextSession = nextSessionFromNow();
		if (nextSession == null)
			return false;
		
		LocalDate sessionDate = nextSession.getDate();
		
		LocalDate today = Clock.getDate();
		LocalTime now = Clock.getTime();
		
		// if today and after now hours is equal to sessionDate or sessionDate
		// is equal to tomorrow there is a session in the next 24h
		return ((sessionDate.isEqual(today) && nextSession.getStartTime().isAfter(now)) 
				|| sessionDate.isEqual(today.plusDays(1)));
	}
	
	/**
	 * 
	 * @return true if it is possible to add a regular person to the class
	 */
	public boolean isPossibleToSubscribeRegualPerson() {
		if (nextSessionFromNow()== null)
			return false;
		
		return getNumberOfPlacesRegularSubscriptions() > 0;
	}
	
	/**
	 * Get the number of places from the next 24h of time Session
	 * 
	 * @param time The time before the next session
	 * @return the number of free places in the next session or 0 if there is 
	 * no free space or there isn't a session in the next 24h
	 */
	public int getNumberOfPlacesFromNext24hSession() {
		Session nextSession = theNextSessionIn24H();
		
		return nextSession == null ? 0 : nextSession.getFreeSpace();
	}
	
	/**
	 * Get the number of places for the class in the monthly registration type
	 * 
	 * @return the number of free monthly places in the class
	 */
	public int getNumberOfPlacesRegularSubscriptions() {
		return this.sessions.isEmpty() ? 0 : 
			this.max - this.numRegularUsers;
	}
	
	/**
	 * Enroll a consumer in the class in regular mode
	 * 
	 * @param u The consumer to subscribe
	 * @param date The date to enroll the subscription
	 * @throws ApplicationException 
	 */
	public void soloSubscriptionOfConsumer(User u) throws ApplicationException {
		Session nextSession = theNextSessionIn24H();
		
		// no need to verify because this method is only called after the
		// hasSessionInNext24h so there is certainly a session in
		// the next 24H BUT is better to "DOUBLE CHECK to prevent a wreck"
		if (nextSession != null) {
			// next session is full
			if (nextSession.getFreeSpace() < 1)
				throw new FullClassException(this.c.getName());
			
			nextSession.addUser(u);
		}
	}
	
	/**
	 * Enroll a consumer in the class in regular mode
	 * 
	 * @param u The consumer to subscribe
	 * @throws ApplicationException 
	 */
	public void regularSubscriptionOfConsumer(User u) throws ApplicationException {
		if (this.numRegularUsers >= this.max)
			throw new FullClassException(this.c.getName());
		
		addUserToAllSession(u);
		this.numRegularUsers++;
	}
	
	/**
	 * Add a user to all session of the class
	 * 
	 * @param u The user to add
	 * @throws ApplicationException When there is no more session of the class
	 */
	public void addUserToAllSession(User u) throws ApplicationException {
		if (nextSessionFromNow() == null)
			throw new ApplicationException("User " + u.getName() + " Can't be added. "
					+ "There is no more future session of the class.");
		
		for (Session s : this.sessions) {
			// need to check if the session is after now to add him only
			// to the future sessions and not the past session too
			if (isSessionInNext24hOrAfterNow(s, false))
				s.addUser(u);
		}
	}
	
	//////////////////////////
	//////   PRIVATES   //////
	//////////////////////////
	
	/**
	 * Create the sessions and add them to the facility
	 * 
	 * @param facility The facility to add session
	 * @param from The start date
	 * @param to The end date
	 * @param max The free space initial
	 */
	private void createSessionsAndAddToFacility() {	

		// Iterates all days between From to To and for each
		for (LocalDate date = this.initialDate; date.isBefore(this.endDate.plusDays(1)); date = date.plusDays(1)) {

			// check if it is an allowed day of week for this class
			if (this.c.getDaysOfWeek().contains(date.getDayOfWeek())) {
				// creates the session and adds it to the facility
				Session s = createSession(date);
				this.facility.addSession(s);
			}

		}

		// sort the list by its date : useful when is needed get
		// the next session from a specific time
		Collections.sort(this.sessions, 
				(s1, s2) -> s1.getDate().compareTo(s2.getDate()));
	}
	
	/**
	 * Creates a session with this.name this.startTime and endTime
	 * 
	 * @param f The facility of the session
	 * @param date The date of the session
	 * @param endTime The time session ends
	 * @param max The initial free space in session
	 * @return New session created
	 */
	private Session createSession(LocalDate date) {
		
		Session s = new Session(this, date, this.max);
		sessions.add(s);

		return s;
	}
	
	
	/**
	 * 
	 * @return the next session or null if doesn't exist
	 */
	private Session nextSessionFromNow() {
		for (Session s : this.sessions) {
			if (isSessionInNext24hOrAfterNow(s, false))
					return s;
		}
		
		return null;
	}
	
	/**
	 * Check if a session is in the next 24h if it is passed a flag to
	 * indicate it or checks if a session is after now
	 * 
	 * @param s The session to check
	 * @param toCheckInNext24 The flag to say if it's in the next 24h
	 * @return true if the session is next from now, false otherwise
	 */
	private boolean isSessionInNext24hOrAfterNow(Session s, boolean toCheckInNext24) {
		// todays date and time
		LocalDate today = Clock.getDate();
		LocalTime now = Clock.getTime();
		
		LocalDate tomorrow = today.plusDays(1);
		
		// the session date
		LocalDate sessionDate = s.getDate();
		
		if (toCheckInNext24) {
			// if the session date is equals to the specified date and after the now 
			// time or if the session date is equals to one day after the date (24H) 
			// is the session we want because the sessions are sorted by date
			return ((sessionDate.isEqual(today) && s.getStartTime().isAfter(now)) || sessionDate.isEqual(tomorrow));
		}
		
		// check if it after Now
		return (sessionDate.isEqual(today) && s.getStartTime().isAfter(now)) || sessionDate.isAfter(today);
	}
	
	/**
	 * Get the next possible session in the next 24h
	 * 
	 * @return the next session in 24h or null if there isn't
	 */
	private Session theNextSessionIn24H() {
		// get the next session to see if it is in the next 24h
		Session s = nextSessionFromNow();
		if (s != null && isSessionInNext24hOrAfterNow(s, true)) {
			return s;
		}

		return null;
	}

}
