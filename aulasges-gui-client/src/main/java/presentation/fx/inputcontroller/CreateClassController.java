package presentation.fx.inputcontroller;

import java.time.DayOfWeek;
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
import javafx.scene.control.CheckBox;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;
import presentation.fx.model.CreateClassModel;
import presentation.fx.model.Modality;

public class CreateClassController extends BaseController {
	@FXML
	private TextField nameTextField;

	@FXML
	private TextField beginTimeTextField;

	@FXML
	private TextField durationTextField;

	@FXML
	private ComboBox<Modality> modalityComboBox;
	
	//checkbox
	@FXML private CheckBox mondayCheckBox;
	@FXML private CheckBox tuesdayCheckBox;
	@FXML private CheckBox wednesdayCheckBox;
	@FXML private CheckBox thursdayCheckBox;
	@FXML private CheckBox fridayCheckBox;
	@FXML private CheckBox saturdayCheckBox;
	@FXML private CheckBox sundayCheckBox;
	
	private CreateClassModel model;

	private IClassServiceRemote classService;

	public void setClassService(IClassServiceRemote classService) {
		this.classService = classService;
	}

	public void setModel(CreateClassModel model) {
		this.model = model;
		nameTextField.textProperty().bindBidirectional(model.nameProperty());
		beginTimeTextField.textProperty().bindBidirectional(model.beginTimeProperty());
		durationTextField.textProperty().bindBidirectional(model.durationProperty(), new NumberStringConverter());   	
		modalityComboBox.setItems(model.getModalities());   
		modalityComboBox.setValue(model.getSelectedModality());
		
		//checkbox
		mondayCheckBox.selectedProperty().bindBidirectional(model.mondayProperty());
		tuesdayCheckBox.selectedProperty().bindBidirectional(model.tuesdayProperty());
		wednesdayCheckBox.selectedProperty().bindBidirectional(model.wednesdayProperty());
		thursdayCheckBox.selectedProperty().bindBidirectional(model.thursdayProperty());
		fridayCheckBox.selectedProperty().bindBidirectional(model.fridayProperty());
		saturdayCheckBox.selectedProperty().bindBidirectional(model.saturdayProperty());
		sundayCheckBox.selectedProperty().bindBidirectional(model.sundayProperty());
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
		
		UnaryOperator<Change> localTimeFilter = change -> {
			String newText = change.getControlNewText();
			if (newText.matches("([0-9]*|:*|[0-9]*:*[0-9]*:*[0-9]*)")) { 
				return change;
			}
			return null;
		};
		
		durationTextField.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(),
				0, integerFilter));
		
		beginTimeTextField.setTextFormatter(new TextFormatter<String>(localTimeFilter));
	}

	@FXML
	void createClassAction(ActionEvent event) {
		String errorMessages = validateInput();

		if (errorMessages.length() == 0) {
			try {
				List<DayOfWeek> daysOfWeek = new ArrayList<>();
				for (String day : model.getDaysOfWeek()) {
					daysOfWeek.add(DayOfWeek.valueOf(day));
				}
				
				classService.createClass(model.getSelectedModality().getName(), model.getName(), daysOfWeek,
						LocalTime.parse(model.getBeginTime()), model.getDuration());
				model.clearProperties();
				showInfo(i18nBundle.getString("new.class.success"));
			} catch (ApplicationException e) {
				showError(i18nBundle.getString("new.class.error.creating") + ": " + e.getMessage());
			}
		} else
			showError(i18nBundle.getString("new.class.error.validating") + ":\n" + errorMessages);
	}

	private String validateInput() {	
		StringBuilder sb = new StringBuilder();
		String name = model.getName();
		String beginTime = model.getBeginTime();
		List<String> daysOfWeek = model.getDaysOfWeek();
		if (name == null || name.length() == 0)
			sb.append(i18nBundle.getString("new.class.invalid.name"));
		if (beginTime == null || beginTime.length() == 0) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("new.class.invalid.beginTime"));
		}
		if (model.getDuration() <= 0) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("new.class.invalid.duration"));
		}
		if (model.getSelectedModality() == null) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("new.class.invalid.modality"));
		}
		if (daysOfWeek == null || daysOfWeek.size() == 0) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("new.class.invalid.daysOfWeek"));
		}

		return sb.toString();

	}

	@FXML
	void modalitySelected(ActionEvent event) {
		model.setSelectedModality(modalityComboBox.getValue());
	}

}
