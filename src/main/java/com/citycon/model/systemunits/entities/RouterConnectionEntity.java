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
	private RouterEntity firstRouter;
	private RouterEntity secondRouter;

	// ----- Getters -----
	public RouterEntity getFirstRouter() {
		return firstRouter;
	}
	public RouterEntity getSecondRouter() {
		return secondRouter;
	}

	// ----- Setters -----
	public void setFirstRouter(RouterEntity firstRouter) {
		this.firstRouter = firstRouter;
	}
	public void setSecondRouter(RouterEntity secondRouter) {
		this.secondRouter = secondRouter;
	}

	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append("RouterConnection: id(");
		string.append(id);
		string.append("), firstRouter(");
		string.append(firstRouter);
		string.append("), secondRouter(");
		string.append(secondRouter);
		string.append(")");
		return string.toString();
	}
}