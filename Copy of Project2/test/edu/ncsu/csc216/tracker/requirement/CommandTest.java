/**
 * 
 */
package edu.ncsu.csc216.tracker.requirement;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.tracker.requirement.enums.CommandValue;
import edu.ncsu.csc216.tracker.requirement.enums.Rejection;

/**Tests the command class
 * @author Andrew Northrup
 *
 */
public class CommandTest {
	/**Private instance variable for the test command*/
	private Command test;
	/**Private instance variable for the command value of the test command*/
	private CommandValue cv;
	/**Private instance variable for summary of the test command*/
	private String sum;
	/**Private instance variable for the acceptance ID for the test command*/
	private String acceptID;
	/**Private instance variable for priority of the test command*/
	private int prior;
	/**Private instance variable for the estimate of the test command*/
	private String estimate;
	/**Private instance variable for developer of the test command*/
	private String devID;
	/**Private instance variable for the rejection reason of the test command*/
	private Rejection r;
	
	/**Sets up the command for testing
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		cv = CommandValue.ACCEPT;
		sum = "This is a test requirement string";
		acceptID = "TestAcceptanceID";
		prior = 2;
		estimate = "Two hours test";
		devID = "John Doe";
		r = Rejection.DUPLICATE;
		test = new Command(cv, sum, acceptID, prior, estimate, devID, r);
	}

	/**
	 * Test method for Command constructor
	 */
	@Test
	public void testCommand() {
		assertEquals(CommandValue.ACCEPT, cv);
		Command testFail = null;
		//try and create command with value as null
		try{
			testFail = new Command(null, sum, acceptID, prior, estimate, devID, r);
			fail();
		} catch (Exception e){
			assertNull(testFail);
		}
		//try and create command with value as accept and null est
		try{
			testFail = new Command(cv, sum, acceptID, prior, null, devID, r);
			fail();
		} catch (Exception e){
			assertNull(testFail);
		}
		//try and create command with value as accept and empty string estimate
		try{
			testFail = new Command(cv, sum, acceptID, prior, "", devID, r);
			fail();
		} catch (Exception e){
			assertNull(testFail);
		}
		//try and create command with value as accept and priority -1
		try{
			testFail = new Command(cv, sum, acceptID, -1, estimate, devID, r);
			fail();
		} catch (Exception e){
			assertNull(testFail);
		}
		//try and create command with value as reject and rejection is null
		try{
			testFail = new Command(CommandValue.REJECT, sum, acceptID, prior, estimate, devID, null);
			fail();
		} catch (Exception e){
			assertNull(testFail);
		}
		//try and create command with value as assign and devID is null
		try{
			testFail = new Command(CommandValue.ASSIGN, sum, acceptID, prior, estimate, "", r);
			fail();
		} catch (Exception e){
			assertNull(testFail);
		}
		//try and create command with value as assign and devID is empty
		try{
			testFail = new Command(CommandValue.ASSIGN, sum, acceptID, prior, estimate, null, r);
			fail();
		} catch (Exception e){
			assertNull(testFail);
		}
		//try and create command with value as revise and summary is null
		try{
			testFail = new Command(CommandValue.REVISE, null, acceptID, prior, estimate, devID, r);
			fail();
		} catch (Exception e){
			assertNull(testFail);
		}
		//try and create command with value as revise and summary is empty
		try{
			testFail = new Command(CommandValue.REVISE, "", acceptID, prior, estimate, devID, r);
			fail();
		} catch (Exception e){
			assertNull(testFail);
		}
		//try and create command with value as revise and acceptance test id is null
		try{
			testFail = new Command(CommandValue.REVISE, sum, null, prior, estimate, devID, r);
			fail();
		} catch (Exception e){
			assertNull(testFail);
		}
		//try and create command with value as revise and acceptance test id is empty
		try{
			testFail = new Command(CommandValue.REVISE, sum, "", prior, estimate, devID, r);
			fail();
		} catch (Exception e){
			assertNull(testFail);
		}
	}

	/**
	 * Test method for get summary of command.
	 */
	@Test
	public void testGetSummary() {
		assertEquals(test.getSummary(), "This is a test requirement string");
	}

	/**
	 * Test method for get acceptance test ID of a command
	 */
	@Test
	public void testGetAcceptanceTestId() {
		assertEquals(test.getAcceptanceTestId(), "TestAcceptanceID");
	}

	/**
	 * Test method for get command.
	 */
	@Test
	public void testGetCommand() {
		assertEquals(CommandValue.ACCEPT, test.getCommand());
	}

	/**
	 * Test method for get estimate of command.
	 */
	@Test
	public void testGetEstimate() {
		assertEquals(test.getEstimate(), "Two hours test");
	}

	/**
	 * Test method for get priority of command.
	 */
	@Test
	public void testGetPriority() {
		assertEquals(test.getPriority(), 2);
	}

	/**
	 * Test method for get the developer's ID for the command.
	 */
	@Test
	public void testGetDeveloperId() {
		assertEquals(test.getDeveloperId(), "John Doe");
	}

	/**
	 * Test method for get the command's rejection reason.
	 */
	@Test
	public void testGetRejectionReason() {
		assertEquals(test.getRejectionReason(), Rejection.DUPLICATE);
	}

}
