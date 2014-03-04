import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 * A meeting to be held in the future
 */
public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting, Serializable {
    // No methods here, this is just a naming interface
    // (i.e. only necessary for type checking and/or downcasting)
    public FutureMeetingImpl(){
        super();
    }
    public FutureMeetingImpl(int meetingId,Calendar scheduledDate,Set<Contact> participants){
        super(meetingId,scheduledDate,participants);
    }
}