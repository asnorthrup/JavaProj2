/**
 * 
 */
package edu.ncsu.csc216.tracker.requirement;

import edu.ncsu.csc216.tracker.requirement.enums.CommandValue;
import edu.ncsu.csc216.tracker.requirement.enums.Rejection;
import edu.ncsu.csc216.tracker.xml.Req;

/**Class that represents a requirement to include in the program requirements list
 * @author Andrew Northrup
 *
 */
public class Requirement {
	/**Instance variable for requirement ID*/
	private int requirementId;
	/**Instance variable for summary of requirement request*/
	private String summary;
	/**Instance variable for acceptance Test ID	*/
	private String acceptanceTestId;
	/**Instance variable for priority of requirement */
	private int priority;
	/**Instance variable for estimate of time to complete requirement */
	private String estimate;
	/**Instance variable for developer working on requirement */
	private String developer;
	/**Instance variable for storing a Req if it is created */
	private Req req = null;
	
	/**Constant for name of status for submitted requirement */
	public static final String SUBMITTED_NAME = "Submitted";
	/**Constant for name of status for accepted requirement */
	public static final String ACCEPTED_NAME = "Accepted";
	/**Constant for name of status for rejected requirement */
	public static final String REJECTED_NAME = "Rejected";
	/**Constant for name of status for working requirement */
	public static final String WORKING_NAME = "Working";
	/**Constant for name of status for completed requirement */
	public static final String COMPLETED_NAME = "Completed";
	/**Constant for name of status for verified requirement */
	public static final String VERIFIED_NAME = "Verified";
	/**Constant for name of status for duplicated requirement */
	public static final String DUPLICATE_NAME = "Duplicate";
	/**Constant for name of status for infeasible requirement */
	public static final String INFEASIBLE_NAME = "Infeasible";
	/**Constant for name of status for too large requirement */
	public static final String TOO_LARGE_NAME = "Too Large";
	/**Constant for name of status for out of scope requirement */
	public static final String OUT_OF_SCOPE_NAME = "Out of Scope";
	/**Constant for name of status for inappropriate requirement */
	public static final String INAPPROPRIATE_NAME = "Inappropriate";
	
	/**Instance variable to store the rejection reason*/
	private Rejection rejectionReason;
	
	/**Instance variable for creating a new submitted state*/
	private final RequirementState submittedState = new SubmittedState();
	/**Instance variable for creating a new accepted state*/
	private final RequirementState acceptedState = new AcceptedState();
	/**Instance variable for creating a new working state*/
	private final RequirementState workingState = new WorkingState();
	/**Instance variable for creating a new completed state*/
	private final RequirementState completedState = new CompletedState();
	/**Instance variable for creating a new verified state*/
	private final RequirementState verifiedState = new VerifiedState();
	/**Instance variable for creating a new rejected state*/
	private final RequirementState rejectedState = new RejectedState();
	/**Instance variable for the current state the requirement is in*/
	private RequirementState currentState = submittedState;
	
	/**Instance variable for a counter for the requirement ID plus 1 */
	public static int counter = 0;
	
	/**Constructor for a requirement
	 * @param summary for summary of the requirement
	 * @param acceptTestId for acceptance test ID
	 */
	public Requirement(String summary, String acceptTestId){
		this.summary = summary;
		this.acceptanceTestId = acceptTestId;
		this.requirementId = counter;
		incrementCounter();
	}
	/** Creates a requirement from the XML reader
	 * @param req a requirement read from the file read by XML reader
	 */
	public Requirement(Req req) {
		this.req = req;
		this.requirementId = req.getId();
		this.developer = req.getDeveloper();
		this.estimate = req.getEstimate();
		this.priority = req.getPriority();
		setRejectionReason(req.getRejection());
		setState(req.getState());
		this.summary = req.getSummary();
		this.acceptanceTestId = req.getTest();
	}
	/**Requirement counter is set to zero for a new list and is set
	 * to the Max ID of a XML file read in. This method increments the counter and is a static method.
	 */
	private static void incrementCounter(){
		counter++;
	}
	/**Gets the requirement ID of the requirement
	 * @return an int that is the requirement's ID number
	 */
	public int getRequirementId() {
		return this.requirementId;
	}
	/**Gets the RequirementState object that the requirement currently is
	 * @return RequirementState object of the state the requirement is
	 */
	public RequirementState getState(){
		return currentState;
	}
	/** Sets the state of the requirement based on the state passed in
	 * @param stateNameSetTo for the state to set the requirement to
	 */
	private void setState(String stateNameSetTo){
		switch(stateNameSetTo) {
		case SUBMITTED_NAME: currentState = submittedState; break;
		case ACCEPTED_NAME: currentState = acceptedState; break;
		case REJECTED_NAME: currentState = rejectedState; break;
		case WORKING_NAME: currentState = workingState; break;
		case COMPLETED_NAME: currentState = completedState; break;
		case VERIFIED_NAME: currentState = verifiedState; break;
		default: throw new IllegalArgumentException();
		}
	}
	/**Gets the priority of the requirement
	 * @return integer for the priority number of the requirement
	 */
	public int getPriority(){
		return this.priority;
	}
	/**Gets the estimate of how long the requirement will take
	 * @return string of an estimate for how long the requirement will take
	 */
	public String getEstimate(){
		return this.estimate;
	}
	/**Gets the reason the requirement was rejected
	 * @return the rejection enum reason
	 */
	public Rejection getRejectionReason(){
		return rejectionReason;
	}
	/**Gets the reasons the requirement was rejected as a string
	 * @return string that is why requirement was rejected
	 */
	public String getRejectionReasonString(){
		if(rejectionReason == null){
			return "";
		}
		else{
			switch(rejectionReason) {
				case DUPLICATE: return DUPLICATE_NAME; 
				case INFEASIBLE: return INFEASIBLE_NAME; 
				case TOO_LARGE: return TOO_LARGE_NAME; 
				case OUT_OF_SCOPE: return OUT_OF_SCOPE_NAME; 
				case INAPPROPRIATE: return INAPPROPRIATE_NAME; 
				default: throw new IllegalArgumentException();
			}
		}
	}
	/**Sets the reason the requirement was rejected
	 * @param rejectReason string of why the requirement was rejected.
	 */
	private void setRejectionReason(String rejectReason){
		if(rejectReason != null){
			switch(rejectReason) {
			case DUPLICATE_NAME: this.rejectionReason = Rejection.DUPLICATE; break;
			case INFEASIBLE_NAME: this.rejectionReason = Rejection.INFEASIBLE; break;
			case TOO_LARGE_NAME: this.rejectionReason = Rejection.TOO_LARGE; break;
			case OUT_OF_SCOPE_NAME: this.rejectionReason = Rejection.OUT_OF_SCOPE; break;
			case INAPPROPRIATE_NAME: this.rejectionReason = Rejection.INAPPROPRIATE; break;
			default: throw new IllegalArgumentException();
			}
		} else{
			this.rejectionReason = null;
		}
		
	}
	/**Gets the assigned developer's name
	 * @return string of assigned developer's name
	 */
	public String getDeveloper(){
		return this.developer;
	}
	/**Gets the summary of the requirement
	 * @return returns a string of the requirement's summary
	 */
	public String getSummary(){
		return this.summary;
	}
	/**Gets the string of the acceptance test ID
	 * @return string of the acceptance test ID
	 */
	public String getAcceptanceTestId(){
		return this.acceptanceTestId;
	}
	/**Sets the Acceptance Test ID
	 * @param testID String for the acceptance test ID
	 */
	public void setAcceptanceTestId(String testID){
		this.acceptanceTestId = testID;
	}
	/**Sets the name of the developer
	 * @param developerName for the name of the developer assigned to the requirement
	 */
	public void setDeveloper(String developerName){
		this.developer = developerName;
	}
	/**Updates the state of the requirement based on the command it is given
	 * @param c for the command that was passed to the requirement
	 */
	public void update(Command c){
		switch(currentState.getStateName()) {
			case SUBMITTED_NAME: submittedState.updateState(c); break;
			case ACCEPTED_NAME: acceptedState.updateState(c); break;
			case REJECTED_NAME: rejectedState.updateState(c); break;
			case WORKING_NAME: workingState.updateState(c); break;
			case COMPLETED_NAME: completedState.updateState(c); break;
			case VERIFIED_NAME: verifiedState.updateState(c); break;
			default: throw new IllegalArgumentException();
		}
	}
	/**Gets the XML read in requirement 'Req'
	 * @return Req object for the XML requirement
	 */
	public Req getXMLReq(){
		return req;
	}
	/**Sets the counter for the requirement ID number
	 * @param reqId for the integer of the counter
	 */
	public static void setCounter(int reqId){
		counter = reqId;
	}
	/**Inner class that handles setting the requirements for a submitted state that was passed command accept or reject
	 * @author Andrew Northrup
	 */
	private class SubmittedState implements RequirementState {
		/**Handles updating the fields for the requirement associated with command passed in
		 * @param for a command passed to the requirement
		 * @throws unsupported operation if a command doesn't match something that can be done to a submitted state requirement 
		 */
		@Override
		public void updateState(Command c) {
			if(c.getCommand() == CommandValue.ACCEPT || c.getCommand() == CommandValue.REJECT){
				if(c.getCommand() == CommandValue.ACCEPT){
					estimate = c.getEstimate();
					priority = c.getPriority();
					currentState = acceptedState;
				} else if(c.getCommand() == CommandValue.REJECT){
					estimate = null;
					priority = 0;
					developer = null;
					rejectionReason = c.getRejectionReason();
					currentState = rejectedState;
				}
			}
			else {
				throw new UnsupportedOperationException();
			}
		}
		/**Gets the name of the state, required for determining what the current state is when needed
		 * @returns a string of the state's name
		 */
		@Override
		public String getStateName() {
			return SUBMITTED_NAME;
		}
	}
	
	/**Inner class that handles setting the requirements for an accepted state that was passed command assign or reject
	 * @author Andrew Northrup
	 */
	private class AcceptedState implements RequirementState {
		/**Handles updating the fields for the requirement associated with command passed in
		 * @param for a command passed to the requirement
		 * @throws unsupported operation if a command doesn't match something that can be done to a accepted state requirement 
		 */
		@Override
		public void updateState(Command c) {
			if(c.getCommand() == CommandValue.ASSIGN || c.getCommand() == CommandValue.REJECT){
				if(c.getCommand() == CommandValue.ASSIGN){
					developer = c.getDeveloperId();
					currentState = workingState;
				}
				if(c.getCommand() == CommandValue.REJECT){
					estimate = null;
					priority = 0;
					developer = null;
					rejectionReason = c.getRejectionReason();
					currentState = rejectedState;
				}
			} else{
				throw new UnsupportedOperationException();
			}
		}
		/**Gets the name of the state, required for determining what the current state is when needed
		 * @returns a string of the state's name
		 */
		@Override
		public String getStateName() {
			return ACCEPTED_NAME;
		}
		
	}
	/**Inner class that handles setting the requirements for a working state that was passed command assign or reject
	 * @author Andrew Northrup
	 */
	private class WorkingState implements RequirementState {
		/**Handles updating the fields for the requirement associated with command passed in
		 * @param for a command passed to the requirement
		 * @throws unsupported operation if a command doesn't match something that can be done to a working state requirement 
		 */
		@Override
		public void updateState(Command c) {
			if(c.getCommand() == CommandValue.COMPLETE || c.getCommand() == CommandValue.REJECT){
				if(c.getCommand() == CommandValue.COMPLETE){
					currentState = completedState;
				} else if(c.getCommand() == CommandValue.REJECT){
					estimate = null;
					priority = 0;
					developer = null;
					rejectionReason = c.getRejectionReason();
					currentState = rejectedState;
				}
			} else{
				throw new UnsupportedOperationException();
			}
		}
		/**Gets the name of the state, required for determining what the current state is when needed
		 * @returns a string of the state's name
		 */
		@Override
		public String getStateName() {
			return WORKING_NAME;
		}
	}

	/**Inner class that handles setting the requirements for a completed state that was passed command assign or reject
	 * @author Andrew Northrup
	 */
	private class CompletedState implements RequirementState {
		/**Handles updating the fields for the requirement associated with command passed in
		 * @param for a command passed to the requirement
		 * @throws unsupported operation if a command doesn't match something that can be done to a completed state requirement 
		 */
		@Override
		public void updateState(Command c) {
			if(c.getCommand() == CommandValue.FAIL || c.getCommand() == CommandValue.REJECT || c.getCommand() == CommandValue.ASSIGN || c.getCommand() == CommandValue.PASS){
				if(c.getCommand() == CommandValue.FAIL){
					currentState = workingState;
				} else if(c.getCommand() == CommandValue.ASSIGN){
					developer = c.getDeveloperId();
					currentState = workingState;
				} else if(c.getCommand() == CommandValue.PASS){
					currentState = verifiedState;
				} else if(c.getCommand() == CommandValue.REJECT){
					estimate = null;
					priority = 0;
					developer = null;
					rejectionReason = c.getRejectionReason();
					currentState = rejectedState;
				}
			} else{
				throw new UnsupportedOperationException();
			}
		}
		/**Gets the name of the state, required for determining what the current state is when needed
		 * @returns a string of the state's name
		 */
		@Override
		public String getStateName() {
			return COMPLETED_NAME;
		}
	}
	
	/**Inner class that handles setting the requirements for a completed state that was passed command assign or reject
	 * @author Andrew Northrup
	 */
	private class VerifiedState implements RequirementState {
		/**Handles updating the fields for the requirement associated with command passed in
		 * @param for a command passed to the requirement
		 * @throws unsupported operation if a command doesn't match something that can be done to a verified state requirement 
		 */
		@Override
		public void updateState(Command c) {
			if(c.getCommand() == CommandValue.ASSIGN || c.getCommand() == CommandValue.REJECT){
				if(c.getCommand() == CommandValue.ASSIGN){
					developer = c.getDeveloperId();
					currentState = workingState;
				} else if(c.getCommand() == CommandValue.REJECT){
					estimate = null;
					priority = 0;
					developer = null;
					rejectionReason = c.getRejectionReason();
					currentState = rejectedState;
				} 
			} else {
				throw new UnsupportedOperationException();
			}
		}
		/**Gets the name of the state, required for determining what the current state is when needed
		 * @returns a string of the state's name
		 */
		@Override
		public String getStateName() {
			return VERIFIED_NAME;
		}	
	}
	/**Inner class that handles setting the requirements for a completed state that was passed command assign or reject
	 * @author Andrew Northrup
	 */
	private class RejectedState implements RequirementState {
		/**Handles updating the fields for the requirement associated with command passed in
		 * @param for a command passed to the requirement
		 * @throws unsupported operation if a command doesn't match something that can be done to a rejected state requirement 
		 */
		@Override
		public void updateState(Command c) {
			if(c.getCommand() == CommandValue.REVISE){
				acceptanceTestId = c.getAcceptanceTestId();
				summary = c.getSummary();
				rejectionReason = null;
				currentState = submittedState;
			} else{
				throw new UnsupportedOperationException(); 
			}
		}
		/**Gets the name of the state, required for determining what the current state is when needed
		 * @returns a string of the state's name
		 */
		@Override
		public String getStateName() {
			return REJECTED_NAME;
		}
	}
}
