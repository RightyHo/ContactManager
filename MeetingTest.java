import org.junit.*;
import static org.junit.Assert.*;
import java.util.Calendar;

public class MeetingTest {
	private Set<Contacts> meetingAttendees;
	Calendar date;
	Meeting myMeeting;
	int intOutput;
	int intExpected;
	String strOutput;
	String strExpected;
	Calendar dateOutput;
	Calendar dateExpected;

	@Before
	public void buildUp(){
		intOutput = 0;
		intExpected = 0;
		strOutput = "";
		strExpected = "";
	}

	@Test
	public void testsGetId(){
		date = new Calendar();
		date.set(2014,10,31);
		Contact matt = new ContactImpl(8321,"Matt Swinson","Gooner");
		Contact dom = new ContactImpl(2557,"Dom Leavy","Young Gun");
		meetingAttendees.add(matt);
		meetingAttendees.add(dom);
		myMeeting = new MeetingImpl(411,date,meetingAttendees);
		intOutput = myMeeting.getId();
		intExpected = 411;
		assertEquals(intExpected,intOutput);
	}
	@Test
	public void testsGetDate(){
		dateOutput = myMeeting.getDate();
		dateExpected = date;
		assertEquals(dateExpected,dateOutput);
	}
	@Test
	public void testsGetContacts(){
		strOutput = myContact.getNotes();
		strExpected = "NBA legend";
		assertEquals(Expected,output);	
	}
	
}