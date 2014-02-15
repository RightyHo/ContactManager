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
	
//	public ContactManagerTest(){
//		diary = new ContactManagerImpl();
//		contactsGroup = new HashSet<Contact>();
//		meetingDate = new GregorianCalendar();
//		output = 0;
//	}
    @Before
    public void buildUp(){
        contactsGroup = new HashSet<Contact>();
        aux = new HashSet<Contact>();
        diary = new ContactManagerImpl();
		meetingDate = new GregorianCalendar();
		output = 0;
    }
	@Test
	public void testsAddFutureMeeting(){
        //add new contacts
		diary.addNewContact("Jamie O'Regan","Best buddy");
		diary.addNewContact("Travis Wallach","MC");
		diary.addNewContact("Wade Kelly","Groomsman");
        //add contacts to contact set
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
        //test passing valid past meeting id
        
        //test passing a valid meeting id for a meeting in the future
        
        //test passing an invalid meeting id
    }
    @Test
    public void testsAddNewPastMeeting(){
        //add new contacts
		diary.addNewContact("Richard Barker","Neighbour");
		diary.addNewContact("Kate Crowne","Family friend");
		diary.addNewContact("Laura Edwards","Girl friend");
        //add contacts to contact set
        aux = diary.getContacts("Richard Barker");
		contactsGroup.addAll(aux);
        aux = diary.getContacts("Kate Crowne");
		contactsGroup.addAll(aux);
        aux = diary.getContacts("Laura Edwards");
		contactsGroup.addAll(aux);
		//set past date
		meetingDate.set(Calendar.YEAR,2011);
		meetingDate.set(Calendar.MONTH,Calendar.JULY);
		meetingDate.set(Calendar.DAY_OF_MONTH,14);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        System.out.println("Date of Past Croyde Trip: " + dateFormat.format(meetingDate.getTime()));
        //test using existing contacts & past date
		diary.addNewPastMeeting(contactsGroup,meetingDate,"Summer trip to the beach in 2011");
		//add some test code to make sure that this past meeting was added correctly
		//test using existing contacts & future date
		try{
            meetingDate.set(Calendar.YEAR,2015);
            meetingDate.set(Calendar.MONTH,Calendar.SEPTEMBER);
            meetingDate.set(Calendar.DAY_OF_MONTH,2);
            System.out.println("Lauras birthday next year: " + dateFormat.format(meetingDate.getTime()));
			diary.addNewPastMeeting(contactsGroup,meetingDate,"Laura will be 31");
		} catch(IllegalArgumentException ex){
			System.out.println("Error you entered a date in the future!");
            ex.printStackTrace();
		}
		//test using non-existant contacts & past date
		try{
			Contact mysteryPerson = new ContactImpl(2346,"Joe Blogs","made up person not in contacts");
			Set<Contact> mysteryList = new HashSet<Contact>();
			mysteryList.add(mysteryPerson);
			meetingDate.set(2009,06,28);
			diary.addNewPastMeeting(mysteryList,meetingDate,"Lets face it this never happened");
		} catch(IllegalArgumentException ex){
			System.out.println("Error you entered a non-existant contact!");
            ex.printStackTrace();
		}
        //test leaving one of the arguments null
        try{
            String nullString = null;
            diary.addNewPastMeeting(contactsGroup,meetingDate,nullString);
        } catch(NullPointerException ex){
            System.out.println("Error the meeting notes string you entered was null!");
            ex.printStackTrace();
        }
        try{
            meetingDate = null;
            diary.addNewPastMeeting(contactsGroup,meetingDate,"meeting date is null btw");
        } catch(NullPointerException ex){
            System.out.println("Error the meeting date you entered was null!");
            ex.printStackTrace();
        }
        try{
            contactsGroup = null;
            meetingDate.set(2011,03,8);
            diary.addNewPastMeeting(contactsGroup,meetingDate,"contactsGroup is null btw");
        } catch(NullPointerException ex){
            System.out.println("Error the contacts group you entered was null!");
            ex.printStackTrace();
        }

    }
}



















































































