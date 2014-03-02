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
//        System.out.println("Future date for meeting: " + dateFormat.format(meetingDate.getTime()));
        //test using existing contacts & future date
		output = diary.addFutureMeeting(contactsGroup,meetingDate);
		assertTrue(output != 0);
//        System.out.println("The meeting ID is " + String.valueOf(output));
		//test using existing contacts & past date
		try{
            meetingDate.set(Calendar.YEAR,1984);
            meetingDate.set(Calendar.MONTH,Calendar.SEPTEMBER);
            meetingDate.set(Calendar.DAY_OF_MONTH,2);
//            System.out.println("Past date for meeting: " + dateFormat.format(meetingDate.getTime()));
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
			meetingDate.set(2022,05,28);
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
    public void testsGetMeeting(){
        //test that method outputs the expected meeting when a valid future meeting ID is passed
        Meeting meetOutput = diary.getMeeting(futMeetIds.get(0));
        FutureMeeting fmExpected = diary.getFutureMeeting(futMeetIds.get(0));
        output = meetOutput.getId();
        expected = fmExpected.getId();
        assertEquals(expected,output);
        //test that method outputs the expected meeting when a valid past meeting ID is passed
        meetOutput = diary.getMeeting(pastMeetIds.get(0));
        PastMeeting pmExpected = diary.getPastMeeting(pastMeetIds.get(0));
        output = meetOutput.getId();
        expected = pmExpected.getId();
        assertEquals(expected,output);
        //test passing the method a meeting ID that doesn't exist
        meetOutput = diary.getMeeting(7777);
        assertNull(meetOutput);
    }
    @Test
    public void testsGetFutureMeetingList(){
        //test that method returns a list of meetings when a contact with a future meeting is passed
        //check that the returned list is in chronological order & doesn't contain duplicates
        
        //get contact Philippa Ho
        Contact searchContact = diary.getSingleContact("Philippa Ho");
        //get list of all Philippa Ho's future meetings there should be 2
        List<Meeting> listOutput = diary.getFutureMeetingList(searchContact);
        if(listOutput.isEmpty()){
            System.out.println("ERROR - Can't find any future meetings with Philippa Ho in attendance");
        } else if(listOutput.size() == 1){
            //test that the method returns Philippa Ho's first meeting on 02/09/15
            for(int i=0;i<futMeetIds.size();i++){
                int id = futMeetIds.get(i);
                FutureMeeting fm= diary.getFutureMeeting(id);
                Set<Contact> attendees = fm.getContacts();
                if(attendees.contains(searchContact)){
                    Calendar calOutput = fm.getDate();
                    Calendar calExpected = new GregorianCalendar(2015,8,02);
                    assertTrue(calExpected.equals(calOutput));
                }
            }
        } else {
            //test that returned list is in chronological order & doesn't contain duplicates
            for(int i=0;i<listOutput.size()-1;i++){
                //test for duplicates
                Meeting priorMeet = listOutput.get(i);
                Meeting laterMeet = listOutput.get(i+1);
                assertFalse(priorMeet.equals(laterMeet));
                //test for date order
                Calendar priorDate = priorMeet.getDate();
                Calendar laterDate = laterMeet.getDate();
                assertTrue(priorDate.before(laterDate));
            }
        }
        //test passing a contact with no past meetings
        //get contact Tondi Busse - who only has a past meeting
        searchContact = diary.getSingleContact("Tondi Busse");
        List<Meeting> listOutput2 = diary.getFutureMeetingList(searchContact);
        assertTrue(listOutput2.isEmpty());
        //test passing a contact that does not exist
        try {
            Contact notInDiary = new ContactImpl(5498,"Stranger","contact isn't in my diary");
            listOutput2 = diary.getFutureMeetingList(notInDiary);
        } catch (IllegalArgumentException ex){
            System.out.println("TEST PASSED: Error the contact you entered isn't in my diary!");
//          ex.printStackTrace();
        }
    }
    @Test
    public void testsGetFutureMeetingList2(){
        //test that method returns a list of meetings when a date with a future meeting is passed
        //check that the returned list is in chronological order & doesn't contain duplicates

        //get list of all future meetings on 02/09/15 (there should be just 1)
        meetingDate.set(2015,8,02);
        List<Meeting> listOutput = diary.getFutureMeetingList(meetingDate);
        if(listOutput.isEmpty()){
            System.out.println("ERROR - Can't find any future meetings scheduled for 2nd September 2015");
        } else if(listOutput.size() == 1){
            //test that the method returns a meeting scheduled to take place on 02/09/15
            for(int i=0;i<futMeetIds.size();i++){
                int id = futMeetIds.get(i);
                FutureMeeting fm = diary.getFutureMeeting(id);
                Calendar auxDate = fm.getDate();
                if(auxDate.equals(meetingDate)){
                    output = listOutput.get(0).getId();
                    expected = fm.getId();
                    assertEquals(expected,output);
                }
            }
        } else {
            //test that returned list is in chronological order & doesn't contain duplicates
            for(int i=0;i<listOutput.size()-1;i++){
                //test for duplicates
                Meeting priorMeet = listOutput.get(i);
                Meeting laterMeet = listOutput.get(i+1);
                assertFalse(priorMeet.equals(laterMeet));
                //test for date order
                Calendar priorDate = priorMeet.getDate();
//                System.out.println(priorDate);
                Calendar laterDate = laterMeet.getDate();
//                System.out.println(laterDate);
                if(!priorDate.equals(laterDate)){
                    assertTrue(priorDate.before(laterDate));
                }
            }
        }
    }
    @Test
    public void testsGetPastMeetingList(){
        //test that method returns a list of meetings when a contact with a past meeting is passed
        //check that the returned list is in chronological order & doesn't contain duplicates
        
        //get contact Richard Barker
        Contact searchContact = diary.getSingleContact("Richard Barker");
        //get list of all Richard Barkers past meetings there should be 2
        List<PastMeeting> listOutput = diary.getPastMeetingList(searchContact);
        if(listOutput.isEmpty()){
            System.out.println("ERROR - Can't find any past meetings with Richard Barker in attendance");
        } else if(listOutput.size() == 1){
            //test that the method returns Richard Barkers first meeting on 14/07/11
            for(int i=0;i<pastMeetIds.size();i++){
                int id = pastMeetIds.get(i);
                PastMeeting pm = diary.getPastMeeting(id);
                Set<Contact> attendees = pm.getContacts();
                if(attendees.contains(searchContact)){
                    Calendar calOutput = pm.getDate();
                    Calendar calExpected = new GregorianCalendar(2011, 06, 14);
                    assertTrue(calExpected.equals(calOutput));
                }
            }
        } else {
            //test that returned list is in chronological order & doesn't contain duplicates
            for(int i=0;i<listOutput.size()-1;i++){
                //test for duplicates
                PastMeeting priorMeet = listOutput.get(i);
                PastMeeting laterMeet = listOutput.get(i+1);
                assertFalse(priorMeet.equals(laterMeet));
                //test for date order
                Calendar priorDate = priorMeet.getDate();
                Calendar laterDate = laterMeet.getDate();
                assertTrue(priorDate.before(laterDate));
            }
        }
        //test passing a contact with no past meetings
        //get contact Wade Kelly - who only has a future meeting
        searchContact = diary.getSingleContact("Wade Kelly");
        List<PastMeeting> listOutput2 = diary.getPastMeetingList(searchContact);
        assertTrue(listOutput2.isEmpty());
        //test passing a contact that does not exist
        try {
            Contact nonPerson = new ContactImpl(4678,"Ghost","contact doesn't exist");
            listOutput2 = diary.getPastMeetingList(nonPerson);
        } catch (IllegalArgumentException ex){
            System.out.println("TEST PASSED: Error the contact you entered doesn't exist!");
            //          ex.printStackTrace();
        }
    }
    @Test
    public void testsAddNewPastMeeting(){
//        System.out.println("number of future meetings in diary BEFORE adding new one: " + futMeetIds.size());
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
//        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String description = "Engagement Dinner in Sydney";
//        System.out.println(description + ": " + dateFormat.format(meetingDate.getTime()));
        //test using existing contacts & past date
        diary.addNewPastMeeting(contactsGroup,meetingDate,description);
        //add new past meeting ID to pastMeetIds list
        List<Integer> allPastMeets = diary.getPastMeetingIdList();
        pastMeetIds.add(allPastMeets.get(allPastMeets.size()-1));
//        System.out.println("number of past meetings in diary AFTER adding new one: " + pastMeetIds.size());
        //test that past meeting was added correctly
        strOutput = "";
        strExpected = description;
        for(int i=0;i<pastMeetIds.size();i++){
            try{
                int id = pastMeetIds.get(i);
                PastMeeting pm = diary.getPastMeeting(id);
                String s = pm.getNotes();
//                System.out.println(s);
                if(s.equals(description)){
                    strOutput = s;
                }
            } catch (NullPointerException ex){
                System.out.println("ERROR - somethings gone wrong, we've got a null pointer");
            }
        }
        assertEquals(strExpected,strOutput);
        //test using existing contacts & future date
        try{
            meetingDate.set(Calendar.YEAR,2015);
            meetingDate.set(Calendar.MONTH,Calendar.JANUARY);
            meetingDate.set(Calendar.DAY_OF_MONTH,14);
//            System.out.println("Mums birthday next year: " + dateFormat.format(meetingDate.getTime()));
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
    public void testsAddMeetingNotes(){
        //try adding notes to a valid meeting that occured in the past
        try{
            diary.addMeetingNotes(pastMeetIds.get(0),"Better late than never...meeting was successful!");
        } catch (IllegalArgumentException ex){
            System.out.println("ERROR - the meeting does not exist");
//  	    ex.printStackTrace();
        } catch (IllegalStateException ex){
            System.out.println("ERROR - the meeting is set for a date in the future");
//  	    ex.printStackTrace();
        } catch (NullPointerException ex){
            System.out.println("ERROR - the notes you tried to add are null!");
//  	    ex.printStackTrace();
        }
        strOutput = diary.getPastMeeting(pastMeetIds.get(0)).getNotes();
        strExpected = "Better late than never...meeting was successful!";
        assertEquals(strExpected,strOutput);
        //try adding notes to a valid meeting that will occur in the future
        try{
            diary.addMeetingNotes(futMeetIds.get(0),"This shouldn't work...");
        } catch (IllegalArgumentException ex){
            System.out.println("ERROR - the meeting does not exist");
//  	    ex.printStackTrace();
        } catch (IllegalStateException ex){
            System.out.println("TEST PASSED: ERROR - the meeting is set for a date in the future");
//  	    ex.printStackTrace();
        } catch (NullPointerException ex){
            System.out.println("ERROR - the notes you tried to add are null!");
//  	    ex.printStackTrace();
        }
        //try adding notes to a meeting ID that doesn't exist
        try{
            diary.addMeetingNotes(4824,"Good luck trying to find this meeting!");
        } catch (IllegalArgumentException ex){
            System.out.println("TEST PASSED: ERROR - the meeting does not exist");
//  	    ex.printStackTrace();
        } catch (IllegalStateException ex){
            System.out.println("ERROR - the meeting is set for a date in the future");
//  	    ex.printStackTrace();
        } catch (NullPointerException ex){
            System.out.println("ERROR - the notes you tried to add are null!");
//  	    ex.printStackTrace();
        }
        //try adding null notes to a meeting
        try{
            diary.addMeetingNotes(pastMeetIds.get(0),null);
        } catch (IllegalArgumentException ex){
            System.out.println("ERROR - the meeting does not exist");
//  	    ex.printStackTrace();
        } catch (IllegalStateException ex){
            System.out.println("ERROR - the meeting is set for a date in the future");
//  	    ex.printStackTrace();
        } catch (NullPointerException ex){
            System.out.println("TEST PASSED: ERROR - the notes you tried to add are null!");
//  	    ex.printStackTrace();
        }
    }
    @Test
    public void testsAddNewContact(){
        //test with valid name & notes
        try{
            diary.addNewContact("Andrew Ho","Thats me!");
        } catch (NullPointerException ex){
            System.out.println("ERROR - one of the name or notes fields are null!");
        }
        Contact findContact = diary.getSingleContact("Andrew Ho");
        strExpected = "Andrew Ho";
        strOutput = findContact.getName();
        assertEquals(strExpected,strOutput);
        //test with null name
        try{
            diary.addNewContact(null,"who dis?");
        } catch (NullPointerException ex){
            System.out.println("TEST PASSED: Error - the name field WAS null!");
        }
        //test with null notes
        try{
            diary.addNewContact("Juan Love",null);
        } catch (NullPointerException ex){
            System.out.println("TEST PASSED: Error - the notes field WAS null!");
        }
    }
    @Test
    public void testsGetContacts(){
        Set<Contact> setOutput = null;
        try {
            setOutput = diary.getContacts("Travis Wallach");
        } catch (NullPointerException ex){
            System.out.println("ERROR - the name parameter was null");
        }
        //test that the method returns a non-empty set
        assertFalse(setOutput.isEmpty());
        //test that method outputs the expected contact ID
        if(!setOutput.isEmpty()){
            Contact[] ctArray = setOutput.toArray(new Contact[setOutput.size()]);
            output = ctArray[0].getId();
            expected = diary.getSingleContact("Travis Wallach").getId();
            assertEquals(expected,output);
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
        diary.addNewContact("Tondi Busse","DJ Baby Mumma Trouble");
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



















































































