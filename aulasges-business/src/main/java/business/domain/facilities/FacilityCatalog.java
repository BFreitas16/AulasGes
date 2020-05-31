package business.domain.facilities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import business.domain.classes.Session;
import facade.exceptions.ApplicationException;

/**
 * Catalog of facilities
 * 
 * @author fC51468
 * @version 1.1 (29/03/2020)
 * 
 */
@Stateless
public class FacilityCatalog {
	
	@PersistenceContext
	private EntityManager em;
		
	/**
	 * Creates a new facility with a name, a typo, and a capacity
	 * 
	 * @param name The name of facility
	 * @param type The type of facility
	 * @param capacity The max capacity of the facility
	 */
	public void createFacility(String name, FacilityType type, int capacity) {
		Facility f = new Facility(name, type, capacity);
		em.persist(f);
	}

	/**
	 * Get a facility by a name
	 * 
	 * @param name The facility's name
	 * @requires name != null
	 * @return the facility with the given name
	 * @throws ApplicationException When doesn't exist a facility with
	 * the given name
	 */
	public Facility getFacility(String name) throws ApplicationException {
		try {
			TypedQuery<Facility> query = em.createNamedQuery(Facility.FIND_BY_NAME, Facility.class);
			query.setParameter(Facility.FIND_BY_NAME_NAME, name);
			return query.getSingleResult();
		}catch(Exception e) {
			throw new ApplicationException("Error obtaining the facility");
		}
	}

	/**
	 * Get all facilities
	 * 
	 * @return a list of all facilities
	 * @throws ApplicationException 
	 */
	public List<Facility> getAllFacilities() throws ApplicationException {
		try {
			TypedQuery<Facility> query = em.createNamedQuery(Facility.FIND_ALL, Facility.class);
			return query.getResultList();
		} catch(Exception e) {
			throw new ApplicationException("Error obtaining the facilities list");
		}
	}
	
	/**
	 * Get the occupation of a facility 
	 * 
	 * @param f the facility
	 * @param dateToReceive the date of occupation
	 * @return the list of sessions in a facility by date
	 */
	public List<Session> getSessionIn(Facility f, LocalDate dateToReceive){
		List<Session> sessions = new ArrayList<>();
		try {
			TypedQuery<Session> query = em.createNamedQuery(Session.FIND_BY_DATE, Session.class);
			query.setParameter(Session.FIND_BY_DATE_DATE, dateToReceive);
			query.setParameter(Session.FIND_BY_DATE_FACILITY_NAME, f);
			
			List<Session> records = query.getResultList();
			for (Session s : records) sessions.add(s);
			
			return sessions;
		} catch(Exception e) {
			return sessions;
		}
	}
	
}
