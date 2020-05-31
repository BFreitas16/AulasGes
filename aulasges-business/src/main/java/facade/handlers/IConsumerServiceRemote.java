package facade.handlers;

import java.util.List;

import javax.ejb.Remote;

import business.domain.subscriptions.SubscriptionType;
import facade.dto.Class;
import facade.dto.Modality;
import facade.exceptions.ApplicationException;

@Remote
public interface IConsumerServiceRemote {
	
	public List<Modality> enrollClassInit() throws ApplicationException;
	
	public List<Class> indicatesModalityAndTypeOfRegistration(String mod, 
            String registration) throws ApplicationException;
	
	public double enrollClass(String theClass, int consumerNumber, String registration) 
			throws ApplicationException;

	public List<String> getSubscriptions();
}
