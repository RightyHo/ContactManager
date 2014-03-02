import java.io.Serializable;
import java.util.List;

public class Schedule implements Serializable {
    private List<Meeting> meetingSchedule;
    private List<Contact> contactList;
    
    public Schedule(List<Meeting> meetingSchedule,List<Contact> contactList){
        this.meetingSchedule = meetingSchedule;
        this.contactList = contactList;
    }
    public List<Meeting> getMeetingSchedule(){
        return meetingSchedule;
    }
    public List<Contact> getContactList(){
        return contactList;
    }
    public void setMeetingSchedule(List<Meeting> meetingSchedule){
        this.meetingSchedule = meetingSchedule;
    }
    public void setContactList(List<Contact> contactList){
        this.contactList = contactList;
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