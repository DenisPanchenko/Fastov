/**
 * Pool of actions.
 * Actions are represented by Action class.
 * Functionality:
 * 1) Add action to pool
 * 2) Remove last action from pool
 * 3) Perform all actions
 * 
 * @author:			Denis Panchenko, Tanya Pushchalo
 * Written:			27/10/2013
 * Last Updated: 	27/10/2013
 */
package core;

import java.util.*;

public abstract class ActionPool {
	private Stack _actionStack; // stack of actions
	
	/**
	 * Returns true is action pool does not
	 * contain any actions, otherwise returns false.
	 * @return boolean
	 */
	public boolean isEmpty()
	{
		if(_actionStack.size() > 0)
			return false;
		return true;
	}
	
	/**
	 * Add action a to the end of the action stack
	 * @param a
	 */
	public void pushAction(Action a)
	{
		if(a != null)
			_actionStack.push(a);
	}

	/**
	 * Remove last action in the pool
	 * if it exists, otherwise does nothing.
	 */
	public void popAction()
	{
		if(!isEmpty())
			_actionStack.pop();
	}
	
	/**
	 * Abstract method that performs all actions
	 * in the pool 
	 */
	public abstract void performAll();
}
