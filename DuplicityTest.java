import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.junit.Test;

public class DuplicityTest {

	
	// creates a calendar with n appointments at the specified date
	private static Calendar busyDay(int n, Date time) {
		Calendar crazyCal = new Assignment();
		for (int i = 0; i < n; i++)
			crazyCal.add(Integer.toString(i), time, i % 2 == 0 ? "back" : "forth");
		return crazyCal;
	}

	@Test
	public void testGetNextAppointmentDateSameDay() {
		
		Date thatDay = new Date();
		int numApp = 20;
		Calendar booked = busyDay(numApp, thatDay);
		
		ArrayList<String> VALID_DESCRIPTION = new ArrayList<>(numApp);
		for (int i = 0; i < numApp; i++)
			VALID_DESCRIPTION.add(Integer.toString(i));
		
		for (int i = 0; i < numApp; i++) {
			Appointment a = booked.getNextAppointment(thatDay);
			assertEquals(a.getStartTime(), thatDay);
			assertTrue(VALID_DESCRIPTION.contains(a.getDescription()));
			booked.remove(a);
			VALID_DESCRIPTION.remove(a.getDescription());
		}
	}
	
	// creates a calendar storing a list of appointments 
	// with descriptions specified by the array at the same date and time
	private static Calendar multiDescriptionEvent(String[] descriptions, Date time, String location) {
		Calendar complexEvent = new Assignment();
		for (int i = 0; i < descriptions.length; i++)
			complexEvent.add(descriptions[i], time, location);
		return complexEvent;
	}


	@Test
	public void testGetNextAppointmentDateSameDayLocation() {
		
		ArrayList<String> DESCRIPTIONS = new ArrayList<String> 
			(Arrays.asList("beware the jabberwock my son", 
					"the jaws that bite", "the claws that catch", 
					"o frabjous day", "callooh callay"));		
		Date frabjous = new Date();
		String location = "underland";
		Calendar oraculum = multiDescriptionEvent(
				DESCRIPTIONS.toArray(new String[0]), frabjous, location);
		
		for (int i = 0, stop = DESCRIPTIONS.size(); i < stop; i++) {
			Appointment a = oraculum.getNextAppointment(frabjous);
			assertEquals(a.getStartTime(), frabjous);
			assertTrue(DESCRIPTIONS.contains(a.getDescription()));
			assertEquals(a.getLocation(), location);
			oraculum.remove(a);
			DESCRIPTIONS.remove(a.getDescription());
		}
	}
	
	// create a calendar with n appointments with the specified date, location and description
	private static Calendar nDuplicates(int n, Date time, String location, String description) {
		Calendar examTime = new Assignment();
		for (int i = 0; i < n; i++)
			examTime.add(description, time, location);
		return examTime;
	}
		
	@Test
	public void testGetNextAppointmentDateDuplicates() {
		String DESCRIPTION = "exams", LOCATION = "uni";
		Date examTime = new Date();
		int stressLevel = 9000 + 5; // over 9000
		Calendar studentDiary = nDuplicates(stressLevel, examTime, LOCATION, DESCRIPTION);
		
		assertEquals(studentDiary.getAppointments(LOCATION).size(), stressLevel);
		for (int i = 0; i < stressLevel; i++) {
			Appointment exam = studentDiary.getNextAppointment(examTime);
			assertEquals(exam.getStartTime(), examTime);
			assertEquals(exam.getDescription(), DESCRIPTION);
			assertEquals(exam.getLocation(), LOCATION);
			studentDiary.remove(exam);
		}
		assertNull(studentDiary.getNextAppointment(examTime));
	}
	
}
