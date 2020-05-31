package util;

import java.time.LocalDate;
import java.time.LocalTime;

public class Clock {
	private static final LocalDate DATE = LocalDate.of(2020,04,20);
	private static final LocalTime TIME = LocalTime.of(14, 0);
	
	private Clock() {}
	
	public static LocalDate getDate() {
		return DATE;
	}
	
	public static LocalTime getTime() {
		return TIME;
	}
}
