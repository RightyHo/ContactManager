import org.junit.*;
import static org.junit.Assert.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

public class ContactManagerTest {
    private ContactManager diary;
    private List<Integer> pastMeetIds;
    private List<Integer> futMeetIds;
    private Set<Contact> contactsGroup;
    private Calendar meetingDate;
    private Set<Contact> aux;
    private int output;
    private int expected;
    private String strOutput;
    private String strExpected;
    
    @Before
    public void buildUp(){
        //add other specific output assert variables?
        output = 0;
        expected = 0;
        strOutput = "";
        strExpected = "";
        
        diary = new ContactManagerImpl();
        pastMeetIds = new ArrayList<Integer>();
        futMeetIds = new ArrayList<Integer>();
        contactsGroup = new HashSet<Contact>();
        aux = new HashSet<Contact>();
        meetingDate = new GregorianCalendar();
        setUpContacts();
        setUpPastMeetings();
        setUpFutureMeetings();
        
        contactsGroup.clear();
        aux.clear();
    }
    
	@Test
	public void testsAddFutureMeeting(){
        //add contacts to contact set
        aux = diary.getContacts("Laura Edwards");
		contactsGroup.addAll(aux);
        aux = diary.getContacts("Jamie O'Regan");
		contactsGroup.addAll(aux);
        aux = diary.getContacts("Nicky Ho");
		contactsGroup.addAll(aux);
		//set future date
		meetingDate.set(Calendar.YEAR,2014);
		meetingDate.set(Calendar.MONTH,Calendar.JUNE);
		meetingDate.set(Calendar.DAY_OF_MONTH,28);
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
			System.out.println("TEST PASSED:  Error you entered a date in the past!");
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
			System.out.println("TEST PASSED:  Error you entered a non-existant contact!");
//            ex.printStackTrace();
		}
	}
    @Test
    public void testsGetPastMeeting(){
        //test passing valid past meeting id
        int expectedId = pastMeetIds.get(0);
        PastMeeting outputMeeting = diary.getPastMeeting(pastMeetIds.get(0));
        output = outputMeeting.getId();
        assertEquals(expectedId,output);
        //test passing a valid meeting id for a meeting in the future
        try {
            expectedId = futMeetIds.get(0);
            outputMeeting = diary.getPastMeeting(futMeetIds.get(0));
            if(outputMeeting != null){
                output = outputMeeting.getId();
                assertEquals(expectedId,output);
            }
        } catch(IllegalArgumentException ex){
            System.out.println("TEST PASSED:  Error you entered a FUTURE meeting ID for getPastMeeting");
        }
        //test passing an invalid meeting id
        outputMeeting = diary.getPastMeeting(9999);
        assertNull(outputMeeting);
    }
    @Test
    public void testsGetFutureMeeting(){
        //test with an valid ID and date
        int expectedId = futMeetIds.get(0);
        FutureMeeting meetingOutput = diary.getFutureMeeting(futMeetIds.get(0));
        output = meetingOutput.getId();
        assertEquals(expectedId,output);
        //test with an invalid ID
        meetingOutput = diary.getFutureMeeting(1111);
        assertNull(meetingOutput);
        //test with the ID of a meeting that happened in the past
        try{
            diary.getFutureMeeting(pastMeetIds.get(1));
        } catch (IllegalArgumentException ex){
            System.out.println("TEST PASSED: Error the ID of a meeting that you searched on happened in the past!");
        }
    }
    @Test
    public void testsAddNewPastMeeting(){
        System.out.println("number of past meetings in diary before adding new one: " + pastMeetIds.size());
        System.out.println("number of future meetings in diary before adding new one: " + futMeetIds.size());
        //add contacts to contact set
        aux = diary.getContacts("Wade Kelly");
        contactsGroup.addAll(aux);
        aux = diary.getContacts("Laura Edwards");
        contactsGroup.addAll(aux);
        aux = diary.getContacts("Travis Wallach");
        contactsGroup.addAll(aux);
        //set past date
        meetingDate.set(Calendar.YEAR,2012);
        meetingDate.set(Calendar.MONTH,Calendar.JANUARY);
        meetingDate.set(Calendar.DAY_OF_MONTH,31);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String description = "Engagement Dinner in Sydney";
        System.out.println(description + ": " + dateFormat.format(meetingDate.getTime()));
        //test using existing contacts & past date
        diary.addNewPastMeeting(contactsGroup,meetingDate,description);
        //add new past meeting ID to pastMeetIds list
        List<Integer> allPastMeets = diary.getPastMeetingIdList();
        pastMeetIds.add(allPastMeets.get(allPastMeets.size()-1));
        System.out.println("number of past meetings in diary AFTER adding new one: " + pastMeetIds.size());
        System.out.println("number of future meetings in diary AFTER adding new one: " + futMeetIds.size());
        //test that past meeting was added correctly
        strOutput = "";
        strExpected = description;
        for(int i=0;i<pastMeetIds.size();i++){
            try{
                int id = pastMeetIds.get(i);
                PastMeeting pm = diary.getPastMeeting(id);
                String s = pm.getNotes();
                System.out.println(s);
                if(s.equals(description)){
                    strOutput = s;
                }
            } catch (NullPointerException ex){
                System.out.println("Error - somethings gone wrong, we've got a null pointer");
            }
        }
        assertEquals(strExpected,strOutput);
        //test using existing contacts & future date
        try{
            meetingDate.set(Calendar.YEAR,2015);
            meetingDate.set(Calendar.MONTH,Calendar.JANUARY);
            meetingDate.set(Calendar.DAY_OF_MONTH,14);
            System.out.println("Mums birthday next year: " + dateFormat.format(meetingDate.getTime()));
            diary.addNewPastMeeting(contactsGroup,meetingDate,"Mums birthday next year");
        } catch(IllegalArgumentException ex){
            System.out.println("TEST PASSED: Error you entered a date in the future!");
//          ex.printStackTrace();
        }
        //test using non-existant contacts & past date
        try{
            Contact mysteryPerson = new ContactImpl(2346,"Joe Blogs","made up person not in contacts");
            Set<Contact> mysteryList = new HashSet<Contact>();
            mysteryList.add(mysteryPerson);
            meetingDate.set(2009,06,28);
            diary.addNewPastMeeting(mysteryList,meetingDate,"Lets face it this never happened");
        } catch(IllegalArgumentException ex){
            System.out.println("TEST PASSED: Error you entered a non-existant contact!");
//          ex.printStackTrace();
        }
        //test leaving one of the arguments null
        try{
            String nullString = null;
            diary.addNewPastMeeting(contactsGroup,meetingDate,nullString);
        } catch(NullPointerException ex){
            System.out.println("TEST PASSED: Error the meeting notes string you entered was null!");
//  	    ex.printStackTrace();
        }
        try{
            meetingDate = null;
            diary.addNewPastMeeting(contactsGroup,meetingDate,"meeting date is null btw");
        } catch(NullPointerException ex){
            System.out.println("TEST PASSED: Error the meeting date you entered was null!");
//          ex.printStackTrace();
        }
        try{
            contactsGroup = null;
            meetingDate.set(2011,03,8);
            diary.addNewPastMeeting(contactsGroup,meetingDate,"contactsGroup is null btw");
        } catch(NullPointerException ex){
            System.out.println("TEST PASSED: Error the contacts group you entered was null!");
//  	    ex.printStackTrace();
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
    private void setUpContacts(){
        //add new contacts to the diary
        diary.addNewContact("Jamie O'Regan","Best buddy");
        diary.addNewContact("Travis Wallach","MC");
        diary.addNewContact("Wade Kelly","Groomsman");
        diary.addNewContact("Richard Barker","Neighbour");
        diary.addNewContact("Kate Crowne","Family friend");
        diary.addNewContact("Laura Edwards","Girl friend");
        diary.addNewContact("Willem Botha","Squash Guru");
        diary.addNewContact("Oli Callington","Footie fiend");
        diary.addNewContact("James Gill","Pedal Juice Partner");
        diary.addNewContact("James McLeod","Law Consultant");
        diary.addNewContact("Nicky Ho","Interior Designer");
        diary.addNewContact("Philippa Ho","Dr");
        diary.addNewContact("Matthew Swinson","Jacky boy fan club");
        diary.addNewContact("Dominic Leavy","B52 bomber");
    }
    private void setUpPastMeetings(){
        //build past meeting 1
        contactsGroup.clear();
        aux.clear();
        //set contact group
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
        //set past meeting 1;
        diary.addNewPastMeeting(contactsGroup,meetingDate,"Savoy Equity Awards Dinner");
        
        //build past meeting 2
        contactsGroup.clear();
        aux.clear();
        //set contact group
        aux = diary.getContacts("Willem Botha");
        contactsGroup.addAll(aux);
        aux = diary.getContacts("Laura Edwards");
        contactsGroup.addAll(aux);
        aux = diary.getContacts("Oli Callington");
        contactsGroup.addAll(aux);
        //set past date
        meetingDate.set(Calendar.YEAR,2014);
        meetingDate.set(Calendar.MONTH,Calendar.FEBRUARY);
        meetingDate.set(Calendar.DAY_OF_MONTH,1);
        //set past meeting 2
        diary.addNewPastMeeting(contactsGroup,meetingDate,"Dry January fall off wagon party");
        
        //build past meeting 3
        contactsGroup.clear();
        aux.clear();
        //set contact group
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
        //set past meeting 3;
        diary.addNewPastMeeting(contactsGroup,meetingDate,"Summer trip to the beach in 2011");
        
        //build past meeting 4
        contactsGroup.clear();
        aux.clear();
        //set contact group
        aux = diary.getContacts("Richard Barker");
        contactsGroup.addAll(aux);
        aux = diary.getContacts("Matthew Swinson");
        contactsGroup.addAll(aux);
        aux = diary.getContacts("Dominic Leavy");
        contactsGroup.addAll(aux);
        //set past date
        meetingDate.set(Calendar.YEAR,2013);
        meetingDate.set(Calendar.MONTH,Calendar.APRIL);
        meetingDate.set(Calendar.DAY_OF_MONTH,11);
        //set past meeting 4;
        diary.addNewPastMeeting(contactsGroup,meetingDate,"Watched Football");
        
        //add new past meetings to the pMeetIds list - could add this separately in the buildUp method?
        pastMeetIds = diary.getPastMeetingIdList();
        
    }
    private void setUpFutureMeetings(){
        //build future meeting 1
        contactsGroup.clear();
        aux.clear();
        //set contact group
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
        //set future meeting 1
        int id = diary.addFutureMeeting(contactsGroup,meetingDate);
        futMeetIds.add(id);
        
        //build future meeting 2
        contactsGroup.clear();
        aux.clear();
        //set contact group
        aux = diary.getContacts("James Gill");
        contactsGroup.addAll(aux);
        aux = diary.getContacts("James McLeod");
        contactsGroup.addAll(aux);
        aux = diary.getContacts("Oli Callington");
        contactsGroup.addAll(aux);
        //set future date
        meetingDate.set(Calendar.YEAR,2014);
        meetingDate.set(Calendar.MONTH,Calendar.JUNE);
        meetingDate.set(Calendar.DAY_OF_MONTH,21);
        //set future meeting 2
        id = diary.addFutureMeeting(contactsGroup,meetingDate);
        futMeetIds.add(id);
        
        //build future meeting 3
        contactsGroup.clear();
        aux.clear();
        //set contact group
        aux = diary.getContacts("Nicky Ho");
        contactsGroup.addAll(aux);
        aux = diary.getContacts("Philippa Ho");
        contactsGroup.addAll(aux);
        aux = diary.getContacts("Laura Edwards");
        contactsGroup.addAll(aux);
        //set future date
        meetingDate.set(Calendar.YEAR,2015);
        meetingDate.set(Calendar.MONTH,Calendar.SEPTEMBER);
        meetingDate.set(Calendar.DAY_OF_MONTH,2);
        //set future meeting 3
        id = diary.addFutureMeeting(contactsGroup,meetingDate);
        futMeetIds.add(id);
        
        //build future meeting 4
        contactsGroup.clear();
        aux.clear();
        //set contact group
        aux = diary.getContacts("Philippa Ho");
        contactsGroup.addAll(aux);
        aux = diary.getContacts("Matthew Swinson");
        contactsGroup.addAll(aux);
        aux = diary.getContacts("Willem Botha");
        contactsGroup.addAll(aux);
        //set future date
        meetingDate.set(Calendar.YEAR,2016);
        meetingDate.set(Calendar.MONTH,Calendar.FEBRUARY);
        meetingDate.set(Calendar.DAY_OF_MONTH,4);
        //set future meeting 4
        id = diary.addFutureMeeting(contactsGroup,meetingDate);
        futMeetIds.add(id);
    }
}



















































































