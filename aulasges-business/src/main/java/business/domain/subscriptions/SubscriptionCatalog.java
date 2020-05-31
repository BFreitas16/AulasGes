package business.domain.subscriptions;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

import business.domain.classes.ActiveClass;
import business.domain.classes.Class;
import business.domain.users.User;
import facade.exceptions.ApplicationException;

@Stateless
public class SubscriptionCatalog {
	
	@PersistenceContext
	private EntityManager em;
	
	
	/**
	 * Subscribe a user to a class and returns the cost 
	 * @param subs the subscription
	 * @param c the class
	 * @param u the user
	 * @return the price of the subscription
	 * @throws ApplicationException
	 */
	public double subscribeUserToClass(Subscription subs, Class c, User u) throws ApplicationException {
        ActiveClass ac = c.getCurrentActiveClass();
        em.lock(ac, LockModeType.OPTIMISTIC);

        // associate the class in the class
        subs.associateClass(ac);

        // user registration in the class
        subs.subscribeConsumerToClass(u);

        em.persist(subs);

        // enroll the class with a type of registration
        return u.subscribe(subs, c);
    }

}
