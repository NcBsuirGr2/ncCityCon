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
	private String firstRouterCityName;
	private String firstRouterCountry;
	private boolean firstRouterActive;

	private int secondRouterId;
	private String secondRouterSN;
	private String secondRouterCityName;
	private String secondRouterCountry;
	private boolean secondRouterActive;

	// ----- Getters -----
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

	public String getFirstRouterCityName() {
		return firstRouterCityName;
	}	
	public String getSecondRouterCityName() {
		return secondRouterCityName;
	}

	public String getFirstRouterCountry() { 
		return firstRouterCountry; 
	}
	public String getSecondRouterCountry() { 
		return secondRouterCountry; 
	}
	public boolean isFirstRouterActive() { 
		return firstRouterActive; 
	}
	public boolean isSecondRouterActive() { 
		return secondRouterActive; 
	}
	// ----- Setters -----
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

	public void setFirstRouterCityName(String firstRouterCityName) {
		this.firstRouterCityName = firstRouterCityName;
	}
	public void setSecondRouterCityName(String secondRouterCityName) {
		this.secondRouterCityName = secondRouterCityName;
	}

	public void setFirstRouterCountry(String firstRouterCountry) {
		this.firstRouterCountry = firstRouterCountry;
	}
	public void setSecondRouterCountry(String secondRouterCountry) {
		this.secondRouterCountry = secondRouterCountry;
	}
	public void setFirstRouterActive(boolean firstRouterAcitive) {
		this.firstRouterActive = firstRouterAcitive;
	}
	public void setSecondRouterActive(boolean secondRouterActive) {
		this.secondRouterActive = secondRouterActive;
	}


	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append("RouterConnection: id(");
		string.append(id);
		string.append("), firstRouterId(");
		string.append(firstRouterId);
		string.append("), firstRouterSN(");
		string.append(firstRouterSN);
		string.append("), firstRouterCityName(");
		string.append(firstRouterCityName);
		string.append("), firstRouterCountry");
		string.append(firstRouterCountry);
		string.append("), firstRouterActive");
		string.append(firstRouterActive);
		string.append("), secondRouterId(");
		string.append(secondRouterId);
		string.append("), secondRouterSN(");
		string.append(secondRouterSN);
		string.append("), secondRouterCityName(");
		string.append(secondRouterCityName);
		string.append("), secondRouterCountry");
		string.append(secondRouterCountry);
		string.append("), secondRouterActive");
		string.append(secondRouterActive);
		string.append(")");
		return string.toString();
	}
}