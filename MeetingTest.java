import org.junit.*;
import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;
import java.util.HashSet;

public class MeetingTest {
	private Set<Contact> meetingAttendees;
	private Calendar date;
	private Meeting myMeeting;
    private Contact matt;
    private Contact dom;
	private int intOutput;
	private int intExpected;
	private String strOutput;
	private String strExpected;
	private Calendar dateOutput;
	private Calendar dateExpected;

	@Before
	public void buildUp(){
        meetingAttendees = new HashSet<Contact>();
        matt = new ContactImpl(8321,"Matt Swinson","Gooner");
		dom = new ContactImpl(2557,"Dom Leavy","Young Gun");
        date = new GregorianCalendar();
		date.set(2014,10,31);
        meetingAttendees.add(matt);
		meetingAttendees.add(dom);
		myMeeting = new MeetingImpl(411,date,meetingAttendees);
		intOutput = 0;
		intExpected = 0;
		strOutput = "";
		strExpected = "";
	}
	@Test
	public void testsGetId(){
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
    //test getNotes method from PastMeeting class
	@Test
    public void testsGetNotes(){
        date.set(2014,1,24);
        PastMeeting coventryGame = new PastMeetingImpl(400,date,meetingAttendees,"Podolski put the game to bed with a brace in the first half");
        strOutput = coventryGame.getNotes();
        strExpected = "Podolski put the game to bed with a brace in the first half";
        assertEquals(strExpected,strOutput);
    }
}


