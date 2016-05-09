package edu.ncsu.csc216.tracker.requirement;

import edu.ncsu.csc216.tracker.requirement.enums.CommandValue;
import edu.ncsu.csc216.tracker.requirement.enums.Rejection;
/**This class is what handles the user inputs in the
 * GUI. This class passes information to be handled by the
 * finite state machine.
 * @author Andrew Northrup
 * 
 */
public class Command {
	/**Instance variable for the requirement summary */
	private String summary;
	/**Instance variable for the acceptance ID test the requirement must pass*/
	private String acceptanceTestId;
	/**Instance variable for the developer's ID who is working on the requirement */
	private String developerId;
	/**Instance variable for the estimate for amount of time to complete requirement */
	private String estimate;
	/**Instance variable for the priority of the requirement from 0 to 3(inclusive) */
	private int priority;
	/**Instance variable for the value of the command*/
	private CommandValue command;
	/**Instance variable for the rejection reason*/
	private Rejection rejectReason;
	/** Constructor for the command which is passed all the variables needed for a 
	 * requirement. The constructor will throw Illegal Argument Exceptions for invalid
	 * inputs.
	 * @param value from the enum for what the action is to be
	 * @param summary for the summary of the requirement
	 * @param acceptTestId for the acceptance test ID
	 * @param priority for the priority of the requirement
	 * @param estimate for the requirement time estimate
	 * @param developerId for the developer name
	 * @param rejectReason for the reason the requirement was rejected
	 */
	public Command(CommandValue value, String summary, String acceptTestId, int priority, String estimate, String developerId, Rejection rejectReason){
		if(value == null){throw new IllegalArgumentException(); }
		if(priority > 3 || priority < 0){throw new IllegalArgumentException(); }
		if(value == CommandValue.ACCEPT && (estimate == null || estimate.isEmpty())) {throw new IllegalArgumentException(); }
		if(value == CommandValue.REJECT && rejectReason == null) {throw new IllegalArgumentException(); }
		if(value == CommandValue.ASSIGN && (developerId == null || developerId.isEmpty())){
			throw new IllegalArgumentException();
		}
		if(value == CommandValue.REVISE && (summary == null || summary.isEmpty() || acceptTestId == null || acceptTestId.isEmpty())){
			throw new IllegalArgumentException();
		}
		this.command = value;
		this.summary = summary;
		this.acceptanceTestId = acceptTestId;
		this.priority = priority;
		this.estimate = estimate;
		this.developerId = developerId;
		this.rejectReason = rejectReason;

	}
	/** Gets the summary of the requirement
	 * @return string for the requirement summary
	 */
	public String getSummary(){
		return this.summary;
	}
	/**Gets the acceptance test ID
	 * @return the string for the acceptance test ID
	 */
	public String getAcceptanceTestId(){
		return this.acceptanceTestId;
	}
	/**Gets the command value of type CommandValue
	 * @return command value of the command created
	 */
	public CommandValue getCommand(){
		return command;
	}
	/**Gets the estimate for the requirement
	 * @return string for the estimate
	 */
	public String getEstimate(){
		return this.estimate;
	}
	/**Gets the priority for the requirement
	 * @return integer for the priority of the requirement
	 */
	public int getPriority(){
		return this.priority;
	}
	/**Gets the developer ID string
	 * @return string for the developers ID
	 */
	public String getDeveloperId(){
		return this.developerId;
	}
	/**Gets the reason rejected for the 
	 * @return a rejection objection for reason rejected
	 */
	public Rejection getRejectionReason(){
		return rejectReason;
	}
	
}
