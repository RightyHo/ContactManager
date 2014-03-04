import java.io.Serializable;
/**
* A contact is a person we are making business with or may do in the future.
*
* Contacts have an ID (unique), a name (probably unique, but maybe
* not), and notes that the user may want to save about them.
*/
public class ContactImpl implements Contact, Serializable {
	private int contactId;
	private String contactName;
	private String contactNotes;

/**
* Constructor for ContactImpl class
*/
    public ContactImpl(){}
    
	public ContactImpl(int contactId,String contactName,String contactNotes){
		this.contactId = contactId;
		this.contactName = contactName;
		this.contactNotes = contactNotes;
	}
/**
* Returns the ID of the contact.
*
* @return the ID of the contact.
*/
    public int getId(){
        return contactId;
    }
/**
* Returns the name of the contact.
*
* @return the name of the contact.
*/
    public String getName(){
        return contactName;
    }
/**
* Returns our notes about the contact, if any.
*
* If we have not written anything about the contact, the empty
* string is returned.
*
* @return a string with notes about the contact, maybe empty.
*/
    public String getNotes(){
        return contactNotes;            //***haven't added any code to cater for empty string yet***
    }
/**
* Add notes about the contact.
*
* @param note the notes to be added
*/
    public void addNotes(String note){
        contactNotes = note;
    }
}