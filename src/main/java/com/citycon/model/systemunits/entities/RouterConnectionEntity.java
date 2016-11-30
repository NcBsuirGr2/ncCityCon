package com.citycon.model.systemunits.entities;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Represents all necessary information about router connection. It is the plain
 * java bean which must be obtained from DAO layer and be used in jsp-pages 
 * to display router connection information.
 *
 * @author Mike
 * @version 1.1
 */
public class RouterConnectionEntity extends Entity {
	@NotNull
	@Valid
	private RouterEntity firstRouter = new RouterEntity();

	@NotNull
	@Valid
	private RouterEntity secondRouter = new RouterEntity();

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