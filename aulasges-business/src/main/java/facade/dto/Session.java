package facade.dto;

import java.io.Serializable;
import java.time.LocalTime;

/**
 * A Session/Class DTO
 * 
 * @author fC51468
 * @version 1.1 (29/03/2020)
 * 
 */
public class Session implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1048780875632094500L;

	/**
	 * The class name of this session
	 */
	private String name;
	
	/**
	 * The start time of this lesson
	 */
	private LocalTime startTime;
	
	/**
	 * The end time of this lesson
	 */
	private LocalTime endTime;
	
	/**
	 * Creates a new Session of a class with a start and end time
	 * 
	 * @param name The name of the class of this session
	 * @param startTime The start time of the session
	 * @param endTime The end time of the lesson
	 */
	public Session(String name, LocalTime startTime, LocalTime endTime) {
		this.name = name;
		this.startTime= startTime;
		this.endTime = endTime;
		
	}
	
	/**
	 * Get the name of the class of this session
	 * 
	 * @return the name of the class of this session
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Get the start time of the session
	 * 
	 * @return the start time of the lesson
	 */
	public LocalTime getStartTime() {
		return this.startTime;
	}
	
	/**
	 * Get the end time of the session
	 * 
	 * @return the end time of the lesson
	 */
	public LocalTime getEndTime() {
		return this.endTime;
	}
	
	@Override
	public String toString() {
		return "Session: " + this.name + "(" + this.startTime.toString() + 
				" - " + this.endTime.toString() + ")";
	}
	
}
