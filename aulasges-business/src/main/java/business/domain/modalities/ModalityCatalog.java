package business.domain.modalities;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import facade.exceptions.ApplicationException;

@Stateless
public class ModalityCatalog {
	
	@PersistenceContext
	private EntityManager em;
	
	/**
	 * Creates a new modality with a name, a description, 
	 * minimum duration and a price per hour
	 * 
	 * @param name The name of modality
	 * @param minDuration The minimum duration of a class of this modality
	 * @param hourPrice The price per hour
	 */
	public void createModality(String name, int minDuration, double hourPrice) {
		Modality m = new Modality(name, minDuration, hourPrice);
		em.persist(m);
	}

	/**
	 * Get all modalities
	 * 
	 * @return a list of all modalities
	 * @throws ApplicationException 
	 */
	public List<Modality> getAllModalities() throws ApplicationException {
		try {
			TypedQuery<Modality> query = em.createNamedQuery(Modality.FIND_ALL, Modality.class);
			return query.getResultList();
		} catch(Exception e) {
			throw new ApplicationException("Error obtaining the modalities list");
		}
	}

	/**
	 * Get a modality by the name
	 * 
	 * @param name The name of the modality
	 * @requires name != null
	 * @return the modality with the specified name
	 * @throws ApplicationException When there isn't a modality with the specified name
	 */
	public Modality getModality(String name) throws ApplicationException {
		try {
			TypedQuery<Modality> query = em.createNamedQuery(Modality.FIND_BY_NAME, Modality.class);
			query.setParameter(Modality.FIND_BY_NAME_NAME, name);
			return query.getSingleResult();
		}catch(Exception e) {
			throw new ApplicationException("Error obtaining the modality");
		}
	}

}
