import java.util.Calendar;
import java.util.Set;
import java.io.Serializable;

/**
* A class to represent meetings
*
* Meetings have unique IDs, scheduled date and a list of participating contacts
*/
public class MeetingImpl implements Meeting, Serializable {
	private int meetingId;
	private Calendar scheduledDate;
	private Set<Contact> participants;

/**
* Constructor for MeetingImpl class
*/
    public MeetingImpl(){}
    
	public MeetingImpl(int meetingId,Calendar scheduledDate,Set<Contact> participants){
		this.meetingId = meetingId;
		this.scheduledDate = scheduledDate;
		this.participants = participants;
	}
/**
* Returns the id of the meeting.
*
* @return the id of the meeting.
*/
    public int getId(){
        return meetingId;
    }
/**
* Return the date of the meeting.
*
* @return the date of the meeting.
*/
    public Calendar getDate(){
        return scheduledDate;
    }
/**
* Return the details of people that attended the meeting.
*
* The list contains a minimum of one contact (if there were
* just two people: the user and the contact) and may contain an
* arbitraty number of them.
*
* @return the details of people that attended the meeting.
*/
    public Set<Contact> getContacts(){
        return participants;            //***haven't added any code to guard against empty participants set***
    }
}