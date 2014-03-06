import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

/**
 * A class to manage your contacts and meetings.
 * Implements the ContactManager interface using an ArrayList
 */
public class ContactManagerImpl implements ContactManager {
    private List<Meeting> meetingSchedule;
    private Set<Contact> contactSet;
    private List<Contact> contactList;
    private final String FILENAME = "Contacts.txt";
    
    /**
     * Class constructor initialises the meetingSchedule List, contactSet & contactList
     * and reads from file to pull in historic data
     */
    public ContactManagerImpl(){
        meetingSchedule = new ArrayList<Meeting>();
        contactSet = new HashSet<Contact>();
        contactList = new ArrayList<Contact>();
        readFile();
    }
    /**
     * Add a new meeting to be held in the future.
     *
     * @param contacts a list of contacts that will participate in the meeting
     * @param date the date on which the meeting will take place
     * @return the ID for the meeting
     * @throws IllegalArgumentException if the meeting is set for a time in the past,
     * or if any contact is unknown / non-existent
     */
    public int addFutureMeeting(Set<Contact> contacts, Calendar date){
        Calendar todaysDate = Calendar.getInstance();
        if(date.before(todaysDate)){
            throw new IllegalArgumentException();
        }
        if(!(contactSet.containsAll(contacts))){
            throw new IllegalArgumentException();
        }
        int newID = (int)(Math.random()*10000);
        
        Meeting upComingMeeting = new FutureMeetingImpl(newID,date,contacts);
        //find the location to insert the meeting in the list - in date order
        for(int i=0;i<meetingSchedule.size();i++){
            Meeting checkMeeting = meetingSchedule.get(i);
            Calendar checkDate = checkMeeting.getDate();
            if(checkDate.after(date)){
                meetingSchedule.add(i,upComingMeeting);
                return upComingMeeting.getId();
            }
        }
        //Append the specified meeting to the end of this list if the new meeting date is after all existing meetings in list
        meetingSchedule.add(upComingMeeting);
        return upComingMeeting.getId();
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
                    try{
                        if(auxMeeting instanceof PastMeeting){
                            result = (PastMeeting) auxMeeting;
                            return result;
                        }
                    } catch (ClassCastException ex){
                        System.out.println("ERROR - auxMeeting MUST BE a PastMeeting");
                    }
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
                    try{
                        if(auxMeeting instanceof FutureMeeting){
                            result = (FutureMeeting) auxMeeting;
                            return result;
                        }
                    } catch (ClassCastException ex){
                        System.out.println("ERROR - auxMeeting MUST BE a FutureMeeting");
                    }
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
        Meeting result = null;
        Calendar todaysDate = Calendar.getInstance();
        for(int i=0;i<meetingSchedule.size();i++){
            Meeting auxMeeting = meetingSchedule.get(i);
            int auxId = auxMeeting.getId();
            if(auxId == id){
                result = auxMeeting;
                return result;
            }
        }
        return result;
    }
    /**
     * Returns the list of future meetings scheduled with this contact.
     *
     * If there are none, the returned list will be empty. Otherwise,
     * the list will be chronologically sorted and will not contain any
     * duplicates.
     *
     * @param contact one of the users contacts
     * @return the list of future meeting(s) scheduled with this contact (maybe empty).
     * @throws IllegalArgumentException if the contact does not exist
     */
    public List<Meeting> getFutureMeetingList(Contact contact){
        List<Meeting> result = new ArrayList<Meeting>();
        if(!(contactSet.contains(contact))){
            throw new IllegalArgumentException();
        } else {
            for(int i=0;i<meetingSchedule.size();i++){
                Meeting auxMeeting = meetingSchedule.get(i);
                if(auxMeeting.getContacts().contains(contact)){
                    if(!(result.contains(auxMeeting))){
                        result.add(auxMeeting);
                    }
                }
            }
            return result;
        }
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
        List<Meeting> result = new ArrayList<Meeting>();
        for(int i=0;i<meetingSchedule.size();i++){
            Meeting auxMeeting = meetingSchedule.get(i);
            if(auxMeeting.getDate().equals(date)){
                if(!(result.contains(auxMeeting))){
                    result.add(auxMeeting);
                }
            }
        }
        return result;
    }
    /**
     * Returns the list of past meetings in which this contact has participated.
     *
     * If there are none, the returned list will be empty. Otherwise,
     * the list will be chronologically sorted and will not contain any
     * duplicates.
     *
     * @param contact one of the users contacts
     * @return Returns the list of past meetings in which this contact has participated (maybe empty).
     * @throws IllegalArgumentException if the contact does not exist
     */
    public List<PastMeeting> getPastMeetingList(Contact contact){
        List<PastMeeting> result = new ArrayList<PastMeeting>();
        if(!(contactSet.contains(contact))){
            throw new IllegalArgumentException();
        } else {
            for(int i=0;i<meetingSchedule.size();i++){
                Meeting auxMeeting = meetingSchedule.get(i);
                if(auxMeeting.getContacts().contains(contact)){
                    if(!(result.contains(auxMeeting))){
                        try{
                            if(auxMeeting instanceof PastMeeting){
                                result.add((PastMeeting) auxMeeting);
                            }
                        } catch (ClassCastException ex){
                            System.out.println("ERROR - auxMeeting MUST BE a PastMeeting");
                        }
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
        Calendar todaysDate = Calendar.getInstance();
        if(date.after(todaysDate)){
            throw new IllegalArgumentException();
        }
        if(!(contactSet.containsAll(contacts))){
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
            Calendar checkDate = checkMeeting.getDate();
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
        Meeting focusMeeting = getMeeting(id);
        Calendar todaysDate = Calendar.getInstance();
        if(focusMeeting == null){
            throw new IllegalArgumentException();
        } else if(focusMeeting.getDate().after(todaysDate)){
            throw new IllegalStateException();
        } else if(text == null){
            throw new NullPointerException();
        } else {
            try{
                int outputId = convertToPastMeeting(focusMeeting,text);
                System.out.println("Meeting Notes were added for Meeting ID: " + String.valueOf(outputId));
            } catch (IllegalStateException ex){
                System.out.println("ERROR - meeting has yet to occur");
            } catch (NullPointerException ex){
                System.out.println("ERROR - meeting or notes were sent with a null value");
            }
        }
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
            contactList.add(newContact);
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
        boolean validId;
        Set<Contact> result = new HashSet<Contact>();
        for(int id : ids){
            validId = false;
            for(Contact c : contactSet){
                if(c.getId() == id){
                    result.add(c);
                    validId = true;
                }
            }
            if(!validId){
                throw new IllegalArgumentException();
            }
        }
        return result;
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
        ObjectOutputStream oos = null;
        FileOutputStream fout = null;
        try{
            fout = new FileOutputStream(FILENAME, true);
            oos = new ObjectOutputStream(fout);
            oos.writeObject(meetingSchedule);
            oos.writeObject(contactSet);
            oos.writeObject(contactList);
            oos.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if(oos  != null){
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * Returns a list of all past meeting ID's
     *
     * @return a list of all past meeting ID's
     */
    public List<Integer> getPastMeetingIdList(){
        List<Integer> result = new ArrayList<Integer>();
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
        Calendar todaysDate = Calendar.getInstance();
        for(int i=0;i<meetingSchedule.size();i++){
            if(meetingSchedule.get(i).getDate().after(todaysDate)){
                result.add(meetingSchedule.get(i).getId());
            }
        }
        return result;
    }
    /**
     * Returns a single contact that correspond to the ID.
     *
     * @param id number of contact
     * @return a single contact that corresponds to the ID.
     * @throws IllegalArgumentException if the ID does not correspond to a real contact
     */
    public Contact getSingleContact(int id){
        for(int i=0;i<contactList.size();i++){
            if(contactList.get(i).getId()==id){
                return contactList.get(i);
            }
        }
        throw new IllegalArgumentException();
    }
    /**
     * Returns a single contact that corresponds to the contact name string
     *
     * @param name of contact to search for
     * @return a single contact that corresponds to the contact name
     * @throws IllegalArgumentException if the ID does not correspond to a real contact
     */
    public Contact getSingleContact(String name){
        for(int i=0;i<contactList.size();i++){
            Contact auxContact = contactList.get(i);
            if(auxContact.getName().equals(name)){
                return auxContact;
            }
        }
        throw new IllegalArgumentException();
    }
    /**
     * Converts a future meeting to a past meeting by copying the future meeting details and adding
     * meeting notes to a new past meeting...and removing the old future meeting from the contact manager
     *
     * @return int meeting ID of the meeting that was converted
     * @param futMeet a meeting that was originally set up as a future meeting but which has now occured
     * @param text messages to be added about the meeting.
     * @throws NullPointerException if futMeet or text is null
     * @throws IllegalStateException if the meeting has yet to occur
     */
    public int convertToPastMeeting(Meeting futMeet,String text){
        Calendar todaysDate = Calendar.getInstance();
        if(futMeet.getDate().after(todaysDate)){
            throw new IllegalStateException();
        }
        if(futMeet == null || text == null){
            throw new NullPointerException();
        }
        int focusID = futMeet.getId();
        Calendar date = futMeet.getDate();
        Set<Contact> contacts = futMeet.getContacts();
        Meeting oldMeeting = new PastMeetingImpl(focusID,date,contacts,text);
        //remove futMeet from the contact manager meeting schedule
        meetingSchedule.remove(futMeet);
        //find the location to insert the meeting in the list - in date order
        for(int i=0;i<meetingSchedule.size();i++){
            Meeting focusMeeting = meetingSchedule.get(i);
            Calendar focusDate = focusMeeting.getDate();
            if(focusDate.after(date)){
                meetingSchedule.add(i,oldMeeting);
                return focusID;
            }
        }
        //Append the specified meeting to the end of this list if the new meeting date is after all existing meetings in list
        meetingSchedule.add(oldMeeting);
        return focusID;
    }
    /**
     * Reads the saved meeting schedule from the file saved on disk
     * @return the saved list of meetings, contact set & contact list
     * @throws FileNotFoundException if the correct file is not found
     */
    public void readFile(){
        //read Contact.txt file and turn the read file back into objects
        ObjectInputStream oin = null;
        try {
            oin = new ObjectInputStream(
                                        new FileInputStream(FILENAME));
            meetingSchedule = (List<Meeting>) oin.readObject();
            contactSet = (Set<Contact>) oin.readObject();
            contactList = (List<Contact>) oin.readObject();
        } catch (Exception ex){
            //no file for the first time
            System.out.println("The first time this class is run there will not be a file to read!");
        } finally {
            if(oin != null){
                try {
                    oin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } 
        }
    }
}