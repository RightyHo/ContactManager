import org.junit.*;
import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Iterator;
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
	}
    @Before
    public void buildUp(){
        contactsGroup = new HashSet<Contact>();
        aux = new HashSet<Contact>();
		meetingDate = new GregorianCalendar();
		output = 0;
        //add new contacts
		diary.addNewContact("Jamie O'Regan","Best buddy");
		diary.addNewContact("Travis Wallach","MC");
		diary.addNewContact("Wade Kelly","Groomsman");
        diary.addNewContact("Richard Barker","Neighbour");
		diary.addNewContact("Kate Crowne","Family friend");
		diary.addNewContact("Laura Edwards","Girl friend");
        diary.addNewContact("Willem Botha","Squash Guru");
		diary.addNewContact("Oli Callington","Footie fiend");
		diary.addNewContact("Tondi Busse","DJ");
    }
	@Test
	public void testsAddFutureMeeting(){
        //add new contacts
//		diary.addNewContact("Jamie O'Regan","Best buddy");
//		diary.addNewContact("Travis Wallach","MC");
//		diary.addNewContact("Wade Kelly","Groomsman");
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
//            ex.printStackTrace();
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
//            ex.printStackTrace();
		}
	}
    @Test
    public void testsGetPastMeeting(){
        //add new past meeting
        aux = diary.getContacts("Willem Botha");
		contactsGroup.addAll(aux);
        aux = diary.getContacts("Oli Callington");
		contactsGroup.addAll(aux);
        aux = diary.getContacts("Tondi Busse");
		contactsGroup.addAll(aux);
		//set past date
		meetingDate.set(Calendar.YEAR,2013);
		meetingDate.set(Calendar.MONTH,Calendar.JANUARY);
		meetingDate.set(Calendar.DAY_OF_MONTH,28);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        System.out.println("Date of Savoy Awards: " + dateFormat.format(meetingDate.getTime()));
        //test using existing contacts & past date
		diary.addNewPastMeeting(contactsGroup,meetingDate,"Savoy Equity Awards Dinner");
        //test passing valid past meeting id
        List<Integer> ids = diary.getPastMeetingIdList();
        int expectedId = ids.get(0);
        PastMeeting outputMeeting = diary.getPastMeeting(ids.get(0));
        output = outputMeeting.getId();
        assertEquals(expectedId,output);
        //test passing a valid meeting id for a meeting in the future
            //add new future meeting
        
        //test passing an invalid meeting id
        outputMeeting = diary.getPastMeeting(9999);
        assertNull(outputMeeting);
    }
    @Test
    public void testsGetFutureMeeting(){
        //add new future meeting
        aux = diary.getContacts("Jamie O'Regan");
		contactsGroup.addAll(aux);
        aux = diary.getContacts("Willem Botha");
		contactsGroup.addAll(aux);
        aux = diary.getContacts("Richard Barker");
		contactsGroup.addAll(aux);
		meetingDate.set(Calendar.YEAR,2014);
		meetingDate.set(Calendar.MONTH,Calendar.JUNE);
		meetingDate.set(Calendar.DAY_OF_MONTH,21);
        int expectedId = diary.addFutureMeeting(contactsGroup,meetingDate);
        
        //test with an valid ID and date
        FutureMeeting meetingOutput = diary.getFutureMeeting(expectedId);
        output = meetingOutput.getId();
        assertEquals(expectedId,output);
        //test with an invalid ID
        meetingOutput = diary.getFutureMeeting(1111);
        assertNull(meetingOutput);
        
        //add new past meeting
        aux = diary.getContacts("Laura Edwards");
		contactsGroup.addAll(aux);
        aux = diary.getContacts("Willem Botha");
		contactsGroup.addAll(aux);
		meetingDate.set(Calendar.YEAR,2014);
		meetingDate.set(Calendar.MONTH,Calendar.FEBRUARY);
		meetingDate.set(Calendar.DAY_OF_MONTH,01);
        diary.addNewPastMeeting(contactsGroup,meetingDate,"Dry January fall off wagon party");
        List<Integer> pastMeetingIds = diary.getPastMeetingIdList();
        int pastId = pastMeetingIds.get(0);
        //test with the ID of a meeting that happened in the past
        try{
            diary.getFutureMeeting(pastId);
        } catch (IllegalArgumentException ex){
            System.out.println("Error the ID of a meeting that you searched on happened in the past!");
        }
    }
    @Test
    public void testsAddNewPastMeeting(){
        //add new contacts
//		diary.addNewContact("Richard Barker","Neighbour");
//		diary.addNewContact("Kate Crowne","Family friend");
//		diary.addNewContact("Laura Edwards","Girl friend");
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
//            ex.printStackTrace();
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
//            ex.printStackTrace();
		}
        //test leaving one of the arguments null
        try{
            String nullString = null;
            diary.addNewPastMeeting(contactsGroup,meetingDate,nullString);
        } catch(NullPointerException ex){
            System.out.println("Error the meeting notes string you entered was null!");
//            ex.printStackTrace();
        }
        try{
            meetingDate = null;
            diary.addNewPastMeeting(contactsGroup,meetingDate,"meeting date is null btw");
        } catch(NullPointerException ex){
            System.out.println("Error the meeting date you entered was null!");
//            ex.printStackTrace();
        }
        try{
            contactsGroup = null;
            meetingDate.set(2011,03,8);
            diary.addNewPastMeeting(contactsGroup,meetingDate,"contactsGroup is null btw");
        } catch(NullPointerException ex){
            System.out.println("Error the contacts group you entered was null!");
//            ex.printStackTrace();
        }
    }
    @Test
    public void testsGetPastMeetingList(){
        //test that method returns a list of meetings when a contact with a past meeting is passed
        //check that the returned list is in chronological order & doesn't contain duplicates
        Contact searchContact = null;
        //add new contacts
		diary.addNewContact("Richard Barker","Neighbour");
        //add contacts to contact set
        aux = diary.getContacts("Richard Barker");
		contactsGroup.addAll(aux);
		//set past date
		meetingDate.set(Calendar.YEAR,2013);
		meetingDate.set(Calendar.MONTH,Calendar.JULY);
		meetingDate.set(Calendar.DAY_OF_MONTH,1);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        System.out.println("Date of Football game: " + dateFormat.format(meetingDate.getTime()));
        //test using existing contacts & past date
		diary.addNewPastMeeting(contactsGroup,meetingDate,"Watched football at North19");
        //get contact Richard Barker
        Set<Contact> auxSet = diary.getContacts("Richard Barker");
        if(auxSet.isEmpty()){
            System.out.println("get contacts function returned an empty set for Richard Barker");
        } else {
            Contact[] tempArray = auxSet.toArray(new Contact[0]);
            for(int i=0;i<tempArray.length;i++){
                searchContact = tempArray[i];
                System.out.println("contents of the searchContact string: " + searchContact.getName());
            }
        }
        List<PastMeeting> listOutput = diary.getPastMeetingList(searchContact);
        if(listOutput.isEmpty()){
            System.out.println("Can't find any past meetings with Richard Barker");
        } else {
            System.out.println("number of meetings with Barks: " + Integer.toString(listOutput.size()));
            System.out.println("meetings ID Barks: " + Integer.toString(listOutput.get(0).getId()));
            System.out.println("meetings date Barks: " + dateFormat.format(listOutput.get(0).getDate().getTime()));
            output = listOutput.get(0).getId();
            List<Integer> idListOutput = diary.getPastMeetingIdList();
            int expected = idListOutput.get(0);
            assertEquals(expected,output);
            //test first meeting date
//            Calendar calOutput = listOutput.get(0).getDate();
//            Calendar calExpected = new GregorianCalendar(2013,07,1);
//            assertEquals(calExpected,calOutput);
        }
        //test passing a contact with no past meetings
        //get contact Wade Kelly - who only has a future meeting
        searchContact = null;
        auxSet = diary.getContacts("Wade Kelly");
        if(auxSet.isEmpty()){
            System.out.println("get contacts function returned an empty set for Wade Kelly");
        } else {
            Contact[] tempArray = auxSet.toArray(new Contact[0]);
            for(int i=0;i<tempArray.length;i++){
                searchContact = tempArray[i];
                System.out.println("contents of the searchContact string: " + searchContact.getName());
            }
        }
        List<PastMeeting> listOutput2 = diary.getPastMeetingList(searchContact);
        if(listOutput2.isEmpty()){
            System.out.println("As expected there are no past meetings with Wade Kelly");
        } else {
            System.out.println("ERROR we have found a past meeting with Wade Kelly");
            System.out.println("meetings ID Wade: " + Integer.toString(listOutput2.get(0).getId()));
        }
        //test passing a contact that does not exist
        try {
            Contact nonPerson = new ContactImpl(4678,"Ghost","contact doesn't exist");
            listOutput = diary.getPastMeetingList(nonPerson);
        } catch (IllegalArgumentException ex){
            System.out.println("Error the contact you entered doesn't exist!");
//            ex.printStackTrace();
        }
    }
    
}



















































































