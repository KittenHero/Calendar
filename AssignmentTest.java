import static org.junit.Assert.*;

import org.junit.Test;
import java.util.List;
import java.util.Arrays;
import java.util.Date;

public class AssignmentTest {

	
	@Test
	public void calendarScopeTest() {
		
		Date dueDate = new Date(16092016);
		Calendar cal = new Assignment(),
				today = new Assignment();
		
		today.add("assignment due", dueDate, "ilernet");
		assertNull(cal.getNextAppointment(dueDate));
	}
	
	
	// creates an array of dates 
	private static Date[] dateRange(long from, long to, long step) {
		
		// ceiling division: y/x = floor division: (y + x - 1)/x for x, y > 0
		Date[] moments = new Date[(int) ((to - from + step - 1)/step)];
		
		for (int i = 0; from + i*step < to; i++) {
			moments[i] = new Date(from + i*step);
		}
		
		return moments;
	}
	
	// create a calendar from an array of dates
	private static Calendar oddLocales(Date[] moments) {
		
		Calendar oddEvens = new Assignment();
		
		for (int i = 0; i < moments.length; i++) {
			oddEvens.add(Integer.toString(i), moments[i], i % 2 == 0 ? "even" : "odd");
		}
		
		return oddEvens;
	}
	
	@Test
	public void testGetNextAppointmentDateAtTime() {
		
		Date[] moments = dateRange(20, 100, 4);
		
		Calendar oddEvens = oddLocales(moments);
		
		for (int i = 0; i < moments.length; i++) {
			Appointment a = oddEvens.getNextAppointment(moments[i]);
			assertEquals(a.getStartTime(), moments[i]);
			assertEquals(a.getDescription(), Integer.toString(i));
		}
	}
	
	@Test
	public void testGetNextAppointmentDateAfterTime() {
		
		Date[] moments = dateRange(20, 100, 4);
		
		Calendar oddEvens = oddLocales(moments);
		
		for (int i = 0; i < moments.length; i++) {
			Appointment a = oddEvens.getNextAppointment(new Date(moments[i].getTime() - 1));
			assertEquals(a.getStartTime(), moments[i]);
			assertEquals(a.getDescription(), Integer.toString(i));
		}
	}

	@Test
	public void testGetAppointments() {
		Date[] moments = dateRange(7, 89, 3);
		Calendar oddEvens = oddLocales(moments);

		Appointment[] evens = oddEvens.getAppointments("even").toArray(new Appointment[0]);
		Arrays.sort(evens); // assuming not sorted
		for (int i = 0; i < evens.length; i++) {
			assertEquals(evens[i].getLocation(), "even");
			assertEquals(evens[i].getStartTime(), moments[2*i]);
			assertEquals(evens[i].getDescription(), Integer.toString(2*i));
		}
	}
	
	

	@Test
	public void testRemove() {
		
		Date[] moments = dateRange(7, 89, 3);
		Calendar oddEvens = oddLocales(moments);
		
		// removing evens
		Appointment[] evens = oddEvens.getAppointments("even").toArray(new Appointment[0]);
		Arrays.sort(evens); // assuming not sorted
		for (int i = 0; i < evens.length; i++) {
			assertEquals(evens[i], oddEvens.getNextAppointment(moments[2*i], "even"));
			oddEvens.remove(evens[i]);
			assertNotEquals(evens[i], oddEvens.getNextAppointment(moments[2*i], "even"));
		}
		
		// removing odds
		for (Appointment a : oddEvens.getAppointments("odd"))
			oddEvens.remove(a);
		
		// empty tree
		for (int i = 0; i < moments.length; i++)
			assertNull(oddEvens.getNextAppointment(moments[i]));
	}
	
	@Test
	public void testGetAppointmentListNotModified() {
		Date[] moments = dateRange(7l, 89l, 3l);
		Calendar oddEvens = oddLocales(moments);
		
		List<Appointment> oddList = oddEvens.getAppointments("odd");
		Appointment[] odds = oddList.toArray(new Appointment[0]);
		
		// removing odds
		for (int i = 0; i < odds.length; i++)
			oddEvens.remove(odds[i]);
		// adding odd
		oddEvens.add("not in original", moments[0], "odd");
		
		// check list not modified
		assertEquals(oddList, Arrays.asList(odds));
		for (Appointment a : oddList) {
			assertNotNull(a);
			assertEquals(a.getLocation(), "odd");
			assertNotEquals(a.getDescription(), "not in original");
		}
	}
	

}