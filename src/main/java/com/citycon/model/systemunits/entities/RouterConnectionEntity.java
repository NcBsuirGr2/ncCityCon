package com.citycon.model.systemunits.entities;

/**
 * Represents all necessary information about router connection. It is the plain
 * java bean which must be obtained from DAO layer and be used in jsp-pages 
 * to display router connection information.
 *
 * @author Mike
 * @version 1.1
 */
public class RouterConnectionEntity extends Entity {
	private int firstRouterId;
	private int secondRouterId;

	public RouterConnectionEntity() {
		firstRouterId = (int)(Math.random()*100000);
		secondRouterId = (int)(Math.random()*100000);
	}

	public int getFirstRouterId() {
		return firstRouterId;
	}
	public int getSecondRouterId() {
		return secondRouterId;
	}

	public void setFirstRouterId(int firstRouterId) {
		this.firstRouterId = firstRouterId;
	}
	public void setSecondRouterId(int secondRouterId) {
		this.secondRouterId = secondRouterId;
	}
}