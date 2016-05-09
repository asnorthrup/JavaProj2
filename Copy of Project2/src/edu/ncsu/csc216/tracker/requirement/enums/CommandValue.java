/**
 * 
 */
package edu.ncsu.csc216.tracker.requirement.enums;

/**This package is an enum that has all of the commands that can
 * be passed in to a requirement.
 * @author Andrew Northrup
 *
 */
public enum CommandValue {
	/**
	 * Accepted requirement: Submitted to Accepted
	 */
	ACCEPT,
	/** Rejected requirement: Any state to Rejected
	 * 
	 */
	REJECT,
	/**Revised requirement: Rejected to Revised
	 * 
	 */
	REVISE,
	/**Assigned requirement: Submitted to Accepted
	 * 
	 */
	ASSIGN,
	/** Completed requirement: Completed or Accepted to Completed
	 * 
	 */
	COMPLETE,
	/** Passed requirement: Completed to Verified 
	 * 
	 */
	PASS,
	/**Failed requirement: Completed to Working
	 * 
	 */
	FAIL;

}
