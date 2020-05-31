package presentation.fx.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Facility {
	private final StringProperty name = new SimpleStringProperty();
	private final StringProperty type = new SimpleStringProperty();
	private final int capacity ;
	
	 public Facility(String name, String type, int capacity) {
	        setName(name);
	        setType(type);
	        this.capacity = capacity;
	    }
	    
	    public final StringProperty nameProperty() {
	        return this.name;
	    }

	    public final String getName() {
	        return this.nameProperty().get();
	    }

	    public final void setName(final String name) {
	        this.nameProperty().set(name);
	    }
	    
	    public final StringProperty typeProperty() {
	        return this.type;
	    }

	    public final String getType() {
	        return this.typeProperty().get();
	    }

	    public final void setType(final String type) {
	        this.typeProperty().set(type);
	    }
	    
		public int getCapacity() {
			return capacity;
		}
		
		@Override
		public String toString(){
			return getName();
		}

}
