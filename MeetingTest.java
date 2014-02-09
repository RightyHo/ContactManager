import org.junit.*;
import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;

public class MeetingTest {
	private Set<Contact> meetingAttendees;
	Calendar date;
	Meeting myMeeting;
	int intOutput;
	int intExpected;
	String strOutput;
	String strExpected;
	Calendar dateOutput;
	Calendar dateExpected;
    Contact matt;
    Contact dom;

	@Before
	public void buildUp(){
		intOutput = 0;
		intExpected = 0;
		strOutput = "";
		strExpected = "";
	}

	@Test
	public void testsGetId(){
		date = new GregorianCalendar();
		date.set(2014,10,31);
		matt = new ContactImpl(8321,"Matt Swinson","Gooner");
		dom = new ContactImpl(2557,"Dom Leavy","Young Gun");
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
		strOutput = matt.getNotes();
		strExpected = "Gooner";
		assertEquals(strExpected,strOutput);
	}
	
}