import java.util.Calendar;
import java.util.Set;
import java.io.Serializable;
/**
 * A meeting that was held in the past.
 *
 * It includes your notes about what happened and what was agreed.
 */
public class PastMeetingImpl extends MeetingImpl implements PastMeeting,Serializable {
    private String meetingMinutes;

    public PastMeetingImpl(int meetingId,Calendar scheduledDate,Set<Contact> participants,String meetingMinutes){
        super(meetingId,scheduledDate,participants);
        this.meetingMinutes = meetingMinutes;
    }
    /**
     * Returns the notes from the meeting.
     *
     * If there are no notes, the empty string is returned.
     *
     * @return the notes from the meeting.
     */
    public String getNotes(){
        return meetingMinutes;
    }
    /**
     * Sets the notes from the meeting.
     *
     * @param meetingMinutes notes from the meeting to be added
     */
    public void setNotes(String meetingMinutes){
        this.meetingMinutes = meetingMinutes;
    }
}