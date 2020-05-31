package facade.handlers;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import javax.ejb.Remote;

import facade.dto.Session;
import facade.exceptions.ApplicationException;

@Remote
public interface IFacilityServiceRemote {
	
	public List<Session> getOccupationOfAFacility(String name, LocalDate date)
			throws ApplicationException;

}
