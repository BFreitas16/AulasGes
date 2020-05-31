package presentation.web.model;

import java.util.LinkedList;
import java.util.List;

import facade.dto.Class;
import facade.dto.Modality;
import facade.exceptions.ApplicationException;
import facade.handlers.IConsumerServiceRemote;

public class EnrollClassModel extends Model {
	
	
	private String modality;
	private String subscription;
	private String classToEnroll;
	private String consumerNumber;
	private IConsumerServiceRemote enrollClassHandler;
	private List<Class> classes;

	public void setEnrollClassHandler(IConsumerServiceRemote enrollClassHandler) {
		this.enrollClassHandler = enrollClassHandler;
	}
	
	public void setModality(String modality) {
		this.modality = modality;
	}
	
	public String getModality() {
		return modality;
	}
	
	public void setSubscription(String subscription) {
		this.subscription = subscription;
	}
	
	public String getSubscription() {
		return subscription;
	}
	
	public void setClassToEnroll(String classToEnroll) {
		this.classToEnroll = classToEnroll;
	}
	
	public String getClassToEnroll() {
		return classToEnroll;
	}
	
	public void setConsumerNumber(String numeroUtente) {
		this.consumerNumber = numeroUtente;
	}
	
	public String getConsumerNumber() {
		return consumerNumber;
	}
	
	public void setClasses(List<Class> classes) {
		this.classes = classes;
	}
	
	public List<Class> getClasses() {
		return classes;
	}
	
	public void clearFields() {
		modality = subscription = classToEnroll = consumerNumber = "";
	}
	
	public Iterable<Modality> getModalities() {
		try {
			return enrollClassHandler.enrollClassInit();
		} catch (ApplicationException e) {
			return new LinkedList<> ();		
		}
	}
	
	public Iterable<String> getSubscriptions() {
		List<String> subscriptions = enrollClassHandler.getSubscriptions();
		if(subscriptions == null) {
			return new LinkedList<>();
		}
		return subscriptions;
	}

}
