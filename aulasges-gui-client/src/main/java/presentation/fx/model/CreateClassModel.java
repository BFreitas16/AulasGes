package presentation.fx.model;

import java.util.ArrayList;
import java.util.List;

import facade.exceptions.ApplicationException;
import facade.handlers.IClassServiceRemote;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CreateClassModel {

	private final StringProperty name;
	private final StringProperty beginTime;
	private final IntegerProperty duration;
	private final ObjectProperty<StringProperty> selectedDaysOfWeek;
	private final ObservableList<StringProperty> daysOfWeek;
	private final ObjectProperty<Modality> selectedModality;
	private final ObservableList<Modality> modalities;

	//checkbox
	private SimpleBooleanProperty mondayChecked;
	private SimpleBooleanProperty tuesdayChecked;
	private SimpleBooleanProperty wednesdayChecked;
	private SimpleBooleanProperty thursdayChecked;
	private SimpleBooleanProperty fridayChecked;
	private SimpleBooleanProperty saturdayChecked;
	private SimpleBooleanProperty sundayChecked;

	public CreateClassModel(IClassServiceRemote cs) {
		name = new SimpleStringProperty();
		beginTime = new SimpleStringProperty();
		duration = new SimpleIntegerProperty();
		daysOfWeek = FXCollections.observableArrayList();
		modalities = FXCollections.observableArrayList();
		
		//checkbox
		mondayChecked=new SimpleBooleanProperty(false);
		tuesdayChecked=new SimpleBooleanProperty(false);
		wednesdayChecked=new SimpleBooleanProperty(false);
		thursdayChecked=new SimpleBooleanProperty(false);
		fridayChecked=new SimpleBooleanProperty(false);
		saturdayChecked=new SimpleBooleanProperty(false);
		sundayChecked=new SimpleBooleanProperty(false);
	
		try {
			cs.createClassInit().forEach(m -> modalities.add(new Modality(m.getName())));
		} catch (ApplicationException e) {
			// no modalities added
		}
		selectedModality = new SimpleObjectProperty<>(null);
		selectedDaysOfWeek = new SimpleObjectProperty<>(null);
	}

	public ObjectProperty<Modality> selectedModalityProperty() {
		return selectedModality;
	}
	
	

	public final Modality getSelectedModality() {
		return selectedModality.get();
	}

	public final void setSelectedModality(Modality m) {
		selectedModality.set(m);
	}



	public ObservableList<Modality> getModalities() {
		return modalities;
	}

	public String getName() {
		return name.get();
	}

	public StringProperty nameProperty() {
		return name;
	}

	public int getDuration() {
		return duration.get();
	}

	public IntegerProperty durationProperty() {
		return duration;
	}	

	public String getBeginTime() {
		return beginTime.get();
	}

	public StringProperty beginTimeProperty() {
		return beginTime;
	}	
	
	public final void  setMondayChecked(boolean check){
		mondayChecked.set(check);
	}
	
	public final void  setTuesdayChecked(boolean check){
		tuesdayChecked.set(check);
	}
	
	public final void  setWednesdayChecked(boolean check){
		wednesdayChecked.set(check);
	}
	
	public final void  setThursdayChecked(boolean check){
		thursdayChecked.set(check);
	}
	
	public final void  setFridayChecked(boolean check){
		fridayChecked.set(check);
	}
	
	public final void  setSaturdayChecked(boolean check){
		saturdayChecked.set(check);
	}
	
	public final void  setSundayChecked(boolean check){
		sundayChecked.set(check);
	}
	
	public SimpleBooleanProperty mondayProperty() {
		return mondayChecked;
	}
	
	public SimpleBooleanProperty tuesdayProperty() {
		return tuesdayChecked;
	}
	
	public SimpleBooleanProperty wednesdayProperty() {
		return wednesdayChecked;
	}
	
	public SimpleBooleanProperty thursdayProperty() {
		return thursdayChecked;
	}
	
	public SimpleBooleanProperty fridayProperty() {
		return fridayChecked;
	}
	
	public SimpleBooleanProperty saturdayProperty() {
		return saturdayChecked;
	}
	
	public SimpleBooleanProperty sundayProperty() {
		return sundayChecked;
	}
	
	public List<String> getDaysOfWeek(){
		List<String> daysOfWeek = new ArrayList<>();
		if (mondayChecked.getValue()) daysOfWeek.add("MONDAY");
		if (tuesdayChecked.getValue()) daysOfWeek.add("TUESDAY");
		if (wednesdayChecked.getValue()) daysOfWeek.add("WEDNESDAY");
		if (thursdayChecked.getValue()) daysOfWeek.add("THURSDAY");
		if (fridayChecked.getValue()) daysOfWeek.add("FRIDAY");
		if (saturdayChecked.getValue()) daysOfWeek.add("SATURDAY");
		if (sundayChecked.getValue()) daysOfWeek.add("SUNDAY");
		return daysOfWeek;
	}


	public void clearProperties() {
		name.set("");
		beginTime.set("");
		duration.set(0);
		selectedModality.set(null);
		daysOfWeek.clear();
	}

}
