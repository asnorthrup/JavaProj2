package edu.ncsu.csc216.tracker.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.tracker.requirement.Command;
import edu.ncsu.csc216.tracker.requirement.Requirement;
import edu.ncsu.csc216.tracker.requirement.enums.CommandValue;
import edu.ncsu.csc216.tracker.requirement.enums.Rejection;

/**Tests the requirements list class
 * @author Andrew Northrup
 *
 */
public class RequirementsListTest {
	
	/**Private instance variable for the requirements list */
	private RequirementsList rl;

	/**Set up for creating a new requirements list before each unit test
	 * @throws Exception if error setting creating the requirements list
	 */
	@Before
	public void setUp() throws Exception {
		rl = new RequirementsList();
	}
	/**Tests creating of a requirements list and ensures a requirement can be added 
	 */
	@Test
	public void testRequirementsList() {
		rl.addRequirement("summ", "id");
		assertEquals(1, rl.getRequirements().size());
	}

	/**Tests the method get requirement ID method
	 * 
	 */
	@Test
	public void testGetRequirementById() {
		rl.addRequirement("summ", "id");
		assertEquals("id", rl.getRequirementById(0).getAcceptanceTestId());
	}

	/**Test to ensure command is executed from within the requirement's list
	 * 
	 */
	@Test
	public void testExecuteCommand() {
		Requirement.setCounter(0);
		rl.addRequirement("summ", "id");
		rl.executeCommand(0, new Command(CommandValue.ACCEPT, null, null, 1, "1 day", null, null)); 
		assertEquals("Accepted", rl.getRequirementById(0).getState().getStateName());
		rl.executeCommand(0, new Command(CommandValue.ASSIGN, null, null, 0, null, "dev", null));
		assertEquals("Working", rl.getRequirementById(0).getState().getStateName());
		rl.executeCommand(0, new Command(CommandValue.COMPLETE, null, null, 0, null, null, null));
		assertEquals("Completed", rl.getRequirementById(0).getState().getStateName());
		rl.executeCommand(0, new Command(CommandValue.PASS, null, null, 0, null, null, null));
		assertEquals("Verified", rl.getRequirementById(0).getState().getStateName());
		rl.executeCommand(0, new Command(CommandValue.ASSIGN, null, null, 0, null, "dev2", null));
		assertEquals("Working", rl.getRequirementById(0).getState().getStateName());
		rl.executeCommand(0, new Command(CommandValue.COMPLETE, null, null, 0, null, null, null));
		assertEquals("Completed", rl.getRequirementById(0).getState().getStateName());
		rl.executeCommand(0, new Command(CommandValue.FAIL, null, null, 0, null, null, null));
		assertEquals("Working", rl.getRequirementById(0).getState().getStateName());
		rl.executeCommand(0, new Command(CommandValue.COMPLETE, null, null, 0, null, null, null));
		assertEquals("Completed", rl.getRequirementById(0).getState().getStateName());
		rl.executeCommand(0, new Command(CommandValue.ASSIGN, null, null, 0, null, "dev", null));
		assertEquals("Working", rl.getRequirementById(0).getState().getStateName());		
		rl.executeCommand(0, new Command(CommandValue.COMPLETE, null, null, 0, null, null, null));
		assertEquals("Completed", rl.getRequirementById(0).getState().getStateName());
		rl.executeCommand(0, new Command(CommandValue.PASS, null, null, 0, null, null, null));
		assertEquals("Verified", rl.getRequirementById(0).getState().getStateName());
		rl.executeCommand(0, new Command(CommandValue.REJECT, null, null, 0, null, null, Rejection.OUT_OF_SCOPE));
		assertEquals("Rejected", rl.getRequirementById(0).getState().getStateName());
	}

	/**Test that requirement can be delete by the ID number
	 * 
	 */
	@Test
	public void testDeleteRequirementById() {
		rl.addRequirement("summ", "id");
		assertEquals(1, rl.getRequirements().size());
		rl.deleteRequirementById(0);
		assertEquals(0, rl.getRequirements().size());
	}
}
