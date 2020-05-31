package controller.web.inputController.actions;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import facade.exceptions.ApplicationException;
import facade.handlers.IClassServiceRemote;
import presentation.web.model.ActivateClassModel;

@Stateless
public class ActivateClassAction extends Action {
	
	@EJB private IClassServiceRemote enableClassHandler;

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		ActivateClassModel model = createHelper(request);
		request.setAttribute("model", model);
		
		if (validInput(model)) {
			
			try {
				enableClassHandler.enableAClassInFacilityFromTo(model.getName(), model.getFacility(), 
						localDateValue(model.getBeginDate()), localDateValue(model.getEndDate()), intValue(model.getMax()));
				
				model.clearFields();
				model.addMessage("Class successfully activated.");
			} catch (ApplicationException e) {
				model.addMessage("Error activating class: " + e.getMessage());
			}
			
		} else
			model.addMessage("Error validating activating class data");
		
		request.getRequestDispatcher("/class/activateClass.jsp").forward(request, response);
		
	}

	private ActivateClassModel createHelper(HttpServletRequest request) {
		// Create the object model
		ActivateClassModel model = new ActivateClassModel();
		model.setEnableClassHandler(enableClassHandler);
		
		// fill it with data from the request
		model.setName(request.getParameter("name"));
		model.setBeginDate(request.getParameter("beginDate"));
		model.setEndDate(request.getParameter("endDate"));
		model.setMax(request.getParameter("max"));
		model.setFacility(request.getParameter("facility"));
		
		return model;
	}
	
	private boolean validInput(ActivateClassModel model) {
		
		// check if name is filled
		boolean result = isFilled(model, model.getName(), "Name must be filled.");
		
		// check if beginDate is filled and a valid date
		result &= isFilled(model, model.getBeginDate(), "Begin date must be filled")
				&& isLocalDate(model, model.getBeginDate(), "Not a valid begin date");
		
		// check if endDate is filled and a valid date
		result &= isFilled(model, model.getEndDate(), "End date must be filled")
				&& isLocalDate(model, model.getEndDate(), "Not a valid end date");

		// check if max is filled and a valid number
		result &= isFilled(model, model.getMax(), "Max number must be filled") 
					&& isInt(model, model.getMax(), "Max number with invalid characters");
		
		// falta verificar a facility
		result &= isFilled(model, model.getFacility(), "It's needed to specify a facility");

		return result;
	}

}
