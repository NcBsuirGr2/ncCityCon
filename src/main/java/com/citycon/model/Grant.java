package com.citycon.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Represents user grants for the app functionality. For every functionality
 * Grant class contains special field, that describes the level of actions,
 * that user can perform.
 *
 * @author  Mike
 * @version  1.0
 */
public class Grant {
	public final static int NONE = 0;
	public final static int READ = 1;
	public final static int EDIT = 2;

	@Min(NONE)
	@Max(EDIT)
	private int usersBranchLevel = 0;

	@Min(NONE)
	@Max(EDIT)
	private int systemUnitsBranchLevel = 0;
	
	/**
	 * @param level the level of actions that can be performed with the users entities
	 * @throws  IllegalArgumentException if the <code>level</code> not in range NONE...EDIT constants
	 */
	public void setUsersBranchLevel(int level) throws IllegalArgumentException {
		validateGrantLevel(level);
		this.usersBranchLevel = level;
	}
	/**
	 * @param level the level of actions that can be performed with the 
	 * system units entities such as cities, routers, connections
	 * @throws  IllegalArgumentException if the <code>level</code> not in range NONE...EDIT constants
	 */
	public void setSystemUnitsBranchLevel(int level) throws IllegalArgumentException {
		validateGrantLevel(level);
		this.systemUnitsBranchLevel = level;
	}
	/**
	 * @return level the level of actions that can be performed with the users entities
	 */
	public int getUsersBranchLevel() {
		return usersBranchLevel;
	}
	/**
	 * @return level the level of actions that can be performed with the 
	 * system units entities such as cities, routers, connections
	 */
	public int getSystemUnitsBranchLevel() {
		return systemUnitsBranchLevel;
	}

	private void validateGrantLevel(int level) throws IllegalArgumentException {
		if (level < NONE || level > EDIT) {
			throw new IllegalArgumentException("The grant level must be in range +" + NONE +".."+ EDIT +", got "+level);
		}
	}
	
	public String toString() {
		StringBuilder grantString = new StringBuilder();
		grantString.append("Grant: usersBranchLevel(");
		grantString.append(usersBranchLevel);
		grantString.append("), systemUnitsBranchLevel(");
		grantString.append(systemUnitsBranchLevel);
		grantString.append(")");
		return grantString.toString();
	}
}
