import java.util.Date;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class InvalidArgsTest {


	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testGetAppointmentsNullDate() {
		Calendar strangeCalendar = new Assignment();
		
		thrown.expect(IllegalArgumentException.class);
		strangeCalendar.getAppointments(null);
	}
	
	@Test
	public void testGetNextAppointmentNullDate() {
		Calendar strangeCalendar = new Assignment();
		
		thrown.expect(IllegalArgumentException.class);
		strangeCalendar.getNextAppointment(null);
	}
	@Test
	public void testGetNextAppointmentNullLocation() {
		Calendar strangeCalendar = new Assignment();
		
		thrown.expect(IllegalArgumentException.class);
		strangeCalendar.getNextAppointment(new Date(0), null);
	}
	public void testRemoveNull() {
		Calendar today = new Assignment();
		
		thrown.expect(IllegalArgumentException.class);
		today.remove(null);
	}
	@Test
	public void testAddNullDescription() {
		Calendar testCal = new Assignment();
		thrown.expect(IllegalArgumentException.class);
		testCal.add(null, new Date(), "some place");
	}
	@Test
	public void testAddNullDate() {
		Calendar testCal = new Assignment();
		thrown.expect(IllegalArgumentException.class);
		testCal.add("important", null, "some place");
	}
	@Test
	public void testAddNullLocation() {
		Calendar testCal = new Assignment();
		thrown.expect(IllegalArgumentException.class);
		testCal.add("important", new Date(), null);
	}
	@Test
	public void testAddMultiNull() {
		Calendar testCal = new Assignment();
		thrown.expect(IllegalArgumentException.class);
		testCal.add(null, null, "some place");
	}
}
