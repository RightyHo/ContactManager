import org.junit.*;
import static org.junit.Assert.*;

public class ContactTest {
	Contact myContact;
	int intOutput;
	int intExpected;
	String strOutput;
	String strExpected;
	
	@Before
	public void buildUp(){
		int intOutput = 0;
		int intExpected = 0;
		String strOutput = "";
		String strExpected = "";
	}

	@Test
	public void testsGetId(){
		myContact = new ContactImpl(984,"Michael Jordan","NBA legend");
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