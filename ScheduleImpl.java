import java.io.Serializable;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ScheduleImpl implements Schedule,Serializable {
    private List<Meeting> meetingSchedule;
    private Set<Contact> contactSet;
    private List<Contact> contactList;
    
    public ScheduleImpl(){
        meetingSchedule = new ArrayList<Meeting>();
        contactSet = new HashSet<Contact>();
        contactList = new ArrayList<Contact>();
    }
    
    public List<Meeting> getMeetingSchedule(){
        return meetingSchedule;
    }
    public List<Contact> getContactList(){
        return contactList;
    }
    public Set<Contact> getContactSet(){
        return contactSet;
    }
    public void setMeetingSchedule(List<Meeting> meetingSchedule){
        this.meetingSchedule = meetingSchedule;
    }
    public void setContactList(List<Contact> contactList){
        this.contactList = contactList;
    }
    public void setContactSet(Set<Contact> contactSet){
        this.contactSet = contactSet;
    }
    @Override
    public String toString(){
        String result = "";
        for(int i=0;i<meetingSchedule.size();i++){
            result = result + "Meeting ID " + i + ": " + String.valueOf(meetingSchedule.get(i).getId()) + ", ";
            result = result + "Meeting Contacts: " + meetingSchedule.get(i).getContacts() + ", ";
        }
        return result;
    }
}