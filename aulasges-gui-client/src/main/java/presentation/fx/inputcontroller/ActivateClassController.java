package presentation.fx.inputcontroller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

import facade.exceptions.ApplicationException;
import facade.handlers.IClassServiceRemote;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;
import presentation.fx.model.ActivateClassModel;
import presentation.fx.model.CreateClassModel;
import presentation.fx.model.Facility;
import presentation.fx.model.Modality;

public class ActivateClassController extends BaseController {
	@FXML
	private TextField nameTextField;

	@FXML
	private TextField beginDateTextField;
	
	@FXML
	private TextField endDateTextField;

	@FXML
	private TextField maxTextField;

	@FXML
	private ComboBox<Facility> facilityComboBox;

	private ActivateClassModel model;

	private IClassServiceRemote classService;

	public void setClassService(IClassServiceRemote classService) {
		this.classService = classService;
	}

	public void setModel(ActivateClassModel model) {
		this.model = model;
		nameTextField.textProperty().bindBidirectional(model.nameProperty());
		beginDateTextField.textProperty().bindBidirectional(model.beginDateProperty());
		endDateTextField.textProperty().bindBidirectional(model.endDateProperty());
		maxTextField.textProperty().bindBidirectional(model.maxProperty(), new NumberStringConverter());   	
		facilityComboBox.setItems(model.getFacilities());   
		facilityComboBox.setValue(model.getSelectedFacility());
	}
	
	@FXML
	private void initialize() {
		UnaryOperator<Change> integerFilter = change -> {
			String newText = change.getControlNewText();
			if (newText.matches("[0-9]*")) { 
				return change;
			}
			return null;
		};
		
		UnaryOperator<Change> dateFilter = change -> {
			String newText = change.getControlNewText();
			if (newText.matches("([0-9]*|-*|[0-9]*-*[0-9]*-*[0-9]*)")) { 
				return change;
			}
			return null;
		};
		
		maxTextField.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(),
				0, integerFilter));
		beginDateTextField.setTextFormatter(new TextFormatter<String>(dateFilter));
		endDateTextField.setTextFormatter(new TextFormatter<String>(dateFilter));
	}
	
	@FXML
	void activateClassAction(ActionEvent event) {
		String errorMessages = validateInput();

		if (errorMessages.length() == 0) {
			try {
		
				
				classService.enableAClassInFacilityFromTo(model.getName(), model.getSelectedFacility().getName(), 
						LocalDate.parse(model.getBeginDate()), LocalDate.parse(model.getEndDate()), model.getMax());
				model.clearProperties();
				showInfo(i18nBundle.getString("activate.class.success"));
			} catch (ApplicationException e) {
				showError(i18nBundle.getString("activate.class.error.creating") + ": " + e.getMessage());
			}
		} else
			showError(i18nBundle.getString("activate.class.error.validating") + ":\n" + errorMessages);
	}

	private String validateInput() {	
		StringBuilder sb = new StringBuilder();
		String name = model.getName();
		String beginDate = model.getBeginDate();
		String endDate = model.getEndDate();
		
		if (name == null || name.length() == 0)
			sb.append(i18nBundle.getString("activate.class.invalid.name"));
		if (beginDate == null || beginDate.length() == 0) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("activate.class.invalid.beginDate"));
		}
		if (endDate == null || endDate.length() == 0) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("activate.class.invalid.endDate"));
		}
		if (model.getMax() <= 0) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("activate.class.invalid.max"));
		}
		if (model.getSelectedFacility() == null) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("activate.class.invalid.facility"));
		}
		
		return sb.toString();

	}

	@FXML
	void facilitySelected(ActionEvent event) {
		model.setSelectedFacility(facilityComboBox.getValue());
	}
}
