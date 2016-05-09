/**
 * 
 */
package edu.ncsu.csc216.tracker.requirement;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.tracker.model.RequirementsList;
import edu.ncsu.csc216.tracker.requirement.enums.CommandValue;
import edu.ncsu.csc216.tracker.requirement.enums.Rejection;

/**Junit test cases for the requirements class
 * @author Andrew Northrup if error during setup
 *
 */
public class RequirementTest {
	/**Private instance variable for a requirement */
	private Requirement requir;
	/**Private instance variable command to a requirement */
	private Command test;
	/**Private instance variable for a command value to a requirement */
	private CommandValue cv;
	/**Private instance variable for summary of a requirement */
	private String sum;
	/**Private instance variable for acceptance test ID of a requirement */
	private String acceptID;
	/**Private instance variable for priority of a requirement */
	private int prior;
	/**Private instance variable for estimate time for a requirement */
	private String estimate;
	/**Private instance variable for developer of a requirement */
	private String devID;
	/**Private instance variable for a rejection reason of a requirement */
	private Rejection r;
	
	
	
	/**Sets up a new requirement r for use in class
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
		Requirement.setCounter(0);
		requir = new Requirement(sum, acceptID);
		//Not sure this is correct, but ignore for now while testing states
		
	}

	/**Test method for getting requirement as a string.
	 */
	@Test
	public void testRequirementStringString() {
		assertEquals(requir.getSummary(), "This is a test requirement string");
	}

	/**
	 * Test method for getting requirement ID.
	 */
	@Test
	public void testGetRequirementId() {
		assertEquals(requir.getRequirementId(), 0);
	}

	/**
	 * Test method for getting the state of the requirement.
	 */
	@Test
	public void testGetState() {
		assertEquals("Submitted", requir.getState().getStateName());
	}

	/**
	 * Test method for getting the priority of the requirement.
	 */
	@Test
	public void testGetPriority() {
		requir.update(test);
		assertEquals(2, requir.getPriority());
	}

	/**
	 * Test method for getting the estimate of the requirement.
	 */
	@Test
	public void testGetEstimate() {
		requir.update(test);
		assertEquals(requir.getEstimate(), "Two hours test");
	}

	/**
	 * Test method for getting a rejection reason.
	 */
	@Test
	public void testGetRejectionReason() {
		test = new Command(CommandValue.REJECT, sum, acceptID, prior, estimate, devID, r);
		requir.update(test);
		assertEquals(requir.getRejectionReason(), Rejection.DUPLICATE);
	}

	/**
	 * Test method for getting rejection reason as a string.
	 */
	@Test
	public void testGetRejectionReasonString() {
		test = new Command(CommandValue.REJECT, sum, acceptID, prior, estimate, devID, r);
		requir.update(test);
		assertEquals(requir.getRejectionReasonString(), "Duplicate");
	}

	/**
	 * Test method for getting the developer.
	 */
	@Test
	public void testGetDeveloper() {
		test = new Command(CommandValue.ACCEPT, sum, acceptID, prior, estimate, devID, r);
		requir.update(test);
		test = new Command(CommandValue.ASSIGN, sum, acceptID, prior, estimate, devID, r);
		requir.update(test);
		assertEquals(requir.getDeveloper(), "John Doe");
	}

	/**
	 * Test method for getting the requirement summary.
	 */
	@Test
	public void testGetSummary() {
		test = new Command(CommandValue.ACCEPT, sum, acceptID, prior, estimate, devID, r);
		requir.update(test);
		test = new Command(CommandValue.ASSIGN, sum, acceptID, prior, estimate, devID, r);
		requir.update(test);
		assertEquals(requir.getSummary(), sum);
	}

	/**
	 * Test method for getting the acceptance test ID.
	 */
	@Test
	public void testGetAcceptanceTestId() {
		test = new Command(CommandValue.ACCEPT, sum, acceptID, prior, estimate, devID, r);
		requir.update(test);
		test = new Command(CommandValue.ASSIGN, sum, acceptID, prior, estimate, devID, r);
		requir.update(test);
		assertEquals(requir.getAcceptanceTestId(), acceptID);
	}

	/**
	 * Test method for setting the acceptance test ID.
	 */
	@Test
	public void testSetAcceptanceTestId() {
		requir.setAcceptanceTestId("new AcceptID");
		assertEquals(requir.getAcceptanceTestId(), "new AcceptID");
	}

	/**
	 * Test method for setting the developer.
	 */
	@Test
	public void testSetDeveloper() {
		requir.setDeveloper("new John Doe");
		assertEquals(requir.getDeveloper(), "new John Doe");
	}

	/**
	 * Test method for updating each state using a command.
	 */
	@Test
	public void testBasicFlowUpdate() {
		requir.update(test);
		assertEquals(requir.getState().getStateName(), "Accepted");
		//Test command assign requirement
		Command testAssign = new Command(CommandValue.ASSIGN, sum, acceptID, prior, estimate, devID, r);
		requir.update(testAssign);
		assertEquals(requir.getState().getStateName(), "Working");
		//Test command complete requirement
		Command testComplete = new Command(CommandValue.COMPLETE, sum, acceptID, prior, estimate, devID, r);
		requir.update(testComplete);
		assertEquals(requir.getState().getStateName(), "Completed");
		//Test command pass requirement
		Command testPass = new Command(CommandValue.PASS, sum, acceptID, prior, estimate, devID, r);
		requir.update(testPass);
		assertEquals(requir.getState().getStateName(), "Verified");
	}
	/**
	 * Test method for command to reject from different states and revising.
	 */
	@Test
	public void testRejectsFromStates() {
		requir.update(test);
		assertEquals(requir.getState().getStateName(), "Accepted");
		//Test command reject from accepted state
		Command testAcceptToFail = new Command(CommandValue.REJECT, sum, acceptID, prior, estimate, devID, Rejection.INFEASIBLE);
		requir.update(testAcceptToFail);
		assertNull(requir.getDeveloper());
		//Test command reject from Working State
		test = new Command(CommandValue.REVISE, sum, acceptID, prior, estimate, devID, r);
		requir.update(test);
		assertEquals(requir.getState().getStateName(), "Submitted");
		test = new Command(CommandValue.ACCEPT, sum, acceptID, prior, estimate, devID, r);
		requir.update(test);
		test = new Command(CommandValue.ASSIGN, sum, acceptID, prior, estimate, devID, r);
		requir.update(test);
		test = new Command(CommandValue.REJECT, sum, acceptID, prior, estimate, devID, Rejection.TOO_LARGE);
		requir.update(test);
		assertEquals(requir.getRejectionReason(), Rejection.TOO_LARGE);
		
	}
	
	/**
	 * Test method for updating at state with a command that is not supported
	 * for illegal arguments passed to state that it shouldn't
	 */
	@Test
	public void testIllegalCommandUpdate() {
		Command failTest = new Command(CommandValue.ASSIGN, sum, acceptID, prior, estimate, devID, r);
		try{
			requir.update(failTest);
			fail();
		} catch(Exception e){
		assertEquals(requir.getState().getStateName(), "Submitted");
		}
		failTest = new Command(CommandValue.COMPLETE, sum, acceptID, prior, estimate, devID, r);
		try{
			requir.update(failTest);
			fail();
		} catch(Exception e){
		assertEquals(requir.getState().getStateName(), "Submitted");
		}
		requir.update(test);
		failTest = new Command(CommandValue.FAIL, sum, acceptID, prior, estimate, devID, r);
		try{
			requir.update(failTest);
			fail();
		} catch(Exception e){
		assertEquals(requir.getState().getStateName(), "Accepted");
		}
		failTest = new Command(CommandValue.COMPLETE, sum, acceptID, prior, estimate, devID, r);
		try{
			requir.update(failTest);
			fail();
		} catch(Exception e){
		assertEquals(requir.getState().getStateName(), "Accepted");
		}
		failTest = new Command(CommandValue.ACCEPT, sum, acceptID, prior, estimate, devID, r);
		try{
			requir.update(failTest);
			fail();
		} catch(Exception e){
		assertEquals(requir.getState().getStateName(), "Accepted");
		}
		test = new Command(CommandValue.ASSIGN, sum, acceptID, prior, estimate, devID, r);
		requir.update(test);
		failTest = new Command(CommandValue.ACCEPT, sum, acceptID, prior, estimate, devID, r);
		try{
			requir.update(failTest);
			fail();
		} catch(Exception e){
		assertEquals(requir.getState().getStateName(), "Working");
		}
		failTest = new Command(CommandValue.ASSIGN, sum, acceptID, prior, estimate, devID, r);
		try{
			requir.update(failTest);
			fail();
		} catch(Exception e){
		assertEquals(requir.getState().getStateName(), "Working");
		}
		//Send a completed test back to working
		test = new Command(CommandValue.COMPLETE, sum, acceptID, prior, estimate, devID, r);
		requir.update(test);
		test = new Command(CommandValue.FAIL, sum, acceptID, prior, estimate, devID, r);
		requir.update(test);
		assertEquals(requir.getState().getStateName(), "Working");
		test = new Command(CommandValue.COMPLETE, sum, acceptID, prior, estimate, devID, r);
		requir.update(test);
		assertEquals(requir.getState().getStateName(), "Completed");
		//Try invalid command on completed state
		failTest = new Command(CommandValue.ACCEPT, sum, acceptID, prior, estimate, devID, r);
		try{
			requir.update(failTest);
			fail();
		} catch(Exception e){
		assertEquals(requir.getState().getStateName(), "Completed");
		}
		test = new Command(CommandValue.PASS, sum, acceptID, prior, estimate, devID, r);
		requir.update(test);
		assertEquals(requir.getState().getStateName(), "Verified");
		//Pass invalid commands to Verified
		failTest = new Command(CommandValue.ACCEPT, sum, acceptID, prior, estimate, devID, r);
		try{
			requir.update(failTest);
			fail();
		} catch(Exception e){
		assertEquals(requir.getState().getStateName(), "Verified");
		}
		failTest = new Command(CommandValue.PASS, sum, acceptID, prior, estimate, devID, r);
		try{
			requir.update(failTest);
			fail();
		} catch(Exception e){
		assertEquals(requir.getState().getStateName(), "Verified");
		}
		//trying to match teaching model 
		failTest = new Command(CommandValue.FAIL, null, null, 0, null, null, null);
		try{
			requir.update(failTest);
			fail();
		} catch(Exception e){
		assertEquals(requir.getState().getStateName(), "Verified");
		}
		failTest = new Command(CommandValue.COMPLETE, sum, acceptID, prior, estimate, devID, r);
		try{
			requir.update(failTest);
			fail();
		} catch(Exception e){
		assertEquals(requir.getState().getStateName(), "Verified");
		}
	}

	/**
	 * Test method for setting the counter.
	 */
	@Test
	public void testSetCounter() {
		RequirementsList rl = new RequirementsList();
		Requirement.setCounter(10);
		rl.addRequirement("idShouldBe10", "TestID for 10");
		assertEquals(rl.getRequirementById(10).getAcceptanceTestId(), "TestID for 10");
		//fail("Not yet implemented");
	}

}
