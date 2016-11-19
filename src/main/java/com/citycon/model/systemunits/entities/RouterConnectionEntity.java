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
	private String firstRouterSN;
	private int secondRouterId;
	private String secondRouterSN;

	public int getFirstRouterId() {
		return firstRouterId;
	}
	public int getSecondRouterId() {
		return secondRouterId;
	}
	public String getFirstRouterSN() {
		return firstRouterSN;
	}
	public String getSecondRouterSN() {
		return secondRouterSN;
	}

	public void setFirstRouterId(int firstRouterId) {
		this.firstRouterId = firstRouterId;
	}
	public void setSecondRouterId(int secondRouterId) {
		this.secondRouterId = secondRouterId;
	}
	public void setFirstRouterSN(String firstRouterSN) {
		this.firstRouterSN = firstRouterSN;
	}
	public void setSecondRouterSN(String secondRouterSN) {
		this.secondRouterSN = secondRouterSN;
	}

	public String toString() {
		StringBuilder routerConnectionString = new StringBuilder();
		routerConnectionString.append("RouterConnection: id(");
		routerConnectionString.append(id);
		routerConnectionString.append("), firstRouterId(");
		routerConnectionString.append(firstRouterId);
		routerConnectionString.append("), firstRouterSN(");
		routerConnectionString.append(firstRouterSN);
		routerConnectionString.append("), secondRouterId(");
		routerConnectionString.append(secondRouterId);
		routerConnectionString.append("), secondRouterSN(");
		routerConnectionString.append(secondRouterSN);
		routerConnectionString.append(")");
		return routerConnectionString.toString();
	}
}