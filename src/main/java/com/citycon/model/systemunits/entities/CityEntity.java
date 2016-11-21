package com.citycon.model.systemunits.entities;

import com.citycon.model.Grant;
/**
 * Represents all necessary information about city. It is the plain
 * java bean which must be obtained from DAO layer and be used in jsp-pages 
 * to display city information.
 *
 * @author Mike
 * @version 1.1
 */
public class CityEntity extends Entity {	
	private String name;
	private String countryName;
	private int routersNum;

	public String getName() {
		return name;
	}
	public String getCountryName() {
		return countryName;
	}
	public int getRoutersNum() {
		return routersNum;
	}

	public void setName(String name) {
		this.name = name;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public void setRoutersNum(int routersNum) {
		this.routersNum = routersNum;
	}

	public String toString() {
		StringBuilder cityString = new StringBuilder();
		cityString.append("City: id(");
		cityString.append(id);
		cityString.append("), name(");
		cityString.append(name);
		cityString.append("), countryName(");
		cityString.append(countryName);
		cityString.append("), routersNum(");
		cityString.append(routersNum);
		cityString.append(")");
		return cityString.toString();
	}
}