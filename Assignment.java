import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.ArrayList;

public class Assignment implements Calendar {

	static int eventCounter = 0;
	private class Event implements Appointment, Comparable<Appointment> {
		final String location;
		final String descriptor;
		final Date when;
		final int id;
		
		private Event(Date time, String place, String description) {
			location = place;
			descriptor = description;
			when = time;
			id = ++eventCounter;
		}
		
		// used for ceiling requests only
		private Event(Date timeOnly) {
			location = "";
			descriptor = "";
			when = timeOnly;
			id = 0;
		}
		
		@Override public String getDescription() {return descriptor;}
		@Override public String getLocation() {return location;}
		@Override public Date getStartTime() {return when;}
		
		@Override
		public int compareTo(Appointment other) {
			
			if (this.getStartTime().equals(other.getStartTime()))
				if (this.getLocation().equals(other.getLocation()))
					if (this.getDescription().equals(other.getDescription()))
						// same date location and description -> compare id
						return other instanceof Event ? this.id - ((Event) other).id : this.id;
					else // different description
						return this.getDescription().compareTo(other.getDescription());			
				else // different location
					return this.getLocation().compareTo(other.getLocation());
			else // different date <- highest priority, checked first
				return this.getStartTime().compareTo(other.getStartTime());
		}
		
		// for debugging
		@Override
		public String toString() {
			return String.format("\"%s\" %t at:\"%s\" id:%d", descriptor, when, location, id);
		}
	}
	
	// stores an appointment tree for each unique location
	private HashMap<String, TreeSet<Appointment>> eventsByLocation;
	// stores all appointments
	private TreeSet<Appointment> allEvents;

	public Assignment() {
		eventsByLocation = new HashMap<>();
		allEvents = new TreeSet<>();
	}

	public int size() { return allEvents.size(); }
	
	@Override
	public List<Appointment> getAppointments(String location) {
		if (location == null) throw new IllegalArgumentException("did you mean null island?");
		
		// create a new ArrayLst from the tree containing all appointments at %location%
		return new ArrayList<Appointment> (eventsByLocation.getOrDefault(location, new TreeSet<>()));
	}

	@Override
	public Appointment getNextAppointment(Date when) {
		if(when == null) throw new IllegalArgumentException("cannot determine null time");
		
		return allEvents.ceiling(new Event(when));
	}

	@Override
	public Appointment getNextAppointment(Date when, String location) {
		if (when == null || location == null) throw new IllegalArgumentException("null variable(s)");
		
		// look for event at or after when in %location% tree
		return eventsByLocation.getOrDefault(location, new TreeSet<>()).ceiling(new Event(when));
	}

	@Override
	public void add(String description, Date when, String location) {
		if (when == null || description == null ||  location == null)
			throw new IllegalArgumentException("null variable(s)");
		
		Event e = new Event(when, location, description);
		
		// get the tree stored at %location% in the hashmap and add event to it
		TreeSet<Appointment> locationTree = eventsByLocation.getOrDefault(location, new TreeSet<>());
		locationTree.add(e);
		eventsByLocation.putIfAbsent(location, locationTree);
		
		allEvents.add(e);
	}
	
	// throws NullPointerException if location does not exist in this class 
	// (i.e., the object was never added to this instance of Calendar)
	@Override
	public void remove(Appointment appointment) {
		if (appointment == null) throw new IllegalArgumentException("failed to remove null appointment");
		
		allEvents.remove(appointment);
		// remove event from %location% tree if the tree exists, else NullPointerException
		eventsByLocation.get(appointment.getLocation()).remove(appointment);
	}
}
