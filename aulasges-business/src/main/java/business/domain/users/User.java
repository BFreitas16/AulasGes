package business.domain.users;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import business.domain.classes.Class;
import business.domain.subscriptions.Subscription;

/**
 * A User
 * 
 * @author fC51468
 * @version 1.1 (29/03/2020)
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name=User.FIND_BY_ID, query="SELECT u FROM User u WHERE u.userID = :" + 
			User.FIND_BY_ID_ID)
})
public class User {
	
	// Named query name constants
	public static final String FIND_BY_ID = "Modality.findByID";
	public static final String FIND_BY_ID_ID = "userID";
	
	/**
	 * The user's number
	 */
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	/**
	 * The id of user in the system
	 */
	@Column(nullable = false, unique = true) 
	private int userID;
	
	/**
	 * The user's name
	 */
	@Column(nullable = false)
	private String name;
	
	/**
	 * The user's NIF
	 */
	@Column(nullable = false, unique = true) 
	private int nif;
	
	/**
	 * The debt of this user
	 */
	@Column(nullable = false)
	private double debt = 0;
	
	/**
	 * 
	 */
	@OneToMany(mappedBy = "user")
	private List<Subscription> subscriptions = new ArrayList<>();
	
	/**
	 * Constructor needed by JPA.
	 */
	User () {}
	
	/**
	 * Creates a user with a name and an id
	 * 
	 * @param name The name of user
	 * @param numero The id of user
	 */
	public User(String name, int numero, int nif) {
		this.name = name;
		this.userID = numero;
		this.nif = nif;
	}

	/**
	 * Get the id of the user
	 * 
	 * @return the id of the user
	 */
	public int getId() {
		return this.userID;
	}

	/**
	 * Get the name of the user
	 * 
	 * @return the user's name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * 
	 * @return the user's NIF
	 */
	public int getNIF() {
		return this.nif;
	}
	
	/**
	 * Get the debt of the user
	 * 
	 * @return the user's debt
	 */
	public double getDebt() {
		return this.debt;
	}
	
	/**
	 * Get all the subscriptions of the user
	 * 
	 * @return the user's subscriptions
	 */
	public List<Subscription> getSubscriptions() {
		return this.subscriptions;
	}
	
	/**
	 * Subscribe a user to a class with a type of subscription and
	 * gives the user the cost
	 * 
	 * @param subs The type of registration
	 * @param c The class to enroll
	 * @return the cost of this registration
	 */
	public double subscribe(Subscription subs, Class c) {

	    // calculate the cost of this registration
	    double cost = subs.computePayingCost();

	    // registration of the user
	    this.debt += cost;
	    this.subscriptions.add(subs);
	    subs.associateUser(this);

	    return cost;
	}
	
	@Override
	public String toString() {
		return "User: " + this.getName() + " with NIF: " + this.getNIF() + 
				"\nDebt: " + this.debt;
	}
	

}
