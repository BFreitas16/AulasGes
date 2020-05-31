package controller.web.inputController.actions;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import facade.exceptions.ApplicationException;
import facade.handlers.IConsumerServiceRemote;
import presentation.web.model.EnrollClassModel;

@Stateless
public class EnrollClassInitAction extends Action {
	
	@EJB private IConsumerServiceRemote enrollClassHandler;

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		EnrollClassModel model = createHelper(request);
		request.setAttribute("model", model);
		
		if (validInput(model)) {
			
			try {

				List<facade.dto.Class> classes = enrollClassHandler.indicatesModalityAndTypeOfRegistration(model.getModality(), model.getSubscription());
				model.setClasses(classes);
				
				request.getRequestDispatcher("/consumer/enrollClass.jsp").forward(request, response);
				return;
				
			} catch (ApplicationException e) {
				model.addMessage("Error enrolling class: " + e.getMessage());
			}
			
		} else
			model.addMessage("Error validating enroll class data");
		
		request.getRequestDispatcher("/consumer/enrollClassInit.jsp").forward(request, response);
		
	}
	
	private EnrollClassModel createHelper(HttpServletRequest request) {
		// Create the object model
		EnrollClassModel model = new EnrollClassModel();
		model.setEnrollClassHandler(enrollClassHandler);
		
		// fill it with data from the request
		model.setModality(request.getParameter("modality"));
		model.setSubscription(request.getParameter("subscription"));
		
		return model;
	}
	
	private boolean validInput(EnrollClassModel model) {
		
		// check if name is filled
		boolean result = isFilled(model, model.getModality(), "Modality must be specified");

		// falta verificar a modality
		result &= isFilled(model, model.getSubscription(), "It's needed to specify a subscription");
		
		return result;
	}

}
