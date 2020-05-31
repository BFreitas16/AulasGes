package business.domain.users;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import facade.exceptions.ApplicationException;


/**
 * The Users catalog 
 * 
 *  @author fc51468
 *  @version 1.1 (29/03/2020)
 */
@Stateless
public class UserCatalog {
	
	@PersistenceContext
	private EntityManager em;

	/**
	 * Get a consumer by id
	 * 
	 * @param consumerNumber The consumer id
	 * @return the Consumer with the specified id
	 * @throws ApplicationException When doesn't exist a consumer with 
	 * the given id
	 */
	public User getUserByID(int consumerNumber) throws ApplicationException {
		try {
			TypedQuery<User> query = em.createNamedQuery(User.FIND_BY_ID, User.class);
			query.setParameter(User.FIND_BY_ID_ID, consumerNumber);
			return query.getSingleResult();
		}catch(Exception e) {
			throw new ApplicationException("Error obtaining the user");
		}
	}

}
