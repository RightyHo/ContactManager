import java.util.Calendar; 
import java.util.List; 
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
/**
* A class to manage your contacts and meetings. 
* Implements the ContactManager interface using an ArrayList
*/
public class ContactManagerImpl implements ContactManager {
	private List<Meeting> meetingSchedule;
    private Set<Contact> contactSet;
/**
* Class constructor initialises the meetingSchedule List using an ArrayList
* The other alternative was to use a LinkedList structure
*/	
	public ContactManagerImpl(){
		meetingSchedule = new ArrayList<Meeting>();
        contactSet = new HashSet<Contact>();
	}
/**
* Add a new meeting to be held in the future. 
*
* @param contacts a list of contacts that will participate in the meeting
* @param date the date on which the meeting will take place
* @return the ID for the meeting
* @throws IllegalArgumentException if the meeting is set for a time in the past,
*	of if any contact is unknown / non-existent
*/
    public int addFutureMeeting(Set<Contact> contacts, Calendar date){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar todaysDate = Calendar.getInstance();
        System.out.println("The todays date is: " + dateFormat.format(todaysDate.getTime()));
        if(date.before(todaysDate)){
            throw new IllegalArgumentException();
        }
           if(!contactSet.containsAll(contacts)){
            throw new IllegalArgumentException();
        }
        int newID = (int)(Math.random()*10000);
        Meeting upComingMeeting = new MeetingImpl(newID,date,contacts);
        //find the location to insert the meeting in the list - in date order
        for(int i=0;i<meetingSchedule.size();i++){
            Meeting checkMeeting = meetingSchedule.get(i);
            Calendar checkDate = checkMeeting.getDate();  //could put these both on one line?
            if(checkDate.after(date)){
                meetingSchedule.add(i,upComingMeeting);
                return upComingMeeting.getId();		//Could just return newID if this is problematic
            }
        }
        //Append the specified meeting to the end of this list if the new meeting date is after all existing meetings in list
        meetingSchedule.add(upComingMeeting);
        return upComingMeeting.getId();		//Could just return newID if this is problematic
    }
              

/**
* Returns the PAST meeting with the requested ID, or null if it there is none. 
*
* @param id the ID for the meeting
* @return the meeting with the requested ID, or null if it there is none.
* @throws IllegalArgumentException if there is a meeting with that ID happening in the future
*/
    public PastMeeting getPastMeeting(int id){
        PastMeeting result = null;
        Calendar todaysDate = Calendar.getInstance();
            for(int i=0;i<meetingSchedule.size();i++){
                Meeting auxMeeting = meetingSchedule.get(i);
                int auxId = auxMeeting.getId();
                if(auxId == id){
                    if(auxMeeting.getDate().after(todaysDate.getTime())){
                        throw new IllegalArgumentException();
                    } else {
                        result = (PastMeeting) auxMeeting;
                        return result;
                    }
                }
            }
            return result;
    }

/**
* Returns the FUTURE meeting with the requested ID, or null if there is none. 
*
* @param id the ID for the meeting
* @return the meeting with the requested ID, or null if it there is none.
* @throws IllegalArgumentException if there is a meeting with that ID happening in the past 
*/
    public FutureMeeting getFutureMeeting(int id){
        FutureMeeting result = null;
        Calendar todaysDate = Calendar.getInstance();
        for(int i=0;i<meetingSchedule.size();i++){
            Meeting auxMeeting = meetingSchedule.get(i);
            int auxId = auxMeeting.getId();
            if(auxId == id){
                if(auxMeeting.getDate().before(todaysDate.getTime())){
                    throw new IllegalArgumentException();
                } else {
                    result = (FutureMeeting) auxMeeting;
                    return result;
                }
            }
        }
        return result;
    }
/**
* Returns the meeting with the requested ID, or null if it there is none.
*
* @param id the ID for the meeting
* @return the meeting with the requested ID, or null if it there is none.
*/
    public Meeting getMeeting(int id){
        return null;
    }
/**
* Returns the list of future meetings scheduled with this contact. 
*
* If there are none, the returned list will be empty. Otherwise,
* the list will be chronologically sorted and will not contain any 
* duplicates.
*
* @param contact one of the user’s contacts
* @return the list of future meeting(s) scheduled with this contact (maybe empty).
* @throws IllegalArgumentException if the contact does not exist 
*/
    public List<Meeting> getFutureMeetingList(Contact contact){
        return null;
    }
/**
* Returns the list of meetings that are scheduled for the specified date
*
* If there are none, the returned list will be empty. Otherwise,
* the list will be chronologically sorted and will not contain any 
* duplicates.
*
* @param date the date
* @return the list of meetings 
*/
    public List<Meeting> getFutureMeetingList(Calendar date){
        return null;
    }
/**
* Returns the list of past meetings in which this contact has participated. 
*
* If there are none, the returned list will be empty. Otherwise,
* the list will be chronologically sorted and will not contain any 
* duplicates.
*
* @param contact one of the user’s contacts
* @return Returns the list of past meetings in which this contact has participated (maybe empty).
* @throws IllegalArgumentException if the contact does not exist
*/
    public List<PastMeeting> getPastMeetingList(Contact contact){
        List<PastMeeting> result = new ArrayList<PastMeeting>();
        if(!contactSet.contains(contact)){
            throw new IllegalArgumentException();
        } else {
            for(int i=0;i<meetingSchedule.size();i++){
                if(meetingSchedule.get(i).getContacts().contains(contact)){
                    if(!result.contains(meetingSchedule.get(i))){
                        result.add((PastMeeting) meetingSchedule.get(i));
                    }
                }
            }
        return result;
        }
    }
/**
* Create a new record for a meeting that took place in the past. 
*
* @param contacts a list of participants
* @param date the date on which the meeting took place
* @param text messages to be added about the meeting.
* @throws IllegalArgumentException if the list of contacts is
* empty, or any of the contacts does not exist
* @throws NullPointerException if any of the arguments is null 
*/
    public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar todaysDate = Calendar.getInstance();
        System.out.println("The todays date is: " + dateFormat.format(todaysDate.getTime()));
        if(date.after(todaysDate)){
            throw new IllegalArgumentException();
        }
        if(!contactSet.containsAll(contacts)){
            throw new IllegalArgumentException();
        }
        if(contacts == null){
            throw new NullPointerException();
        }
        if(date == null){
            throw new NullPointerException();
        }
        if(text == null){
            throw new NullPointerException();
        }
        int newID = (int)(Math.random()*10000);
        Meeting oldMeeting = new PastMeetingImpl(newID,date,contacts,text);
        //find the location to insert the meeting in the list - in date order
        for(int i=0;i<meetingSchedule.size();i++){
            Meeting checkMeeting = meetingSchedule.get(i);
            Calendar checkDate = checkMeeting.getDate();  //could put these both on one line?
            if(checkDate.after(date)){
                meetingSchedule.add(i,oldMeeting);
                return;
            }
        }
        //Append the specified meeting to the end of this list if the new meeting date is after all existing meetings in list
        meetingSchedule.add(oldMeeting);
        return;
    }
/**
* Add notes to a meeting. 
*
* This method is used when a future meeting takes place, and is
* then converted to a past meeting (with notes). 
*
* It can be also used to add notes to a past meeting at a later date. 
*
* @param id the ID of the meeting
* @param text messages to be added about the meeting.
* @throws IllegalArgumentException if the meeting does not exist
* @throws IllegalStateException if the meeting is set for a date in the future
* @throws NullPointerException if the notes are null 
*/
    public void addMeetingNotes(int id, String text){
        //do something
    }
/**
* Create a new contact with the specified name and notes. 
*
* @param name the name of the contact.
* @param notes notes to be added about the contact.
* @throws NullPointerException if the name or the notes are null
*/
    public void addNewContact(String name, String notes){
        if(name == null){
            throw new NullPointerException();
        } else if(notes == null){
            throw new NullPointerException();
        } else {
            //Assign random contact ID number
            int contactId = (int) Math.abs(10000*Math.random());
            Contact newContact = new ContactImpl(contactId,name,notes);
            contactSet.add(newContact);
        }
    }
/**
* Returns a list containing the contacts that correspond to the IDs.
*
* @param ids an arbitrary number of contact IDs
* @return a list containing the contacts that correspond to the IDs.
* @throws IllegalArgumentException if any of the IDs does not correspond to a real contact 
*/
    public Set<Contact> getContacts(int... ids){
        return null;
    }
/**
* Returns a list with the contacts whose name contains that string. 
*
* @param name the string to search for
* @return a list with the contacts whose name contains that string.
* @throws NullPointerException if the parameter is null 
*/
    public Set<Contact> getContacts(String name){
        if(name == null){
            throw new NullPointerException();
        } else {
            Set<Contact> result = new HashSet<Contact>();
            for(Contact c: contactSet){
                if(c.getName().equals(name)){
                    result.add(c);
                }
            }
            return result;
        }
    }
/**
* Save all data to disk. 
*
* This method must be executed when the program is
* closed and when/if the user requests it. 
*/
    public void flush(){
        //do something
    }
/**
* Returns a list of all past meeting ID's
*
* @return a list of all past meeting ID's
*/
    public List<Integer> getPastMeetingIdList(){
        List<Integer> result = new ArrayList<Integer>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar todaysDate = Calendar.getInstance();
        for(int i=0;i<meetingSchedule.size();i++){
            if(meetingSchedule.get(i).getDate().before(todaysDate)){
                result.add(meetingSchedule.get(i).getId());
            }
        }
        return result;
    }
/**
* Returns a list of all future meeting ID's
*
* @return a list of all future meeting ID's
*/
    public List<Integer> getFutureMeetingIdList(){
        List<Integer> result = new ArrayList<Integer>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar todaysDate = Calendar.getInstance();
        for(int i=0;i<meetingSchedule.size();i++){
            if(meetingSchedule.get(i).getDate().after(todaysDate)){
                result.add(meetingSchedule.get(i).getId());
            }
        }
        return result;
    }
}



























