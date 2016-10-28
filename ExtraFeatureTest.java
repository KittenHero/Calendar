import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

public class ExtraFeatureTest {

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
	public void testGetAppointmentsSorted() {
		
		Date[] moments = dateRange(50l, 200l, 20l);
		Calendar oddEvens = oddLocales(moments);
		
		Appointment[] odds = oddEvens.getAppointments("odd").toArray(new Appointment[0]);
		
		assertEquals(odds.length, moments.length/2);
		for (int i = 0; i < odds.length; i++) {
			assertEquals(odds[i].getDescription(), Integer.toString(2*i + 1));
			assertEquals(odds[i].getLocation(), "odd");
			assertEquals(odds[i].getStartTime(), moments[2*i + 1]);
		}
		
		Appointment[] evens = oddEvens.getAppointments("even").toArray(new Appointment[0]);
		
		assertEquals(evens.length, moments.length - odds.length);
		for (int i = 0; i < evens.length; i++) {
			assertEquals(evens[i].getDescription(), Integer.toString(2*i));
			assertEquals(evens[i].getLocation(), "even");
			assertEquals(evens[i].getStartTime(), moments[2*i]);
		}
	}
	
}
