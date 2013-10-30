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
	protected ArrayList<Action> _actionPool; // stack of actions
	
	/**
	 * Returns true is action pool does not
	 * contain any actions, otherwise returns false.
	 * @return boolean
	 */
	protected boolean isEmpty()
	{
		return _actionPool.isEmpty();
	}
	
	/**
	 * Add action a to the end of the action stack
	 * @param a
	 */
	protected void addAction(Action a)
	{
		if(a != null)
			_actionPool.add(a);
	}

	/**
	 * Remove last action in the pool
	 * if it exists, otherwise does nothing.
	 */
	protected void removeAction()
	{
		if(!isEmpty())
			_actionPool.remove(0);
	}
	
	/**
	 * Abstract method that performs all actions
	 * in the pool 
	 */
	protected abstract void performAll();
	
	public ActionPool()
	{
		_actionPool = new ArrayList<Action>();
	}
	
	/**
	 * Clears the action pool
	 */
	public void clearPool()
	{
		_actionPool.clear();
	}
}
