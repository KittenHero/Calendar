import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

public class AppointmentMethodsTest {

	Appointment hakunamatata(Date forTheRestOfYourDay) {
		Calendar jungleCal = new Assignment();
		jungleCal.add("no worries", forTheRestOfYourDay, "suvanha");
		return jungleCal.getNextAppointment(forTheRestOfYourDay);
	}
	
	@Test
	public void DescriptionTest() {
		assertEquals("no worries", hakunamatata(new Date()).getDescription());
	}
	
	@Test
	public void LocationTest() {
		assertEquals("suvanha", hakunamatata(new Date()).getLocation());
	}
	
	@Test
	public void startTimeest() {
		Date today = new Date();
		assertEquals(today, hakunamatata(today).getStartTime());
	}
}
