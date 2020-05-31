package business.domain.classes;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import business.domain.facilities.Facility;
import business.domain.modalities.Modality;
import facade.exceptions.ApplicationException;

/**
 * Catalog of classes
 * 
 * @author fC51468
 * @version 1.1 (29/03/2020)
 * 
 */
@Stateless
public class ClassCatalog {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Creates a new Class
	 * 
	 * @param name The name of the class
	 * @param m The modality of the class
	 * @param daysOfWeek The days of week of the class
	 * @param startTime The start time of the class
	 * @param duration The duration of the class
	 */
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public void addClass(String name, Modality m, List<DayOfWeek> daysOfWeek, 
			LocalTime startTime, int duration) {
		Class c = new Class(m, name, daysOfWeek, startTime, duration);
		em.persist(c);
	}

	/**
	 * Get a class by its name
	 * 
	 * @param name The name of the class
	 * @requires name != null
	 * @return the class with the given name
	 * @throws ApplicationException When there isn't a class with the given name
	 */
	public Class getClass(String name) throws ApplicationException {
		try {
			TypedQuery<Class> query = em.createNamedQuery(Class.FIND_BY_NAME, Class.class);
			query.setParameter(Class.FIND_BY_NAME_NAME, name);
			return query.getSingleResult();
		}catch(Exception e) {
			throw new ApplicationException("Error obtaining the class");
		}
	}

	/**
	 * Get the classes of a given modality
	 * 
	 * @param m The modality 
	 * @return a list of classes of the given modality
	 * @throws ApplicationException
	 */
	public List<Class> getClassesOfModality(Modality m) throws ApplicationException {
		try {
			TypedQuery<Class> query = em.createNamedQuery(Class.FIND_BY_MODALITY, Class.class);
			query.setParameter(Class.FIND_BY_MODALITY_MODALITY, m);
			return query.getResultList();
		}catch(Exception e) {
			throw new ApplicationException("Error obtaining active classes of modality " + m.getName());
		}
	}

	public List<ActiveClass> getActiveClassesOfModality(Modality m) throws ApplicationException {
		List<ActiveClass> activeClasses = new ArrayList<>();
		try {
			TypedQuery<ActiveClass> query = em.createNamedQuery(Class.FIND_ACTIVE_BY_MODALITY, ActiveClass.class);
			query.setParameter(Class.FIND_BY_MODALITY_MODALITY_MODALITY, m);

			for (ActiveClass ac : query.getResultList()) {
				if (ac != null) activeClasses.add(ac);
			}

			return activeClasses;
		}catch(Exception e) {
			throw new ApplicationException("Error obtaining active classes of modality " + m.getName());
		}
	}

	public void activateClass(Class c, Facility f, LocalDate from, LocalDate to, int max) throws ApplicationException {
		try {
			em.lock(c, LockModeType.OPTIMISTIC);

			// activates the class and creates all its sessions
			c.activateClassAndCreateSessions(f, from, to, max);

		} catch(Exception e) {
			throw new ApplicationException("Error activating a class : " + c.getName() + "\n" + e.getMessage());
		}

	}
}
