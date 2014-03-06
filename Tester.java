import java.util.List;
import java.util.Set;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.GregorianCalendar;

public class Tester {
    private ContactManager diary;
    private Contact myContact;
    private int intOutput;
    private int intExpected;
    private List<Integer> pastMeetIds;
    private List<Integer> futMeetIds;
    private Set<Contact> contactsGroup;
    private Calendar meetingDate;
    private Set<Contact> aux;
    private int output;
    private int expected;
    private String strOutput;
    private String strExpected;
    
    public static void main(String[] args){
        Tester t = new Tester();
        t.launch();
    }
    private void launch(){
        myContact = new ContactImpl(984,"Michael Jordan","NBA legend");
        output = 0;
        expected = 0;
        strOutput = "";
        strExpected = "";
        try{
            diary = new ContactManagerImpl();
            pastMeetIds = new ArrayList<Integer>();
            futMeetIds = new ArrayList<Integer>();
            contactsGroup = new HashSet<Contact>();
            aux = new HashSet<Contact>();
            meetingDate = new GregorianCalendar();
            contactsGroup.clear();
            aux.clear();
        } catch (Exception ex){
            ex.printStackTrace();
        }
        output = myContact.getId();
        expected = 984;
        if(intExpected == intOutput){
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED!");
        }
        strOutput = myContact.getName();
        strExpected = "Michael Jordan";
        if(strExpected.equals(strOutput)){
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED!");
        }
        strOutput = myContact.getNotes();
        strExpected = "NBA legend";
        if(strExpected.equals(strOutput)){
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED!");
        }
        myContact.addNotes("Best shooting guard of all time");
        strOutput = myContact.getNotes();
        strExpected = "Best shooting guard of all time";
        if(strExpected.equals(strOutput)){
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED!");
        }
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
        diary.flush();
        diary.readFile();
        //           System.out.println("*****decoded file******* MEETING SCHEDULE: " + diary.getMeetingSchedule);
        //      System.out.println("*****decoded file******* CONTACT SET: " + diary.getContactSet);
        //    System.out.println("*****decoded file******* CONTACT LIST: " + diary.getContactList);
    }
}