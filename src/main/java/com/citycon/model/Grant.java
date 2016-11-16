package com.citycon.model;
/**
 * Represents user grants for the app functionality. For every functionality
 * Grant class contains special field, that describes the level of actions,
 * that user can perform.
 *
 * @author  Mike
 * @version  0.3
 */
public class Grant {
	public final static int NONE = 0;
	public final static int READ = 1;
	public final static int EDIT = 2;

	private int usersBranchLevel = 0;
	private int systemUnitsBranchLevel = 0;
	/**
	 * @param level the level of actions that can be performed with the users entities
	 * @throws  IllegalArgumentException if the <code>level</code> not in range NONE...EDIT constants
	 */
	public void setUsersBranchLevel(int level) {
		validateGrantLevel(level);
		this.usersBranchLevel = level;
	}
	/**
	 * @param level the level of actions that can be performed with the system units entities such as cities, routers, connections
	 * @throws  IllegalArgumentException if the <code>level</code> not in range NONE...EDIT constants
	 */
	public void setSystemUnitsBranchLevel(int level) {
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
	 * @return level the level of actions that can be performed with the system units entities such as cities, routers, connections
	 */
	public int getSystemUnitsBranchLevel() {
		return systemUnitsBranchLevel;
	}

	private void validateGrantLevel(int level) {
		if (level < NONE || level > EDIT) {
			throw new IllegalArgumentException("The grant level must be in range +" + NONE +".."+ EDIT +", got "+level);
		}
	}
}