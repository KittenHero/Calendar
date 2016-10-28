import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MaliciousAppointmentTest {
	class EvilAppointment implements Appointment {
		
		@Override
		public String getDescription() { return ""; }

		@Override
		public String getLocation() { return ""; }

		@Override
		public Date getStartTime() { return new Date(); }
		
	}
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void RemoveExternalAppointmentTest() {
		Calendar cal = new Assignment();
		Appointment dentist = new EvilAppointment();
		
		thrown.expect(RuntimeException.class);
		cal.remove(dentist);
	}
	
	@Test
	public void CalendarConfusionTest() {
		Calendar cal = new Assignment();
		cal.add("java date", new Date(Long.parseLong("CAFEDA7E", 16)), "BADF00D");
		
		Appointment holla = cal.getAppointments("BADF00D").get(0);
		assertNotNull(holla);
		
		cal = new Assignment();
		assertNull(cal.getAppointments("BADF00d").get(0));
		
		thrown.expect(RuntimeException.class);
		cal.remove(holla);
	}
}
