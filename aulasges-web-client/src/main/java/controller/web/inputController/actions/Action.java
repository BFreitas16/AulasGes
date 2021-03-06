package controller.web.inputController.actions;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import presentation.web.model.Model;

/**
 * An abstract HTTP action request handler. 
 * Think of it as an operation in the SSD diagram.
 * It has an init method, because objects are
 * create from the prototype (vide UseCaseFrontController)
 * and its easier to use a no parameters construct.
 * 
 * It allows subclasses to define how to handle individual 
 * actions.
 * 
 * We need to store the http request context, since
 * actions are not http servlets and do not have access to
 * the request data.
 *  
 * @author fmartins
 *
 */
public abstract class Action {

	/**
	 * Strategy method for processing each request
	 * @throws ServletException
	 * @throws IOException
	 */
	public abstract void process(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException;
	
	// the following methods might need to be refactored in order to avoid
	// duplicated code
	protected boolean isInt(Model help, String num, String message) {
//		try {
//			Integer.parseInt(num);
//			return true;
//		} catch (NumberFormatException e) {
//			help.addMessage(message);
//			return false;
//		}
		return intValue(num) != Integer.MIN_VALUE;
	}

	protected int intValue(String num) {
		try {
			return Integer.parseInt(num);
		} catch (NumberFormatException e) {
			return Integer.MIN_VALUE;
		}
	}
	
	protected boolean isLocalDate(Model help, String date, String message) {
		return localDateValue(date) != null;
	}
	
	protected boolean isLocalTime(Model help, String date, String message) {
		return localTimeValue(date) != null;
	}
	
	protected LocalDate localDateValue(String date) {
		try {
			return LocalDate.parse(date);
		} catch (DateTimeParseException e) {
			return null;
		}
	}
	
	protected LocalTime localTimeValue(String time) {
		try {
			return LocalTime.parse(time);
		} catch (DateTimeParseException e) {
			return null;
		}
	}


	protected boolean isFilled (Model helper, String value, String message) {
		if (value == null || (value != null && value.equals(""))) {
			helper.addMessage(message);
			return false;
		}
		return true;
	}
	
	protected boolean isFilled (Model helper, LocalDate value, String message) {
		if (value == null) {
			helper.addMessage(message);
			return false;
		}
		return true;
	}

}
