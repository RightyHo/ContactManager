import java.util.Set;
import java.util.List;

public interface Schedule {

List<Meeting> getMeetingSchedule();

List<Contact> getContactList();

Set<Contact> getContactSet();

void setMeetingSchedule(List<Meeting> meetingSchedule);

void setContactList(List<Contact> contactList);

void setContactSet(Set<Contact> contactSet);

String toString();
}