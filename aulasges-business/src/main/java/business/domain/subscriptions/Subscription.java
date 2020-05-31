package business.domain.subscriptions;

import java.util.Date;
import java.util.List;

import static javax.persistence.InheritanceType.SINGLE_TABLE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import business.domain.classes.ActiveClass;
import business.domain.users.User;
import facade.exceptions.ApplicationException;

/**
 * A Subscription
 * 
 * @author fC51468
 * @version 1.1 (29/03/2020)
 * 
 */
@Entity
@Inheritance(strategy=SINGLE_TABLE)
public abstract class Subscription {
	
	@Id	@GeneratedValue private int id;
	
	/**
	 * The Class for this subscription
	 */
	@ManyToOne
	protected ActiveClass activeClass;
	
	@ManyToOne @JoinColumn
	private User user;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private SubscriptionType type;
	
	/**
	 * The enroll date
	 */
	@Column(name="enroll_date", nullable = false)
	private Date date;
	
	/**
	 * Constructor needed by JPA.
	 */
	Subscription() {}
	
	/**
	 * Creates a subscription
	 * 
	 * @param subsType The type of subscription
	 */
	public Subscription(SubscriptionType type) {
		this.type = type;
		this.date = new Date();
	}
	
	/**
	 * 
	 * @return the class associated with the subscription
	 */
	public ActiveClass getClassSubscription() {
		return this.activeClass;
	}
	
	/**
	 * 
	 * @return The type of subscription
	 */
	public SubscriptionType getType() {
		return this.type;
	}
	
	/**
	 * Return the payment for this subscription
	 * 
	 * @return the cost for this subscription
	 */
	public abstract double computePayingCost();
	
	/**
	 * Associate a class to this registration to complete
	 * the subscription
	 * 
	 * @param c The class to associate
	 */
	public void associateClass(ActiveClass c) {
		this.activeClass = c;
	}
	
	/**
	 * Associate a class to this registration to complete
	 * the subscription
	 * 
	 * @param activeClass The class to associate
	 */
	public void associateUser(User u) {
		this.user = u;
	}
	
	/**
	 * Filter the classes
	 * 
	 * @return a list of active classes
	 */
	public abstract List<ActiveClass> getActiveClasses(List<ActiveClass> classes);
	
	/**
	 * Enroll the user to a class
	 * 
	 * @param u The consumer to do the subscription
	 */
	public abstract void subscribeConsumerToClass(User u) throws ApplicationException;

}
