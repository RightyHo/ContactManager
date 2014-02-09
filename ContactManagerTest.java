import org.junit.*;
import static org.junit.Assert.*;
import java.util.Calendar;

public class ContactManagerTest {
	private ContactManager diary;
	private Set<Contacts> contactsGroup;
	private Calendar meetingDate; 
	int output;
	
	public ContactManagerTest(){
		diary = new ContactManagerImpl();
		contactsGroup = new HashSet<Contacts>(); //Change to TreeSet<Contacts> if the order of the elements is important
		meetingDate = new Calendar();
		output = 0;
	}
	@Test
	public void testsAddFutureMeeting(){
		diary.addNewContact("Jamie O'Regan","Best buddy");
		diary.addNewContact("Travis Wallach","MC");
		diary.addNewContact("Wade Kelly","Groomsman");
		contactsGroup.add(getContacts("Jamie O'Regan")); //may need to change this to just .addAll
		contactsGroup.add(getContacts("Travis Wallach")); //may need to change this to just .addAll
		contactsGroup.add(getContacts("Wade Kelly")); //may need to change this to just .addAll		
		//set future date
		meetingDate.set(2014,06,28);
		//test using existing contacts & future date
		output = diary.addFutureMeeting(contactsGroup,meetingDate);
		assertTrue(output != 0);
		//test using existing contacts & past date
		try{
			meetingDate.set(1984,09,02);
			output = diary.addFutureMeeting(contactsGroup,meetingDate);
		} catch(IllegalArgumentException){
			System.out.println("Error you entered a date in the past!")
		}
		//test using non-existant contacts & future date
		try{
			Contact mysteryPerson = new AndrewsContact(2346,"Joe Blogs","made up person not in contacts");
			Set<Contacts> mysteryList = new HashSet<Contacts>();
			mysteryList.add(mysteryPerson);
			meetingDate.set(2022,06,28);
			output = diary.addFutureMeeting(mysteryList,meetingDate);
		} catch(IllegalArgumentException){
			System.out.println("Error you entered a non-existant contact!");
		}
	}
}