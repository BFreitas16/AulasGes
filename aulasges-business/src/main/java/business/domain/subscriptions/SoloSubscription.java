package business.domain.subscriptions;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;

import business.domain.classes.ActiveClass;
import business.domain.users.User;
import facade.exceptions.ApplicationException;

/**
 * A Separated Subscription
 * The cost will be of a session
 * 
 * @author fC51468
 * @version 1.1 (29/03/2020)
 * 
 */
@Entity
public class SoloSubscription extends Subscription {
	
	/**
	 * Creates a Monthly subscription
	 */
	public SoloSubscription() {
		super(SubscriptionType.SOLOSUBSCRIPTION);
	}

	@Override
	public double computePayingCost() {
		return activeClass.getPriceOfASession();
	}

	@Override
	public List<ActiveClass> getActiveClasses(List<ActiveClass> classes) {
		// get the active classes of a modality and then filter it
		// by the classes that are valid for this type of subscription:
		// are valid if have a session in the next 24h
		return classes.stream()
				.filter(ActiveClass::hasSessionInNext24h)
				.collect(Collectors.toList());
	}

	@Override
	public void subscribeConsumerToClass(User u) throws ApplicationException {
		this.activeClass.soloSubscriptionOfConsumer(u);
	}

}
