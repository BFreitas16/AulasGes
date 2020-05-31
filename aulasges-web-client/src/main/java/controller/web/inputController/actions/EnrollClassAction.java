package controller.web.inputController.actions;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import facade.exceptions.ApplicationException;
import facade.handlers.IConsumerServiceRemote;
import presentation.web.model.EnrollClassModel;

@Stateless
public class EnrollClassAction extends Action {
	
	@EJB private IConsumerServiceRemote enrollClassHandler;

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		EnrollClassModel model = createHelper(request);
		request.setAttribute("model", model);
		
		if (validInput(model)) {
			
			try {
				
				double price = enrollClassHandler.enrollClass(model.getClassToEnroll(), intValue(model.getConsumerNumber()), model.getSubscription());
				
				model.clearFields();
				model.addMessage("Price: " + price + "\nEnroll successfully made.");
			} catch (ApplicationException e) {
				model.addMessage("Error enrolling class: " + e.getMessage());
			}
			
		} else
			model.addMessage("Error validating enroll class data");
		
		request.getRequestDispatcher("/consumer/enrollClass.jsp").forward(request, response);
		
	}
	
	private EnrollClassModel createHelper(HttpServletRequest request) {
		// Create the object model
		EnrollClassModel model = new EnrollClassModel();
		model.setEnrollClassHandler(enrollClassHandler);
		
		// fill it with data from the request
		model.setModality(request.getParameter("modality"));
		model.setSubscription(request.getParameter("subscription"));
		
		model.setClassToEnroll(request.getParameter("classToEnroll"));
		model.setConsumerNumber(request.getParameter("consumerNumber"));
		
		return model;
	}
	
	private boolean validInput(EnrollClassModel model) {
		// check if class is filled
		boolean result = isFilled(model, model.getClassToEnroll(), "Class must be filled.");
		
		// check if consumer number is filled and is an int
		result &= isFilled(model, model.getConsumerNumber(), "A consumer number must be filled")
				&& isInt(model, model.getConsumerNumber(), "Consumer number number with invalid characters");
		
		return result;
	}

}
