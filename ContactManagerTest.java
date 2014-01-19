import org.junit.*;
import static org.junit.Assert.*;

public class ContactManagerTest {
	private ContactManager diary;
	private Set<Contacts> contactsGroup;
	public ContactManagerTest(){
		diary = new LinkedList();	//Change to ArrayContactManager if decide to implement ContactManager as an array
		contactsGroup = new HashSet<Contacts>(); //Change to TreeSet<Contacts> if the order of the elements is important
	}
	
	@Test
	public void testsAddFutureMeeting(){
		diary.addNewContact("Jamie O'Regan","Best buddy");
		diary.addNewContact("Travis Wallach","MC");
		diary.addNewContact("Wade Kelly","Groomsman");
		
	}
}