package com.citycon.model.systemunits.entities;

import com.citycon.model.Grant;
/**
 * Represents all necessary information about router connection. It is the plain
 * java bean which must be obtained from DAO layer and be used in jsp-pages 
 *  to display router connection information.
 *
 * @author Mike
 * @version 1.1
 */
public class RouterConnectionEntity extends Entity {
	private String firstRouterId;
	private String secondRouterId;

	public String getFirstRouterId() {
		return firstRouterId;
	}
	public String getSecondRouterId() {
		return secondRouterId;
	}

	public void setFirstRouterId(String firstRouterId) {
		this.firstRouterId = firstRouterId;
	}
	public void setSecondRouterId(String secondRouterId) {
		this.secondRouterId = secondRouterId;
	}
}