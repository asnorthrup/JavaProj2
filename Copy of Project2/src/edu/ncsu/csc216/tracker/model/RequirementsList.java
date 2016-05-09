/**
 * 
 */
package edu.ncsu.csc216.tracker.model;

import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc216.tracker.requirement.Command;
import edu.ncsu.csc216.tracker.requirement.Requirement;
import edu.ncsu.csc216.tracker.xml.Req;

/**The list of requirements for the project
 * @author Andrew Northrup
 *
 */
public class RequirementsList {
	/** Instance variable for array list of requirements*/
	private ArrayList<Requirement> requirementArrayList;
	/** Instance variable for array list of requirements*/
	private List<Req> reqList;
	/**Constructor for requirement list */
	public RequirementsList(){
		requirementArrayList = new ArrayList<Requirement>();
		Requirement.setCounter(0);
	}
	/** Adds a requirement to the list
	 * @param reqSummary summary of the requirement
	 * @param acceptTestId accept test ID
	 * @return the index of where the requirement was added in the list
	 */
	public int addRequirement(String reqSummary, String acceptTestId){
		Requirement requirement = new Requirement(reqSummary, acceptTestId);
		requirementArrayList.add(requirement);
		return requirementArrayList.indexOf(requirement);
	}
	/** Adds requirements from a list of requirements and creates a requirement out of each req
	 * @param reqListPassedIn is a list of requirements read in from XML file
	 */
	public void addXMLReqs(List<Req> reqListPassedIn){
		this.reqList = reqListPassedIn;
		for(Req r: reqList){
			Requirement requiremnt = new Requirement(r);
			this.requirementArrayList.add(requiremnt);
		}
		Requirement.setCounter(findHighestReqId() + 1);
	}
	//Helper method to determine the largest requirement ID number
	private int findHighestReqId(){
		int currentHighestID = 0;
		for(Requirement r: requirementArrayList){
			if(r.getRequirementId() > currentHighestID) {currentHighestID = r.getRequirementId(); }
		}
		return currentHighestID;
	}
	/**Gets the list of requirements in the project
	 * @return list of requirements in the project as a List of type requirements
	 */
	public List<Requirement> getRequirements(){
		return requirementArrayList;
	}
	/**Gets a requirement from the list by the ID number
	 * @param idNumber for number of requirement ID to get from the list
	 * @return requirement associated with ID passed in or null if no requirement exists
	 */
	public Requirement getRequirementById(int idNumber){
		for(Requirement r: requirementArrayList){
			if(r.getRequirementId() == idNumber){
				return r;
			}
		}
		return null;
	}

	/** Code to execute the command based on requirement ID
	 * @param requirementID for the requirement ID number
	 * @param c command c passed to requirement
	 */
	public void executeCommand(int requirementID, Command c){
		Requirement r = getRequirementById(requirementID);
		if (r != null){
			r.update(c);
		}
	}
	/**Deletes a requirement based on requirement ID passed in
	 * @param idNumber for requirement to remove
	 */
	public void deleteRequirementById(int idNumber){
		for(int i = 0; i < requirementArrayList.size(); i++){
			if(requirementArrayList.get(i).getRequirementId() == idNumber){
				requirementArrayList.remove(i);
			}
		}
	}
}
