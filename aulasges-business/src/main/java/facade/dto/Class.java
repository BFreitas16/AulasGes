package facade.dto;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Class implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4975431407377308879L;

	/**
	 * The Class name
	 */
	private String className;

	/**
	 * The days of week for the sessions of this class
	 */
	private List<DayOfWeek> daysOfWeek = new ArrayList<>();

	/**
	 * The sessios's startTime of this class
	 */
	private LocalTime startTime;

	/**
	 * The number of free places
	 */
	private int places;
	
	/**
	 * The facility of class
	 */
	private String facilityName;
	
	
	public Class(String className, LocalTime startTime, 
			List<DayOfWeek> daysOfWeek, String facilityName, int places) {
		
		this.className = className;
		this.startTime = startTime;
		this.daysOfWeek = daysOfWeek;
		this.facilityName = facilityName;
		this.places = places;
	}
	
	/**
	 * 
	 * @return The class name
	 */
	public String getName() {
		return this.className;
	}
	
	/**
	 * 
	 * @return the days of week of the class
	 */
	public List<DayOfWeek> getDaysOfWeek() {
		return this.daysOfWeek;
	}

	/**
	 * 
	 * @return the start time of the class
	 */
	public LocalTime getStartTime() {
		return this.startTime;
	}

	/**
	 * 
	 * @return the free places of the class
	 */
	public int getPlaces() {
		return this.places;
	}

	/**
	 * 
	 * @return the name of facility of the class
	 */
	public String getFacilityName() {
		return this.facilityName;
	}

	@Override
	public String toString() {
		return "Class: " + this.className + " (" + this.daysOfWeek.toString() +
				" starting at " + this.startTime + ")" + " in " + this.facilityName +
				"\nFree places: " + this.places;
	}

}
