/**
* A contact is a person we are making business with or may do in the future. 
*
* Contacts have an ID (unique), a name (probably unique, but maybe
* not), and notes that the user may want to save about them.
*/
public class AndrewsContact implements Contact { 
	private int contactId;
	private String contactName;
	private String contactNotes;
	
/**
* Constructor for AndrewsContact class
*/
	public AndrewsContact(int contactId,String contactName,String contactNotes){
		this.contactId = contactId;
		this.contactName = contactName;
		this.contactNotes = contactNotes;
	}
/**
* Returns the ID of the contact. 
*
* @return the ID of the contact. 
*/
int getId();
/**
* Returns the name of the contact. 
*
* @return the name of the contact. 
*/
String getName();
/**
* Returns our notes about the contact, if any. 
*
* If we have not written anything about the contact, the empty
* string is returned. 
*
* @return a string with notes about the contact, maybe empty. 
*/
String getNotes();
/**
* Add notes about the contact. 
*
* @param note the notes to be added 
*/
void addNotes(String note); 
}