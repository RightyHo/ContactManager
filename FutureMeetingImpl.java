/**
 * A meeting to be held in the future
 */
public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting {
    // No methods here, this is just a naming interface
    // (i.e. only necessary for type checking and/or downcasting)
    public FutureMeetingImpl(int meetingId,Calendar scheduledDate,Set<Contact> participants,String meetingMinutes){
        super(meetingId,scheduledDate,participants);
    }
    public void printHello(){
        System.out.println("Hello this is a dummy method for Future Meeting class");
    }
}