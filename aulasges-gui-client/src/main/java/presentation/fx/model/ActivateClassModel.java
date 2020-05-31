package presentation.fx.model;

import facade.exceptions.ApplicationException;
import facade.handlers.IClassServiceRemote;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ActivateClassModel {
	
	private final StringProperty name;
	private final StringProperty beginDate;
	private final StringProperty endDate;
	private final IntegerProperty max;
	private final ObjectProperty<Facility> selectedFacility;
	private final ObservableList<Facility> facilities;
	
	public ActivateClassModel(IClassServiceRemote cs) {
		name = new SimpleStringProperty();
		beginDate = new SimpleStringProperty();
		endDate = new SimpleStringProperty();
		max = new SimpleIntegerProperty();
		this.facilities = FXCollections.observableArrayList();
		try {
			cs.enableClassInit().forEach(f -> facilities.add(new Facility(f.getName(), f.getType(), f.getCapacity())));
		} catch (ApplicationException e) {
			// no discounts added
		}
		selectedFacility = new SimpleObjectProperty<>(null);
	}
	
	public ObjectProperty<Facility> selectedFacilityProperty() {
		return selectedFacility;
	}

	public final Facility getSelectedFacility() {
		return selectedFacility.get();
	}

	public final void setSelectedFacility(Facility f) {
		selectedFacility.set(f);
	}

	public ObservableList<Facility> getFacilities() {
		return facilities;
	}

	public String getName() {
		return name.get();
	}

	public StringProperty nameProperty() {
		return name;
	}
	
	public String getBeginDate() {
		return beginDate.get();
	}

	public StringProperty beginDateProperty() {
		return beginDate;
	}
	
	public String getEndDate() {
		return endDate.get();
	}

	public StringProperty endDateProperty() {
		return endDate;
	}

	public int getMax() {
		return max.get();
	}

	public IntegerProperty maxProperty() {
		return max;
	}	


	public void clearProperties() {
		name.set("");
		beginDate.set("");
		endDate.set("");
		max.set(0);
		selectedFacility.set(null);
	}

}
