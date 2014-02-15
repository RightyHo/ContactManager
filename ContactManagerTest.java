import org.junit.*;
import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;
import java.util.HashSet;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

public class ContactManagerTest {
	private ContactManager diary;
	private Set<Contact> contactsGroup;
	private Calendar meetingDate; 
	private int output;
    private Set<Contact> aux;
	
	public ContactManagerTest(){
		diary = new ContactManagerImpl();
		contactsGroup = new HashSet<Contact>();
		meetingDate = new GregorianCalendar();
		output = 0;
	}
	@Test
	public void testsAddFutureMeeting(){
		diary.addNewContact("Jamie O'Regan","Best buddy");
		diary.addNewContact("Travis Wallach","MC");
		diary.addNewContact("Wade Kelly","Groomsman");
        aux = new HashSet<Contact>();
        aux = diary.getContacts("Jamie O'Regan");
		contactsGroup.addAll(aux);
        aux = diary.getContacts("Travis Wallach");
		contactsGroup.addAll(aux);
        aux = diary.getContacts("Wade Kelly");
		contactsGroup.addAll(aux);
		//set future date
		meetingDate.set(Calendar.YEAR,2014);
		meetingDate.set(Calendar.MONTH,Calendar.OCTOBER);
		meetingDate.set(Calendar.DAY_OF_MONTH,31);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        System.out.println("Future date for meeting: " + dateFormat.format(meetingDate.getTime()));
        //test using existing contacts & future date
		output = diary.addFutureMeeting(contactsGroup,meetingDate);
		assertTrue(output != 0);
        System.out.println("The meeting ID is " + String.valueOf(output));
		//test using existing contacts & past date
		try{
            meetingDate.set(Calendar.YEAR,1984);
            meetingDate.set(Calendar.MONTH,Calendar.SEPTEMBER);
            meetingDate.set(Calendar.DAY_OF_MONTH,2);
            System.out.println("Past date for meeting: " + dateFormat.format(meetingDate.getTime()));
			output = diary.addFutureMeeting(contactsGroup,meetingDate);
		} catch(IllegalArgumentException ex){
			System.out.println("Error you entered a date in the past!");
            ex.printStackTrace();
		}
		//test using non-existant contacts & future date
		try{
			Contact mysteryPerson = new ContactImpl(2346,"Joe Blogs","made up person not in contacts");
			Set<Contact> mysteryList = new HashSet<Contact>();
			mysteryList.add(mysteryPerson);
			meetingDate.set(2022,06,28);
			output = diary.addFutureMeeting(mysteryList,meetingDate);
		} catch(IllegalArgumentException ex){
			System.out.println("Error you entered a non-existant contact!");
            ex.printStackTrace();
		}
	}
    @Test
    public void testsGetPastMeeting(){
        
    }
}


















































































