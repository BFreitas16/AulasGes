package business.domain.subscriptions;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Entity;

import business.domain.classes.ActiveClass;
import business.domain.users.User;
import facade.exceptions.ApplicationException;

/**
 * A Monthly Subscription
 * The cost will be of all sessions in this month
 * 
 * @author fC51468
 * @version 1.1 (29/03/2020)
 * 
 */
@Entity
public class RegularSubscription extends Subscription {
	
	/**
	 * Creates a Monthly subscription
	 */
	public RegularSubscription() {
		super(SubscriptionType.REGULARSUBSCRIPTION);
	}

	@Override
	public double computePayingCost() {
		return activeClass.getPriceOfASession() * activeClass.getSessionsNumberPerWeek() * 4;
	}

	@Override
	public List<ActiveClass> getActiveClasses(List<ActiveClass> classes) {
		// get the active classes of a modality and then filter it
		// by the classes that are valid for this type of subscription:
		// are valid if there is free spaces to regular subscriptions
		return classes.stream()
				.filter(ActiveClass::isPossibleToSubscribeRegualPerson)
				.collect(Collectors.toList());
	}

	@Override
	public void subscribeConsumerToClass(User u) throws ApplicationException {
		this.activeClass.regularSubscriptionOfConsumer(u);
	}

}
