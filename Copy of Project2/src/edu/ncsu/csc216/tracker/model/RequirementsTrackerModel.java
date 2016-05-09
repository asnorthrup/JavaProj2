/**
 * 
 */
package edu.ncsu.csc216.tracker.model;

import java.util.ArrayList;
import edu.ncsu.csc216.tracker.requirement.Command;
import edu.ncsu.csc216.tracker.requirement.Requirement;
import edu.ncsu.csc216.tracker.xml.Req;
import edu.ncsu.csc216.tracker.xml.RequirementIOException;
import edu.ncsu.csc216.tracker.xml.RequirementsReader;
import edu.ncsu.csc216.tracker.xml.RequirementsWriter;

/**This class maintains the current requirements list as well as loading, saving,
 * and creating new requirements lists. This is the class that uses RequirementReader and
 * RequirementWriter from the RequirementsTrackerXML library. This class catches Requirement IO
 * exceptions and throws an Illegal argument exception
 * @author Andrew Northrup
 */

public class RequirementsTrackerModel {
	/**Instance variable for the reader of the XML file*/
	private RequirementsReader reader;
	/**Instance variable for the writer of the XML file*/
	private RequirementsWriter writer;
	/**Instance variable for the list of requirements from the reader*/
	private RequirementsList requirementsList;
	/**Instance variable for the list of requirements lists, to have more than one requirements lists*/
	private ArrayList<RequirementsList> listOfRequirementsListArrayList;
	/**Instance variable for the list of requirements lists, to keep track of the current list of requirements within the others*/
	private RequirementsList currentRequirementsList;
	/**Requirements tracker model singleton instance*/
	private static RequirementsTrackerModel model = null;
	
	/**Constructor for the requirements tracker model. Constructor creates the requirements list object
	 * 
	 */
	private RequirementsTrackerModel(){
		listOfRequirementsListArrayList = new ArrayList<RequirementsList>();
		createNewRequirementsList();
	}
	/**Returns the singleton instance of RequirementsTrackerModel. If an instance doesn't exist,
	 * one is created.
	 * @return RequirementsTrackerModel for singleton that is an instance of requirements tracker model
	 */
	public static RequirementsTrackerModel getInstance(){
		if (model == null){
			model = new RequirementsTrackerModel();
		}
		return model;
	}
	/** Writes the requirements to the file passed in
	 * @param writeFile name of file to write the current requirements list to
	 */
	public void saveRequirementsToFile(String writeFile){
		writer = new RequirementsWriter(writeFile);
		for(Requirement r: requirementsList.getRequirements()){
			Req req = new Req();
			if (r.getDeveloper() != null){
				req.setDeveloper(r.getDeveloper());
			}
			if(r.getEstimate() != null){
				req.setEstimate(r.getEstimate());
			}
			if(r.getRejectionReason() != null){
				req.setRejection(r.getRejectionReasonString());
			}
			req.setId(r.getRequirementId());
			req.setPriority(r.getPriority());
			req.setState(r.getState().getStateName());
			req.setSummary(r.getSummary());
			req.setTest(r.getAcceptanceTestId());
			writer.addReq(req);
		}
		try {
			writer.marshal();
		} catch (RequirementIOException e) {
			System.out.println("Error saving reqs to XML file");
		}
		
	}
	/**Loads requirements from an existing file
	 * @param readFile name of file to read in XML requirements
	 */

	public void loadRequirementsFromFile(String readFile) {
			try {
				reader = new RequirementsReader(readFile);
				createNewRequirementsList();
				currentRequirementsList.addXMLReqs(reader.getReqs());
			} catch (RequirementIOException e) {
				throw new IllegalArgumentException();
			}	
	}
	/**Creates a list of the requirements from the file read in
	 * 
	 */
	public void createNewRequirementsList(){
		requirementsList = new RequirementsList();
		currentRequirementsList = requirementsList;
		listOfRequirementsListArrayList.add(requirementsList);
		
	}
	/**Creates a 2D array of the requirements used by GUI to display ID, state and summary in positions
	 * 1, 2, 3 respectively
	 * @return 2D array representing the requirements and their ID, state and summary
	 */
	public Object[][] getRequirementListAsArray(){
		Object[][] requirementsArray = new Object[requirementsList.getRequirements().size()][3];
		for (int i = 0; i < requirementsList.getRequirements().size(); i++){
			requirementsArray[i][0] = (Integer)requirementsList.getRequirements().get(i).getRequirementId();
			requirementsArray[i][1] = requirementsList.getRequirements().get(i).getState().getStateName();
			requirementsArray[i][2] = requirementsList.getRequirements().get(i).getSummary();
		}
		return requirementsArray;
	}
	/**Finds a requirement from the list by id number
	 * @param requirementId for the ID number requirement to find
	 * @return requirement object for requirement with corresponding ID, returns null if one does not exist
	 */
	public Requirement getRequirementById(int requirementId){
		return requirementsList.getRequirementById(requirementId);
	}
	
	/**Sends a command to a requirement based on the requirement ID
	 * @param requirementID for the requirement ID to update
	 * @param command command to the requirement
	 */
	public void executeCommand(int requirementID, Command command){
		getRequirementById(requirementID).update(command);
	}
	/**Deletes a requirement from the list by the requirement ID number
	 * @param requirementIdNum to remove from the requirements list
	 */
	public void deleteRequirementById(int requirementIdNum){
		requirementsList.deleteRequirementById(requirementIdNum);
	}
	/**Adds a requirement to the requirement list
	 * @param summary for the requirement
	 * @param acceptTestId acceptance test ID
	 */
	public void addRequirement(String summary, String acceptTestId){
		requirementsList.addRequirement(summary, acceptTestId);
	}
	
}
