package controller.web.inputController.actions;

import java.io.IOException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import facade.exceptions.ApplicationException;
import facade.handlers.IClassServiceRemote;
import presentation.web.model.CreateClassModel;

@Stateless
public class CreateClassAction extends Action {
	
	@EJB private IClassServiceRemote createClassHandler;

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		CreateClassModel model = createHelper(request);
		request.setAttribute("model", model);
		
		if (validInput(model)) {
			
			try {				
				// convert DaysOfWeek
				List<DayOfWeek> daysOfWeek = new ArrayList<>();
				for (String day : model.getDaysOfWeek()) {
					daysOfWeek.add(DayOfWeek.valueOf(day));
				}
				
				createClassHandler.createClass(model.getModality(), model.getName(),
						daysOfWeek, localTimeValue(model.getBeginTime()), intValue(model.getDuration()));
				
				model.clearFields();
				model.addMessage("Class successfully created.");				
			} catch (ApplicationException e) {
				model.addMessage("Error adding customer: " + e.getMessage());
			}
			
		} else
			model.addMessage("Error validating creating class data");
		
		request.getRequestDispatcher("/class/createClass.jsp").forward(request, response);
		
	}
	
	private CreateClassModel createHelper(HttpServletRequest request) {
		// Create the object model
		CreateClassModel model = new CreateClassModel();
		model.setCreateClassHandler(createClassHandler);
		
		// fill it with data from the request
		model.setName(request.getParameter("name"));
		model.setBeginTime(request.getParameter("beginTime"));
		model.setDuration(request.getParameter("duration"));
		model.setDaysOfWeek(request.getParameterValues("daysOfWeek"));
		model.setModality(request.getParameter("modality"));
		
		return model;
	}
	
	private boolean validInput(CreateClassModel model) {
		
		// check if name is filled
		boolean result = isFilled(model, model.getName(), "Name must be filled.");
		
		// check if beginTime is filled and a valid date
		result &= isFilled(model, model.getBeginTime(), "Begin time must be filled")
				&& isLocalTime(model, model.getBeginTime(), "Not a valid begin time");
		
		// check if duration is filled and a valid number
		result &= isFilled(model, model.getDuration(), "Duration number must be filled") 
				&& isInt(model, model.getDuration(), "Duration number with invalid characters");
		
		// check if daysOfWeek is filled
		//TODO passar erro ??? 
		result &= model.getDaysOfWeek() != null && model.getDaysOfWeek().length > 0;
		
		// falta verificar a modality
		result &= isFilled(model, model.getModality(), "It's needed to specify a modality");
		
		return result;
	}

}
