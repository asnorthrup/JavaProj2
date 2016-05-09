package edu.ncsu.csc216.tracker.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.tracker.requirement.Command;
import edu.ncsu.csc216.tracker.requirement.enums.CommandValue;


/**JUnit test for Requirements Tracker Model
 * @author Andrew Northrup
 *
 */
public class RequirementsTrackerModelTest {
/*	req1.xml - Valid req file
	req2.xml - Negative id
	req3.xml - Missing state
	req4.xml - Missing summary
	req5.xml - Missing test
	req6.xml - File doesn't exist
	req7.xml - Accepted with no estimate
	req8.xml - Working with no estimate
	req9.xml - Completed with no estimate
	req10.xml - Verified with no estimate
	req11.xml - Submitted with non-zero priority
	req12.xml - Working with no developer
	req13.xml - Completed with no developer
	req14.xml - Verified with no developer
	req15.xml - Rejected with no rejection reason
	req16.xml - Accepted with no priority
	req17.xml - Working with no priority
	req18.xml - Completed with no priority
	req19.xml - Verified with no priority
	req20.xml - Rejected with non-zero priority
	req21.xml - Accepted with priority 4


	NOTE: Testing with XML files and using string comparisons of the contents 
	can be very fragile.  For the RequirementsWriterTest, there can be no tabs
	in the expected output files.  For both RequirementsWriterTest and 
	RequirementsReaderTest, the summary contents must be on one line to minimize 
	test failures due to new lines, tabs, or spaces.*/
	
	
	/**Private instance variable for the requirements tracker model*/
	private RequirementsTrackerModel rlm;
	/**Private instance variable for valid requirements file */
	private final String validTestFile = "test-files/req1.xml";
	/**Private instance variable for the save to file */
	private final String saveNewFile = "test-files/reqSaveFile.xml";

	/**Sets up an instance of the requirements tracker model before each test case
	 * @throws Exception if an error in getting an instance variable
	 */
	@Before
	public void setUp() throws Exception {
		rlm = RequirementsTrackerModel.getInstance();
	}

	/**Tests that an instance of requirements tracker model was created during setup 
	 */
	@Test
	public void testGetInstance() {
		assertTrue(RequirementsTrackerModel.getInstance() != null);
		
	}

	/**Tests that a list of requirements is saved to a file
	 */
	@Test
	public void testSaveRequirementsToFile() {
		RequirementsTrackerModel.getInstance();
		rlm.createNewRequirementsList();
		rlm.addRequirement("testWrite", "Does it write to file");
		rlm.saveRequirementsToFile(saveNewFile);
		rlm.loadRequirementsFromFile(saveNewFile);
		assertEquals(rlm.getRequirementById(0).getSummary(), "testWrite");
		//test with not nulls
		rlm.createNewRequirementsList();
		rlm.loadRequirementsFromFile("test-files/exp_req_all.xml");
		rlm.addRequirement("testAnother", "Anothers Test ID");
		assertEquals(rlm.getRequirementById(33).getSummary(), "testAnother");
		Command cnew = new Command(CommandValue.ACCEPT, "testAnother", "Anothers Test ID", 1, "TestTime", null, null);
		//CommandValue value, String summary, String acceptTestId, int priority, String estimate, String developerId, Rejection rejectReason
		Command cnew2 = new Command(CommandValue.ASSIGN, "testAnother", "Anothers Test ID", 1, "TestTime", "Johnny Cash", null);
		rlm.executeCommand(33, cnew);
		rlm.executeCommand(33, cnew2);
		assertEquals(rlm.getRequirementById(33).getDeveloper(), "Johnny Cash");
		rlm.saveRequirementsToFile("test-files/anotherNewFile.xml");
	}

	/**Tests that requirements tracker model can load requirements from a file
	 * 
	 */

	@Test
	public void testLoadRequirementsFromFile() {
		try{
			rlm.loadRequirementsFromFile(validTestFile);
			assertEquals(rlm.getRequirementById(0).getAcceptanceTestId(), "SubmittedToAccepted");
			assertEquals(rlm.getRequirementById(0).getState().getStateName(), "Submitted");
			assertEquals(rlm.getRequirementById(1).getState().getStateName(), "Accepted");
			assertEquals(rlm.getRequirementById(2).getState().getStateName(), "Working");
			assertEquals(rlm.getRequirementById(4).getState().getStateName(), "Completed");
			assertEquals(rlm.getRequirementById(23).getState().getStateName(), "Rejected");
			assertEquals(rlm.getRequirementById(32).getState().getStateName(), "Verified");
		} catch(IllegalArgumentException e){
			System.out.println("Error loading requirements from file during unit test");
		}		
	}


	/**Tests getting requirements list as an array
	 * 
	 */
	@Test
	public void testGetRequirementListAsArray() {
		rlm.createNewRequirementsList();
		rlm.loadRequirementsFromFile("test-files/exp_req_all.xml");
		rlm.addRequirement("testAnother", "Anothers Test ID");
		Object[][] reqListAsArray = rlm.getRequirementListAsArray();
		assertEquals(reqListAsArray[0][0], 0);
		
	}


	/** Tests the execution of a command through the Requirements Tracker Model
	 * 
	 */
	@Test
	public void testExecuteCommand() {
		rlm.createNewRequirementsList();
		rlm.loadRequirementsFromFile("test-files/exp_req_all.xml");
		rlm.addRequirement("testAnother", "Anothers Test ID");
		assertEquals(rlm.getRequirementById(33).getSummary(), "testAnother");
		Command c = new Command(CommandValue.ACCEPT, "testAnother", "Anothers Test ID", 1, "TestTime", null, null);
		rlm.executeCommand(33, c);
		assertEquals(rlm.getRequirementById(33).getState().getStateName(), "Accepted");
		
	}

	/**JUnut test to delete a requirement by ID number
	 * 
	 */
	@Test
	public void testDeleteRequirementById() {
		rlm.createNewRequirementsList();
		rlm.loadRequirementsFromFile("test-files/exp_req_all.xml");
		rlm.addRequirement("testAnother", "Anothers Test ID");
		assertEquals(rlm.getRequirementById(33).getSummary(), "testAnother");
		Command c = new Command(CommandValue.ACCEPT, "testAnother", "Anothers Test ID", 1, "TestTime", null, null);
		rlm.executeCommand(33, c);
		assertEquals(rlm.getRequirementById(33).getState().getStateName(), "Accepted");
		rlm.deleteRequirementById(33);
		assertNull(rlm.getRequirementById(33));
	}
}
