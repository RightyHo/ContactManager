import org.junit.*;
import static org.junit.Assert.*;

public class ContactTest {
	private Contact myContact;
	private int intOutput;
	private int intExpected;
	private String strOutput;
	private String strExpected;
	
	@Before
	public void buildUp(){
        myContact = new ContactImpl(984,"Michael Jordan","NBA legend");
		intOutput = 0;
		intExpected = 0;
		strOutput = "";
		strExpected = "";
	}

	@Test
	public void testsGetId(){
		intOutput = myContact.getId();
		intExpected = 984;
		assertEquals(intExpected,intOutput);
	}
	@Test
	public void testsGetName(){
		strOutput = myContact.getName();
		strExpected = "Michael Jordan";
		assertEquals(strExpected,strOutput);	
	}
	@Test
	public void testsGetNotes(){
		strOutput = myContact.getNotes();
		strExpected = "NBA legend";
		assertEquals(strExpected,strOutput);	
	}
	@Test
	public void testsAddNotes(){
		myContact.addNotes("Best shooting guard of all time");
		strOutput = myContact.getNotes();
		strExpected = "Best shooting guard of all time";
		assertEquals(strExpected,strOutput);	
	}
}