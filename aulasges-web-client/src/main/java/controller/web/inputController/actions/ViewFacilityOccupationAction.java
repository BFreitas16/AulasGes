package controller.web.inputController.actions;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import facade.dto.Session;
import facade.exceptions.ApplicationException;
import facade.handlers.IFacilityServiceRemote;
import presentation.web.model.ViewFacilityOccupationModel;

@Stateless
public class ViewFacilityOccupationAction extends Action {
	
	@EJB private IFacilityServiceRemote viewFacilityOccupationHandler;

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		ViewFacilityOccupationModel model = createHelper(request);
		request.setAttribute("model", model);
		
		if (validInput(model)) {
			
			try {
				List<Session> sessions = viewFacilityOccupationHandler.getOccupationOfAFacility(model.getName(), localDateValue(model.getDate()));
				List<String> ss = sessions.stream().map(Session::toString).collect(Collectors.toList());
				model.setSessions(ss);
				model.clearFields();
			} catch (ApplicationException e) {
				model.addMessage("Error getting the occupation of a facility: " + e.getMessage());
			}
			
		} else
			model.addMessage("Error validating the data");
		
		request.getRequestDispatcher("/facility/viewFacilityOccupation.jsp").forward(request, response);
		
	}
	
	private ViewFacilityOccupationModel createHelper(HttpServletRequest request) {
		// Create the object model
		ViewFacilityOccupationModel model = new ViewFacilityOccupationModel();
		
		// fill it with data from the request
		model.setName(request.getParameter("name"));
		model.setDate(request.getParameter("date"));
		
		return model;
	}

	private boolean validInput(ViewFacilityOccupationModel model) {
		
		// check if facility name is filled
		boolean result = isFilled(model, model.getName(), "Facility name must be filled");
		
		// check if date is filled and is valid
		result &= isFilled(model, model.getDate(), "Date must be filled")
				&& isLocalDate(model, model.getDate(), "Date isn't a valid format : yyyy-mm-dd");
		
		return result;
	}

}
